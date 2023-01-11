package cz.cvut.fit.tjv.music_store.api.model.convertor;

import cz.cvut.fit.tjv.music_store.api.model.StoreUserDto;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToEntity implements Function<StoreUserDto,StoreUser> {
    @Override
    public StoreUser apply(StoreUserDto userDto)
    {
        return new StoreUser(userDto.getId(), userDto.getUsername(), userDto.getName(), userDto.getSurname(), userDto.getAddress(), userDto.getEmail(), userDto.getCredit_card());
    }
}
