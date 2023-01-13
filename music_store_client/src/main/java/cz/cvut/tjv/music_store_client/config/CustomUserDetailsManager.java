package cz.cvut.tjv.music_store_client.config;

import cz.cvut.tjv.music_store_client.dto.UserDto;
import cz.cvut.tjv.music_store_client.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsManager implements UserDetailsManager {

    @Autowired
    UserService service;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void createUser(UserDetails user)
    {
        UserDto newUser = new UserDto();

        newUser.setUsername(user.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setRole(user.getAuthorities().toString());
        service.create(newUser);

    }

    @Override
    public void updateUser(UserDetails user) {
        UserDto existingUser = service.findByUsername(user.getUsername()).orElseThrow();
        existingUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        existingUser.setRole(user.getAuthorities().toString());
        service.create(existingUser);
    }

    @Override
    public void deleteUser(String username) {
        service.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        //nedodelane
        }

    @Override
    public boolean userExists(String username)
    {
        if(service.findByUsername(username) != null) return true;

        return false;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {

        UserDto user = service.findByUsername(username).orElseThrow( () -> {throw new UsernameNotFoundException("User not found");});

        return new CustomUserDetails(user.getUsername(),user.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));

    }
}
