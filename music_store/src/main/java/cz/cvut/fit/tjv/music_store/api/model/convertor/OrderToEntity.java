package cz.cvut.fit.tjv.music_store.api.model.convertor;

import cz.cvut.fit.tjv.music_store.api.model.OrderDto;
import cz.cvut.fit.tjv.music_store.bussiness.ProductService;
import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.Order;
import cz.cvut.fit.tjv.music_store.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.function.Function;

@Component
public class OrderToEntity implements Function<OrderDto, Order> {
    @Autowired
    StoreUserService userService;
    @Autowired
    ProductService productService;

    @Override
    public Order apply(OrderDto orderDto)
    {


        ArrayList<Product> p = new ArrayList<>();

        for(Integer id : orderDto.getItems_id())
        {
            p.add(productService.readById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item does not exist")));
        }
        if(userService.readById(orderDto.getBuyer_id()) == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author username missing");

        if(orderDto.getItems_id().isEmpty() == true)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Items in this order are empty");



        return new Order(
                orderDto.getId(),
                userService.readById(orderDto.getBuyer_id()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author username does not exist")),
                p,
                orderDto.getInvoice(),
                orderDto.getCost(),
                orderDto.getOrder_status(),
                orderDto.getDate_of_order()
        );
    }
}
