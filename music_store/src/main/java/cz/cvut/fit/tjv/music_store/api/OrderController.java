package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.bussiness.OrderService;
import cz.cvut.fit.tjv.music_store.domain.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController extends AbstractCrudController<Order, Integer> {
    public OrderController(OrderService service){ super(service);}

}
