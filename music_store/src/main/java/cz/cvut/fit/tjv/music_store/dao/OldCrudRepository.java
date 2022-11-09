package cz.cvut.fit.tjv.music_store.dao;

import cz.cvut.fit.tjv.music_store.domain.DomainEntity;

import java.util.Collection;
import java.util.Optional;


/*
    CrudRepository implements all CRUD operations on our domain entities
    -> this is an interface that all repositories will implement
    !! We do not use this anymore since SpringBoot have its own CrudRepository class
       so this is redundant !!
 */

public interface OldCrudRepository <T extends DomainEntity<ID>, ID> {
    /* CRUD OPERACE */
    //==============================
    T save(T e);

    Boolean existsById(T e);

    Optional<T> findById(ID id);

    Collection<T> findAll();

    Boolean deleteById(ID id);
}
