package cz.cvut.tjv.music_store_client.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class loginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
