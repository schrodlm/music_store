package cz.cvut.fit.tjv.music_store.bussiness;

import cz.cvut.fit.tjv.music_store.dao.StoreUserRepository;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.stereotype.Service;

@Service
public class StoreUserService extends AbstractCrudService<StoreUser, Integer>{
    public StoreUserService(StoreUserRepository userRepository){
        super(userRepository);
    }
}
