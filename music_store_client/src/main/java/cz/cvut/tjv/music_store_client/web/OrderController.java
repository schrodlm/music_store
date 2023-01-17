package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.dto.OrderDto;
import cz.cvut.tjv.music_store_client.dto.ProductDto;
import cz.cvut.tjv.music_store_client.service.OrderService;
import cz.cvut.tjv.music_store_client.service.ProductService;
import cz.cvut.tjv.music_store_client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

/*
    Handles all request for Order Entity
 */
@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    //this Array is used to store users products in a in a shopping cart
    ArrayList<Integer> shoppingCart = new ArrayList<>();

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    //==================================================================================================================
    //                                          SHOWING ORDERS
    //==================================================================================================================

    // Show all Order entities in database
    @GetMapping
    public String showAllOrders(Model model)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("allOrders", orderService.readAll());
        return "orders";
    }

    // Handles deletion of an order - deleting represents that order was successfully handled
    @DeleteMapping("/delete/{id}")
    public String orderFinished(@PathVariable long id, RedirectAttributes redirectAttributes)
    {
        orderService.setActiveOrder(id);
        OrderDto tmp = orderService.readOne().orElseThrow();
        String s = "Order ID " + tmp.getId() + " was successfully finished!";
        redirectAttributes.addFlashAttribute("deleted",true);
        redirectAttributes.addFlashAttribute("deleteMessage",s);

        orderService.delete();

        return "redirect:/orders";

    }

    // Shows all Order entities of a specific user from the database
    @GetMapping("/user/{username}")
    public String showUserOrders(@PathVariable String username, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());

        Integer userId = userService.findByUsername(username).orElseThrow().getId();

        model.addAttribute("allOrders", orderService.readUserOrders(userId));

        return "userOrders";
    }

    // shows the important orders - these are orders that are "Preparing" or "Waiting"
    @GetMapping("/important")
    public String showImportantOrders(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());

        Collection<OrderDto> veryImportant = new ArrayList<>();
        Collection<OrderDto> important = new ArrayList<>();
        Collection<OrderDto> notImportant = new ArrayList<>();

        for(OrderDto order : orderService.readImportantOrders())
        {
            order.getDate_of_order().getMonth();
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime orderDate = order.getDate_of_order();

            Duration duration = Duration.between(orderDate,now);

            if(duration.toMinutes() > 7)
            {
                if(order.getOrder_status().equals("Waiting"))
                    veryImportant.add(order);
                else {
                    important.add(order);
                }
            }

            else if(duration.toMinutes() > 4)
            {
                if(order.getOrder_status().equals("Waiting"))
                    important.add(order);
                else
                    notImportant.add(order);
            }
            else notImportant.add(order);
        }

        model.addAttribute("veryImportant", veryImportant);
        model.addAttribute("important", important);
        model.addAttribute("notImportant", notImportant);

        return "importantOrders";
    }

    //==================================================================================================================
    //                                          CREATE ORDER
    //==================================================================================================================

    // creates an order for the logged-in user, uses products currently in a shopping cart
    @PostMapping("/create")
    public String createOrder(RedirectAttributes redirectAttributes)
    {
        if(shoppingCart.isEmpty())
        {
            redirectAttributes.addFlashAttribute("emptyCart", true);
            return "redirect:/products";
        }

        // retrieving logged use
        Integer userId =  userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow().getId();


        //calculate cost of the order from the prices and discounts of products in the shopping cart

        Integer allCost = 0;

        for(var productId : shoppingCart)
        {
            productService.setActiveProduct(productId);
            ProductDto p = productService.readOne().orElseThrow();

            if(p.getDiscount() != null) {
                double tmp = p.getPrice() - (p.getPrice() * (p.getDiscount() / 100.0));
                allCost += (int) tmp;
            }
            else
                allCost += p.getPrice();

        }
        //setting up order
        OrderDto order = new OrderDto();

        order.setBuyer_id(userId);
        order.setDate_of_order(LocalDateTime.now());
        order.setOrder_status("Waiting");
        order.setCost(allCost);
        order.setItems_id(shoppingCart);

        orderService.create(order);


        shoppingCart.clear();

        redirectAttributes.addFlashAttribute("createOrderSuccess", true);

        return "redirect:/products";
    }

    // adds single product to the shopping cart
    @PostMapping("/add/{productId}")
    public String addProductToCart(@PathVariable("productId") Integer productId, RedirectAttributes redirectAttributes)
    {
        shoppingCart.add(productId);

        productService.setActiveProduct(productId);
        String productName = productService.readOne().orElseThrow().getProduct_name();

        redirectAttributes.addFlashAttribute("addedToCartTrue", true);
        redirectAttributes.addFlashAttribute("addedToCart", productName);

        return "redirect:/products";
    }

    //==================================================================================================================
    //                                          EDIT ORDER
    //==================================================================================================================

    // Edit order status
    @GetMapping("/edit")
    public String editOrderStatus(@RequestParam long id, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedUser", authentication.getName());

        //possible statuses of the orded
        List<String> statuses = Arrays.asList("Preparing", "Shipped", "Waiting", "Arrived");


        orderService.setActiveOrder(id);
        model.addAttribute("order",orderService.readOne().orElseThrow());
        model.addAttribute("statuses",statuses);
        model.addAttribute("selectedOption", orderService.readOne().orElseThrow().getOrder_status());

        return "orderEdit";
    }
    // Updating the order status
    @PostMapping("/edit")
    public String updateStatus(@ModelAttribute("order") OrderDto order, RedirectAttributes redirectAttributes)
    {
        orderService.setActiveOrder(order.getId());
        orderService.update(order);

        String changeStatusMsg = "Order " + order.getId() +  " status was successfully changed to " + order.getOrder_status();

        redirectAttributes.addFlashAttribute("changeStatus", true);
        redirectAttributes.addFlashAttribute("changeStatusMsg", changeStatusMsg);

        return "redirect:/orders";
    }



}
