package cz.cvut.fit.tjv.music_store.api;

import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class StoreUserController extends  AbstractCrudController<StoreUser, Integer> {
    public StoreUserController(StoreUserService service){
        super(service);
    }
}
