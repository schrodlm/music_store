package cz.cvut.fit.tjv.music_store.api.model.convertor;

import cz.cvut.fit.tjv.music_store.api.model.StoreUserDto;
import cz.cvut.fit.tjv.music_store.bussiness.ProductService;
import cz.cvut.fit.tjv.music_store.bussiness.StoreUserService;
import cz.cvut.fit.tjv.music_store.domain.Product;
import cz.cvut.fit.tjv.music_store.domain.StoreUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;

@Component
public class UserToEntity implements Function<StoreUserDto,StoreUser> {

    @Autowired
    ProductService productService;

    @Override
    public StoreUser apply(StoreUserDto userDto)
    {
        ArrayList<Product> tmp = new ArrayList<>();
        if(userDto.getLikedProducts() != null) {
            for (var id : userDto.getLikedProducts()) {
                tmp.add(productService.readById(id).orElseThrow());
            }
        }
        StoreUser newUser = new StoreUser(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getRole(),userDto.getName(), userDto.getSurname(), userDto.getAddress(), userDto.getEmail(), userDto.getCredit_card());

        if(!tmp.isEmpty())
            newUser.setLikedProducts(tmp);

        return newUser;
    }
}
