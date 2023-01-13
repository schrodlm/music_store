package cz.cvut.fit.tjv.music_store.bussiness;

import cz.cvut.fit.tjv.music_store.dao.StoreUserRepository;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StoreUserService extends AbstractCrudService<StoreUser, Integer>{

    StoreUserRepository userRepository;
    public StoreUserService(StoreUserRepository userRepository){
        super(userRepository);
        this.userRepository = userRepository;
    }


    public Optional<StoreUser> readByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
