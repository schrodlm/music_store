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
import java.util.function.Function;
import java.util.stream.StreamSupport;

public abstract class AbstractCrudController<E extends DomainEntity<ID>,D, ID> {

    protected AbstractCrudService<E,ID> service;

    protected Function<E,D> toDtoConvertor;

    protected Function<D,E> toEntityConvertor;

    /*
    Controller
     */

    public AbstractCrudController(AbstractCrudService<E,ID> service, Function<E,D> toDtoConvertor, Function<D,E> toEntityConvertor ){

        this.service = service;
        this.toDtoConvertor = toDtoConvertor;
        this.toEntityConvertor = toEntityConvertor;
    }

    //RequestBody means that e
    @PostMapping
    public D create(@RequestBody D e){
        return toDtoConvertor.apply(service.create(toEntityConvertor.apply(e)));
    }

    @GetMapping
    public Collection<D> readAll(){
        return StreamSupport.stream(service.readAll().spliterator(), false).map(toDtoConvertor).toList();
    }

    @PutMapping ("/{id}")
    public void update(D e, @PathVariable ID id)
    {
        service.update(toEntityConvertor.apply(e));
    }

    //DELETE /users/test
    //PathVariable just says that ID id should be passed to /{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id){
        service.deleteById(id);
    }
}
