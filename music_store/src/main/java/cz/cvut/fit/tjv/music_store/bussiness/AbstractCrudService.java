package cz.cvut.fit.tjv.music_store.bussiness;

import cz.cvut.fit.tjv.music_store.domain.DomainEntity;
import cz.cvut.fit.tjv.music_store.exceptions.EntityStateException;
import cz.cvut.fit.tjv.music_store.exceptions.InvalidStateException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Optional;

/*
    Common superclass for business logic of all entities supporting CRUD operations Create, Read, Update,Delete
 */

public class AbstractCrudService<E extends DomainEntity<K>, K> {

    protected final CrudRepository<E, K> repository;

    protected AbstractCrudService(CrudRepository<E,K> repository){
        this.repository = repository;
    }

    /*
    Attempt to store a new entity
    -> if it already exists throw EntityStateException
     */
    public E create(E entity) throws EntityStateException {
       if(repository.existsById(entity.getId()))
           throw new EntityStateException(entity);

       return repository.save(entity);
    }

    public Optional<E> readById(K id){
        return repository.findById(id);
    }

    public Iterable<E> readAll(){
        return repository.findAll();
    }

    /*
        Attempts to replace already stored entity
     */
    public E update(E entity, K id) throws EntityStateException{
        entity.setId(id);

        if(repository.existsById((entity.getId())))
            return repository.save(entity);

        else
            throw new EntityStateException(entity);

    }
    public void deleteById(K id)
    {
        if(repository.existsById(id))
        repository.deleteById(id);

        else throw new InvalidStateException();
    }

}
