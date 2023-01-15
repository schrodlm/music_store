package cz.cvut.tjv.music_store_client.service;

import cz.cvut.tjv.music_store_client.client.OrderClient;
import cz.cvut.tjv.music_store_client.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class OrderService {

    private OrderClient orderClient;
    private boolean isOrderActive = false;

    public OrderService(OrderClient orderClient)
    {
        this.orderClient = orderClient;
    }

    public void setActiveOrder(long id)
    {
        orderClient.setActiveOrder(id);
        isOrderActive = true;
    }

    public Optional<OrderDto> readOne(){
        return orderClient.readOne();
    }
    public Collection<OrderDto> readAll() {return orderClient.readAll();}

    public void delete()
    {
        orderClient.delete();
    }

    public Collection<OrderDto> readUserOrders(long userId)
    {
        return orderClient.readUserOrders(userId);
    }


}
