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
import io.swagger.annotations.*;
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


    public AbstractCrudController(AbstractCrudService<E,ID> service, Function<E,D> toDtoConvertor, Function<D,E> toEntityConvertor ){

        this.service = service;
        this.toDtoConvertor = toDtoConvertor;
        this.toEntityConvertor = toEntityConvertor;
    }

    /*
        CREATE
    */
    @PostMapping
    @ApiOperation(value = "Creates new entity")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 409, message = "Entity already exists") })
    public D create(@ApiParam(name="e", value="Entity")@RequestBody D e){
        try {
            return toDtoConvertor.apply(service.create(toEntityConvertor.apply(e)));
        }
        catch(EntityStateException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    /*
        GET ALL
    */
    @GetMapping
    @ApiOperation(value = "Return all created instances of that Entity")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK")})
    public Collection<D> readAll(){
        return StreamSupport.stream(service.readAll().spliterator(), false).map(toDtoConvertor).toList();
    }

    /*
        GET ONE
    */
    @GetMapping("/{id}")
    @ApiOperation(value = "Returns entity based on ID", notes="ID is passed as a path variable")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found") })
    public D readOne(@ApiParam(name = "id", value = "ID of that entity", required = true) @PathVariable ID id){

        try{
            return toDtoConvertor.apply(service.readById(id).orElseThrow());
        }
        catch(InvalidStateException ex)
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    /*
        UPDATE
     */
    @PutMapping ("/{id}")
    @ApiOperation(value= "Updates Entity specified by ID", notes="ID is passed as a path variable")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found"),
            @ApiResponse(code = 409, message = "Entity is in invalid state")})
    public D update(@ApiParam(name = "e", value="updated version of that entity", required = true) @RequestBody D e, @ApiParam(name = "id", value="ID of entity to be updated", required = true) @PathVariable ID id)
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

    /*
        DELETE
    */
    @DeleteMapping("/{id}")
    @ApiOperation(value= "Deletes Entity specified by ID", notes="ID is passed as a path variable")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource not found") })
    public void delete(@PathVariable ID id){
        try
        {
            service.deleteById(id);
        }
        catch(InvalidStateException ex)
        {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
