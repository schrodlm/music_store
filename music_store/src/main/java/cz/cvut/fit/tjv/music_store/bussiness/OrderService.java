package cz.cvut.fit.tjv.music_store.bussiness;

import cz.cvut.fit.tjv.music_store.dao.OrderRepository;
import cz.cvut.fit.tjv.music_store.domain.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends AbstractCrudService<Order,Integer>{
    public OrderService(OrderRepository orderRepository){
        super(orderRepository);
    }
}
