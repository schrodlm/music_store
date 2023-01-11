package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.api.model.StoreUserDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.UserToDto;
import cz.cvut.fit.tjv.music_store.api.model.convertor.UserToEntity;
import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class StoreUserController extends  AbstractCrudController<StoreUser, StoreUserDto, Integer> {
    public StoreUserController(StoreUserService service, UserToDto toDto, UserToEntity toEntity){
        super(service, toDto, toEntity);
    }
}
