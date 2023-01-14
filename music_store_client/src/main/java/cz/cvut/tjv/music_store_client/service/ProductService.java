package cz.cvut.tjv.music_store_client.service;

import cz.cvut.tjv.music_store_client.client.ProductClient;
import cz.cvut.tjv.music_store_client.dto.ProductDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public ProductDto create(ProductDto productDto) {
        return productClient.create(productDto);
    }

    public void setActiveProduct(long id) {
        productClient.setActiveProduct(id);
        isProductActive = true;
    }

    public boolean isProductActive() {
        return isProductActive;
    }

    public Optional<ProductDto> readOne() {return productClient.readOne();}
    public Collection<ProductDto> readAll() {return productClient.readAll();}

    public void update(ProductDto productDto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        var roles = authentication.getAuthorities();
        productClient.updateOne(productDto);
    }

    public void delete()
    {
        productClient.delete();
    }


}
