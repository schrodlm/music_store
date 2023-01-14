package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.api.model.StoreUserDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.UserToDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.UserToEntity;
import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class StoreUserController extends  AbstractCrudController<StoreUser, StoreUserDto, Integer> {
    public StoreUserController(StoreUserService service, UserToDto toDto, UserToEntity toEntity){
        super(service, toDto, toEntity);
        this.service = service;
        this.toDto = toDto;
        this.toEntity= toEntity;
        }
    StoreUserService service;
    UserToDto toDto;
    UserToEntity toEntity;


    @GetMapping("username/{username}")
    public StoreUserDto readOne(@PathVariable String username){
        return toDtoConvertor.apply(service.readByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
