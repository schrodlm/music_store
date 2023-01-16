package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.api.model.ProductDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.ProductToDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.ProductToEntity;
import cz.cvut.fit.tjv.music_store.api.model.convertor.UserToDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.UserToEntity;
import cz.cvut.fit.tjv.music_store.bussiness.ProductService;
import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.Product;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

/*
    This class is used for HTTP Request

 */
@RestController
@RequestMapping("/products")
public class ProductController extends AbstractCrudController<Product, ProductDto, Integer> {

    ProductController(ProductService service, ProductToDto toDto, ProductToEntity toEntity){
        super(service, toDto, toEntity);
        this.service = service;
        this.toDto = toDto;
        this.toEntity= toEntity;
    }
    ProductToDto toDto;
    ProductToEntity toEntity;
    ProductService service;

    @Autowired
    StoreUserService userService;

     /*
        FINDS ALL PRODUCTS LIKED BY USER
     */
    @GetMapping("{id}/liked")
    @ApiOperation(value = "Find all liked products of user specified by ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found") })
    public Collection<ProductDto> showLikedProducts(@ApiParam(name = "id", value = "ID of that user", required = true)@PathVariable Integer id)
    {

        Collection<ProductDto> tmp = new ArrayList<>();

        for(Product p : service.readLikedProducts(userService.readById(id).orElseThrow()))
        {
            tmp.add(toDto.apply(p));
        }
        return tmp;
    }



}
