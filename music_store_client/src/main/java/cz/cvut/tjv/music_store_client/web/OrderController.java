package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.dto.OrderDto;
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

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @GetMapping
    public String showAllOrders(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());


        model.addAttribute("allOrders", orderService.readAll());
        return "orders";
    }

    @DeleteMapping("/delete/{id}")
    public String orderFinished(@PathVariable long id, RedirectAttributes redirectAttributes)
    {
        orderService.setActiveOrder(id);
        OrderDto tmp = orderService.readOne().orElseThrow();
        String s = tmp.getId() + "was successfully finished!";
        redirectAttributes.addFlashAttribute("deleted",true);
        redirectAttributes.addFlashAttribute("deleteMessage",s);

        orderService.delete();

        return "redirect:/orders";

    }

    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;
    @GetMapping("/user/{username}")
    public String showMyOrders(@PathVariable String username, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());

        Integer userId = userService.findByUsername(username).orElseThrow().getId();


        model.addAttribute("allOrders", orderService.readUserOrders(userId));

        return "userOrders";
    }




}
