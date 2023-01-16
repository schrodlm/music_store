package cz.cvut.fit.tjv.music_store.api;

/*
    Since we follow rules of RESTful APIs
    -> all of our Entities should be able to be Created, Read, Updated and Removed
    Since all of our classes in api layer follow these rules it is natural to have a super class

    This class will also be used to map each function to its own HTTP Method (POST, PUT, PATCH, DELETE, ...)
 */

import cz.cvut.fit.tjv.music_store.bussiness.AbstractCrudService;
import cz.cvut.fit.tjv.music_store.domain.DomainEntity;
import cz.cvut.fit.tjv.music_store.exceptions.EntityStateException;
import cz.cvut.fit.tjv.music_store.exceptions.InvalidStateException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @ApiOperation(value = "Return all created instances of that Entity")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found") })
    public Collection<D> readAll(){
        return StreamSupport.stream(service.readAll().spliterator(), false).map(toDtoConvertor).toList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns entity based on ID", notes="ID must be passed as a path variable")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found") })
    public D readOne(@PathVariable ID id){

        return toDtoConvertor.apply(service.readById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PutMapping ("/{id}")
    public D update(@RequestBody D e, @PathVariable ID id)
    {
        try {
            return toDtoConvertor.apply(service.update(toEntityConvertor.apply(e), id));
        }
        catch(EntityStateException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        catch(InvalidStateException ex) //ID nesedí v datech
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID in the payload does not match in URI");
        }
    }

    //DELETE /users/test
    //PathVariable just says that ID id should be passed to /{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id){
        service.deleteById(id);
    }
}
