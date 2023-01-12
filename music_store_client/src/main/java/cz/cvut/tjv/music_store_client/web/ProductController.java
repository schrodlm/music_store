package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.dto.ProductDto;
import cz.cvut.tjv.music_store_client.service.ProductService;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String products(Model model)
    {
        model.addAttribute("allProducts", productService.readAll());
        return "products";
    }


    //===========================
    //EDIT
    //===========================
    @GetMapping("/edit")
    public String editProduct(@RequestParam long id, Model model)
    {
        productService.setActiveProduct(id);
        model.addAttribute("product",productService.readOne().orElseThrow());
        return "productEdit";
    }

    @PostMapping("/edit")
    public String submitEditProduct(@Valid @ModelAttribute ProductDto productDto, Model model)
    {
        productService.setActiveProduct(productDto.getId());
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
}
