package cz.cvut.fit.tjv.music_store.bussiness;

import cz.cvut.fit.tjv.music_store.dao.ProductRepository;
import cz.cvut.fit.tjv.music_store.dao.StoreUserRepository;
import cz.cvut.fit.tjv.music_store.domain.Product;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService extends AbstractCrudService<Product,Integer>{

    ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {

        super(productRepository);
        this.productRepository = productRepository;
    }

    public Collection<Product> readLikedProducts (StoreUser user) {return productRepository.findProductsByLikedByContains(user);}

}
