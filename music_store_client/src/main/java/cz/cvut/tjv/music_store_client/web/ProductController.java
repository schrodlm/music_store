package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.dto.ProductDto;
import cz.cvut.tjv.music_store_client.dto.UserDto;
import cz.cvut.tjv.music_store_client.service.ProductService;
import cz.cvut.tjv.music_store_client.service.UserService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    private UserService userService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String products(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("allProducts", productService.readAll());
        return "products";
    }


    //===========================
    //EDIT
    //===========================
    @GetMapping("/edit")
    public String editProduct(@RequestParam long id, Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());

        productService.setActiveProduct(id);
        model.addAttribute("product",productService.readOne().orElseThrow());
        return "productEdit";
    }

    @PostMapping("/edit")
    public String submitEditProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model)
    {

        productService.setActiveProduct(productDto.getId());

        if(bindingResult.hasErrors())
        {
            model.addAttribute("error",true);
            model.addAttribute("errorMsg", bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("product", productService.readOne().orElseThrow());
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



    //===========================
    //CREATE
    //===========================
    @GetMapping("/create")
    public String createProduct(Model model)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("product", new ProductDto());
        return "productCreate";
    }


    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult bindingResult, Model model)
    {

        if(bindingResult.hasErrors())
        {
            model.addAttribute("error",true);
            model.addAttribute("errorMsg", bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("product", productDto);
            return "productCreate";
        }

        //this one has updated ID by the autogenerated value in backend
        ProductDto tmp = productService.create(productDto);

        return "redirect:/products";
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam long id, RedirectAttributes redirectAttributes)
    {



        productService.setActiveProduct(id);
        ProductDto tmp = productService.readOne().orElseThrow();
        String s = tmp.getProduct_name() + " was deleted!";
        redirectAttributes.addFlashAttribute("deleted", true);
        redirectAttributes.addFlashAttribute("deleteMessage", s);



        productService.delete();


        return "redirect:/products";
    }

    @GetMapping("/liked")
    public String showLikedProducts(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());

        model.addAttribute("likedProducts", productService.readAllFavourites());
        return "likedProducts";

    }

    @PostMapping("/liked/{productId}")
    public String removeLikedProduct(@PathVariable("productId") Integer productId,Model model)
    {
        UserDto loggedUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

        Collection<Integer> newLiked = loggedUser.getLikedProducts();
        newLiked.remove(productId);

        userService.update(loggedUser);

        model.addAttribute("favouriteDeletedSuccess", true);

        return "likedProducts";

    }


}
