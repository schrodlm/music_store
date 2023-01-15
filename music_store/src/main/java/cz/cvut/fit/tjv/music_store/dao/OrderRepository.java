package cz.cvut.fit.tjv.music_store.dao;

import cz.cvut.fit.tjv.music_store.domain.Order;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/*
    This repository takes care of save,read,update and delete operations to Order Entity, it uses CrudRepository provided
    by SpringBoot which supply us with all this functionality out of gate
 */
@Repository
@Primary
public interface OrderRepository extends JpaRepository<Order,Integer> {

    Collection<Order> findOrdersByBuyer(StoreUser user);

    @Query("SELECT o FROM Order o WHERE o.status IN ('Waiting', 'Preparing') ORDER BY o.date DESC, o.cost ASC")
    Collection<Order> findByStatusInWaitingOrPreparing();
}
