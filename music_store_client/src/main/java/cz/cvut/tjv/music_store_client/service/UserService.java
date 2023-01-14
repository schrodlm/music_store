package cz.cvut.tjv.music_store_client.service;


import cz.cvut.tjv.music_store_client.client.ProductClient;
import cz.cvut.tjv.music_store_client.client.UserClient;
import cz.cvut.tjv.music_store_client.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserService {
    private UserClient userClient;

    private boolean isUserActive = false;

    public UserService(UserClient userClient){this.userClient = userClient;}


    public UserDto create(UserDto userDto){
        setActiveUser(userDto.getId());
        return userClient.create(userDto);
    }

    public void delete(){
        userClient.delete();
    }

    public void deleteByUsername(String username)
    {
        setActiveUser(username);
        delete();
    }

    public void setActiveUser(long id)
    {
        userClient.setActiveUser(id);
        isUserActive = true;
    }

    public void setActiveUser(String username)
    {
        userClient.setActiveUser(username);
        isUserActive = true;
    }

    public Optional<UserDto> readOne() { return userClient.readOne();}

    public Collection<UserDto> readAll(){
        return userClient.readAll();
    }
    public Optional<UserDto> findById(long id)
    {
        setActiveUser(id);
        return readOne();
    }

    public Optional<UserDto> findByUsername(String username)
    {
        setActiveUser(username);
        return readOne();
    }

    public void update(UserDto userDto)
    {
        setActiveUser(userDto.getId());
        userClient.updateOne(userDto);
    }
}
