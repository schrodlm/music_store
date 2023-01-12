package cz.cvut.tjv.music_store_client.service;

import cz.cvut.tjv.music_store_client.client.ProductClient;
import cz.cvut.tjv.music_store_client.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ProductService {
    private ProductClient productClient;
    private boolean isProductActive = false;

    public ProductService(ProductClient productClient) {
        this.productClient = productClient;
    }

    public void create(ProductDto post) {
        productClient.create(post);
    }

    public void setActiveProduct(long id) {
        productClient.setActivePost(id);
        isProductActive = true;
    }

    public boolean isProductActive() {
        return isProductActive;
    }

    public Optional<ProductDto> readOne() {return productClient.readOne();}
    public Collection<ProductDto> readAll() {return productClient.readAll();}

    public void update(ProductDto productDto)
    {
        productClient.updateOne(productDto);
    }

}
