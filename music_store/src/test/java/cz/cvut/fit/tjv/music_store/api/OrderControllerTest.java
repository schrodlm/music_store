package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.api.model.OrderDto;
import cz.cvut.fit.tjv.music_store.bussiness.OrderService;
import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.Order;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTest {
    @MockBean
    private OrderService service;

    @Mock
    private Function<Order, OrderDto> toDto;

    @MockBean
    private StoreUserService storeUserService;

    @Autowired
    private OrderController controller;


    @Test
    public void testFindOrdersByUser() {

        StoreUser user = new StoreUser(1, "User", "password", "USER", "Name", "Lastname", "Address", "email@email.com", "Address 43");
        Order order1 = new Order(1, user, null, 300, "Waiting", LocalDateTime.now());
        Order order2 = new Order(1,user,null, 300, "Preparing", LocalDateTime.now());

        List<Order> orders = Arrays.asList(order1, order2);
        Mockito.when(storeUserService.readById(1)).thenReturn(Optional.of(user));
        Mockito.when(service.findOrdersByUser(user)).thenReturn(orders);
        Mockito.doReturn(new OrderDto()).when(toDto).apply(order1);


        Collection<OrderDto> result = controller.findOrdersByUser(1);
        assertEquals(2, result.size());
    }
}