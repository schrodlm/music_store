package cz.cvut.fit.tjv.music_store.dao;

import cz.cvut.fit.tjv.music_store.domain.Order;
import cz.cvut.fit.tjv.music_store.domain.Product;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;


/*
    This repository takes care of save,read,update and delete operations to StoreUser Entity, it uses CrudRepository provided
    by SpringBoot which supply us with all this functionality out of gate
 */
@Repository
@Primary
public interface StoreUserRepository extends JpaRepository<StoreUser, Integer> {
    Optional<StoreUser> findByUsername(String username);


}
