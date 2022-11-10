package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.bussiness.ProductService;
import cz.cvut.fit.tjv.music_store.domain.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    This class is used for HTTP Request

 */
@RestController
@RequestMapping("/products")
public class ProductController extends AbstractCrudController<Product, Integer> {

    ProductController(ProductService service){
        super(service);
    }
}
