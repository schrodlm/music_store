package cz.cvut.fit.tjv.music_store.api.model.convertor;

import cz.cvut.fit.tjv.music_store.api.model.StoreUserDto;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.StreamSupport;

@Component
public class UserToDto implements Function<StoreUser,StoreUserDto> {

    @Override
    public StoreUserDto apply(StoreUser user)
    {
        ArrayList<Integer> tmp = new ArrayList<>();

        if(user.getLikedProducts() != null) {
            for (var product : user.getLikedProducts()) {
                tmp.add(product.getId());
            }
        }
        StoreUserDto ret = new StoreUserDto();

        ret.setId(user.getId());
        ret.setUsername(user.getUsername());
        ret.setPassword(user.getPassword());
        ret.setRole(user.getRole());
        ret.setName(user.getName());
        ret.setSurname(user.getSurname());
        ret.setAddress(user.getAddress());
        ret.setEmail(user.getEmail());
        ret.setCredit_card(user.getCredit_card());
        ret.setLikedProducts(tmp);
        return ret;
    }

}
