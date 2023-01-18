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
import cz.cvut.fit.tjv.music_store.exceptions.EntityStateException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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


    /*
        FIND ORDERS BY USER ID
     */
    @GetMapping("/user/{id}")
    @ApiOperation(value = "Returns all orders of the user specified by ID")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The user does not exist") })
    public Collection<OrderDto> findOrdersByUser(@ApiParam(name = "id", value = "ID of the user", required = true)@PathVariable Integer id)
    {
            StoreUser user = storeUserService.readById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

            Collection<OrderDto> orders = new ArrayList<>();

            for (var order : service.findOrdersByUser(user)) {
                orders.add(toDto.apply(order));
            }

            return orders;

    }
    /*
        FIND ORDERS WHERE STATUS IN ("Waiting" | "Preparing")
     */
    @GetMapping("/important")
    @ApiOperation(value = "Returns all orders that have status \"Waiting\" or \"Preparing\"")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
    public Collection<OrderDto> findByStatusInWaitingOrPreparing(){

        Collection<OrderDto> ret = new ArrayList<>();
        for(Order order : service.findByStatusInWaitingOrPreparing())
        {
            ret.add(toDto.apply(order));
        }

        return ret;
    }
}
