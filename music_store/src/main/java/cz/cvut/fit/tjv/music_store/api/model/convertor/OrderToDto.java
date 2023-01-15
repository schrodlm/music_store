package cz.cvut.fit.tjv.music_store.api.model.convertor;

import cz.cvut.fit.tjv.music_store.api.model.OrderDto;
import cz.cvut.fit.tjv.music_store.domain.Order;
import cz.cvut.fit.tjv.music_store.domain.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.StreamSupport;

@Component
public class OrderToDto implements Function<Order, OrderDto> {
    @Override
    public OrderDto apply(Order order)
    {
        OrderDto ret = new OrderDto();
        ret.setId(order.getId());
        ret.setBuyer_id(order.getBuyer().getId());

        //setting Array of item ids
        ArrayList<Integer> ids = new ArrayList<>();
        for(Product p : order.getBoughtItems())
        {
            ids.add(p.getId());
        }
        ret.setItems_id(ids);
        ret.setCost(order.getCost());
        ret.setOrder_status(order.getStatus());
        ret.setDate_of_order(order.getDate());

        return ret;
    }
}
