package cz.cvut.fit.tjv.music_store.bussiness;

import cz.cvut.fit.tjv.music_store.dao.ProductRepository;
import cz.cvut.fit.tjv.music_store.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractCrudService<Product,Integer>{
    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }
}
