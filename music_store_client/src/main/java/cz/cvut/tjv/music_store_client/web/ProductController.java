package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.dto.ProductDto;
import cz.cvut.tjv.music_store_client.dto.UserDto;
import cz.cvut.tjv.music_store_client.service.ProductService;
import cz.cvut.tjv.music_store_client.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;


/*
    Handles all request for Order Entity
*/
@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    private UserService userService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    //==================================================================================================================
    //                                          SHOWING PRODUCTS
    //==================================================================================================================

    //Show all products from the database
    @GetMapping
    public String products(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("allProducts", productService.readAll());


        //user is admin and edit product and delete product buttons will be shown
        // -> otherwise add to cart and add to favourite will be shown - for the normal users
        boolean isAdmin = false;

        if(userService.findByUsername(authentication.getName()).orElseThrow().getRole().equals("ADMIN"))
        {
            isAdmin = true;
        }
        model.addAttribute("isAdmin", isAdmin);


            return "products";
    }


    //==================================================================================================================
    //                                          EDIT PRODUCTS
    //==================================================================================================================

    // Edit product
    @GetMapping("/edit")
    public String editProduct(@RequestParam long id, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());

        productService.setActiveProduct(id);
        model.addAttribute("product",productService.readOne().orElseThrow());
        return "productEdit";
    }

    // Submit edited product
    @PostMapping("/edit")
    public String submitEditProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model)
    {

        productService.setActiveProduct(productDto.getId());

        if(bindingResult.hasErrors())
        {
            model.addAttribute("product", productDto);
            return "productEdit";
        }

        try {
            productService.update(productDto);
        }
        catch (BadRequestException e)
        {
            model.addAttribute("error",true);
            model.addAttribute("errorMsg", e.getMessage());
        }
        model.addAttribute("product", productDto);
        return "productEdit";
    }



    //==================================================================================================================
    //                                          CREATE PRODUCTS
    //==================================================================================================================
    @GetMapping("/create")
    public String createProduct(Model model)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("product", new ProductDto());
        return "productCreate";
    }

    // Submit created order
    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult, Model model)
    {

        if(bindingResult.hasErrors())
        {
            model.addAttribute("product", productDto);
            return "productCreate";
        }

        try {
            productService.create(productDto);
        }
        catch (BadRequestException e)
        {
            model.addAttribute("error",true);
            model.addAttribute("errorMsg", e.getMessage());
            return "productCreate";
        }

        return "redirect:/products";
    }

    //==================================================================================================================
    //                                          DELETE PRODUCTS
    //==================================================================================================================
    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam long id, RedirectAttributes redirectAttributes)
    {
        productService.setActiveProduct(id);
        ProductDto toDel = productService.readOne().orElseThrow();

        for(UserDto user : userService.readAll())
        {
            for( ProductDto p : productService.readAllFavourites(user.getId()))
            {
                // didn't implement deleting products that are liked by someone, this is temporary solution
                if(p.getId() == toDel.getId())
                {
                    String s = "You cannot delete product that is liked by someone! (FOR NOW)";
                    redirectAttributes.addFlashAttribute("deleted", true);
                    redirectAttributes.addFlashAttribute("deleteMessage", s);
                    return "redirect:/products";
                }
            }

        }
        productService.setActiveProduct(id);
        ProductDto tmp = productService.readOne().orElseThrow();
        String s = tmp.getProduct_name() + " was deleted!";
        redirectAttributes.addFlashAttribute("deleted", true);
        redirectAttributes.addFlashAttribute("deleteMessage", s);



        productService.delete();


        return "redirect:/products";
    }

    //==================================================================================================================
    //                                          LIKED PRODUCTS
    //==================================================================================================================

    // show products liked by specific user
    @GetMapping("/liked/{username}")
    public String showLikedProducts(@PathVariable("username") String username, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("likedUsername", username);


        userService.setActiveUser(username);
        long userId = userService.readOne().orElseThrow().getId();

        model.addAttribute("likedProducts", productService.readAllFavourites(userId));
        return "likedProducts";

    }
    // removes liked product of a specific user
    @PostMapping("/liked/{username}/{productId}")
    public String removeLikedProduct(@PathVariable("username") String username, @PathVariable("productId") Integer productId,RedirectAttributes redirectAttributes)
    {
        UserDto userToDeleteLikedFrom = userService.findByUsername(username).orElseThrow();

        Collection<Integer> newLiked = userToDeleteLikedFrom.getLikedProducts();
        newLiked.remove(productId);

        userService.update(userToDeleteLikedFrom);

        redirectAttributes.addFlashAttribute("favouriteDeletedSuccess", true);

        return "redirect:/products/liked/" + username;

    }


}
