package cz.cvut.fit.tjv.music_store.dao;

import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


/*
    This repository takes care of save,read,update and delete operations to StoreUser Entity, it uses CrudRepository provided
    by SpringBoot which supply us with all this functionality out of gate
 */
public interface StoreUserRepository extends CrudRepository<StoreUser, Integer> {
    Optional<StoreUser> findByUsername(String username);
}
