package cz.cvut.fit.tjv.music_store.api.model.convertor;

import cz.cvut.fit.tjv.music_store.api.model.ProductDto;
import cz.cvut.fit.tjv.music_store.domain.Product;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductToDto implements Function<Product, ProductDto> {

    @Override
    public ProductDto apply(Product product)
    {
        ProductDto ret = new ProductDto();
        ret.setId(product.getId());
        ret.setProduct_name(product.getProduct_name());
        ret.setPrice(product.getPrice());
        ret.setDiscount(product.getDiscount());
        return ret;
    }
}
