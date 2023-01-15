package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.api.model.OrderDto;
import cz.cvut.fit.tjv.music_store.api.model.ProductDto;
import cz.cvut.fit.tjv.music_store.api.model.StoreUserDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.OrderToDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.OrderToEntity;
import cz.cvut.fit.tjv.music_store.api.model.convertor.ProductToDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.ProductToEntity;
import cz.cvut.fit.tjv.music_store.bussiness.OrderService;
import cz.cvut.fit.tjv.music_store.bussiness.ProductService;
import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.Order;
import cz.cvut.fit.tjv.music_store.domain.Product;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractCrudController<Order, OrderDto, Integer> {

    public OrderController(OrderService service, OrderToDto toDto, OrderToEntity toEntity){
        super(service,toDto,toEntity);
        this.service = service;
        this.toDto = toDto;
        this.toEntity = toEntity;
    }

    @Autowired
    StoreUserService storeUserService;
    OrderService service;
    OrderToDto toDto;
    OrderToEntity toEntity;


    @GetMapping("/user/{id}")
    public Collection<OrderDto> findOrdersByUser(@PathVariable Integer id)
    {

        StoreUser user = storeUserService.readById(id).orElseThrow();

        Collection<OrderDto> orders = new ArrayList<>();

        for(var order : service.findOrdersByUser(user))
        {
            orders.add(toDto.apply(order));
        }

        return orders;

    }
}
