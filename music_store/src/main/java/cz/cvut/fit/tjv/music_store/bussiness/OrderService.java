package cz.cvut.fit.tjv.music_store.bussiness;

import cz.cvut.fit.tjv.music_store.dao.OrderRepository;
import cz.cvut.fit.tjv.music_store.domain.Order;
import cz.cvut.fit.tjv.music_store.domain.Product;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class OrderService extends AbstractCrudService<Order,Integer>{

    OrderRepository orderRepository;
    public OrderService(OrderRepository orderRepository){
        super(orderRepository);

        this.orderRepository = orderRepository;
    }


    public Collection<Order> findOrdersByUser (StoreUser user) {return orderRepository.findOrdersByBuyer(user);}
    public Collection<Order> findByStatusInWaitingOrPreparing(){return orderRepository.findByStatusInWaitingOrPreparing();}
}
