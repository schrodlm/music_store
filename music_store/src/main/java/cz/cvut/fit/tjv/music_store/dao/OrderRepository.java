package cz.cvut.fit.tjv.music_store.dao;

import cz.cvut.fit.tjv.music_store.domain.Order;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

/*
    This repository takes care of save,read,update and delete operations to Order Entity, it uses CrudRepository provided
    by SpringBoot which supply us with all this functionality out of gate
 */
public interface OrderRepository extends CrudRepository<Order,Integer> {

    Collection<Order> findOrdersByBuyer(StoreUser user);
}
