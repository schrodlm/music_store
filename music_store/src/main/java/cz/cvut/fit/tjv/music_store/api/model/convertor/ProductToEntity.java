package cz.cvut.fit.tjv.music_store.api.model.convertor;

import cz.cvut.fit.tjv.music_store.api.model.ProductDto;
import cz.cvut.fit.tjv.music_store.domain.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductToEntity implements Function<ProductDto, Product> {
    @Override
    public Product apply(ProductDto productDto) {
        return new Product(productDto.getId(),productDto.getProduct_name(), productDto.getPrice(), productDto.getDiscount());
    }
}
