package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
