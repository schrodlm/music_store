package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
