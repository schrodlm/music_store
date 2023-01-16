package cz.cvut.fit.tjv.music_store.bussiness;

import cz.cvut.fit.tjv.music_store.dao.ProductRepository;
import cz.cvut.fit.tjv.music_store.domain.Product;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository repository;

    @Test
    void readLikedProducts() {

        Product p1 = new Product(1, "Test1", 30, 30);
        Product p2 = new Product(2, "Test2", 40, 40);
        Product p3 = new Product(3, "Test3", 50, 50);

        StoreUser user = new StoreUser(1, "User", "password", "USER", "Name", "Lastname", "Address", "email@email.com", "Address 43");
        Collection<Product> likedProducts = new ArrayList<>();
        likedProducts.add(p1);
        likedProducts.add(p2);

        user.setLikedProducts(likedProducts);

        productService.readLikedProducts(user);

        Mockito.verify(repository, Mockito.times(1)).findProductsByLikedByContains(user);

        Assertions.assertTrue(likedProducts.contains(p1));
        Assertions.assertTrue(likedProducts.contains(p2));
        Assertions.assertTrue(!likedProducts.contains(p3));

    }
}
