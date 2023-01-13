package cz.cvut.tjv.music_store_client.service;

import cz.cvut.tjv.music_store_client.client.UserClient;
import cz.cvut.tjv.music_store_client.config.CustomUserDetails;
import cz.cvut.tjv.music_store_client.dto.UserDto;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    UserService userService;


    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {



        UserDto user = userService.findByUsername(username).orElseThrow( () -> {throw new UsernameNotFoundException("User not found");});

        return new CustomUserDetails(user.getUsername(),user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));

    }


}
