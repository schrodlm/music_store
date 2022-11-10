package cz.cvut.fit.tjv.music_store.api;

/*
    Since we follow rules of RESTful APIs
    -> all of our Entities should be able to be Created, Read, Updated and Removed
    Since all of our classes in api layer follow these rules it is natural to have a super class

    This class will also be used to map each function to its own HTTP Method (POST, PUT, PATCH, DELETE, ...)
 */

import cz.cvut.fit.tjv.music_store.bussiness.AbstractCrudService;
import cz.cvut.fit.tjv.music_store.domain.DomainEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

public abstract class AbstractCrudController<E extends DomainEntity<ID>, ID> {

    protected AbstractCrudService<E,ID> service;

    /*
    Controller
     */

    public AbstractCrudController(AbstractCrudService<E,ID> service){
        this.service = service;
    }

    //RequestBody means that e
    @PostMapping
    public E create(@RequestBody E e){
        return service.create(e);
    }

    @GetMapping
    public Iterable<E> readAll(){
        return service.readAll();
    }

    @PutMapping ("/{id}")
    public void update(E e, @PathVariable ID id)
    {
        service.update(e);
    }

    //DELETE /users/test
    //PathVariable just says that ID id should be passed to /{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id){
        service.deleteById(id);
    }
}
