package cz.cvut.tjv.music_store_client.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    Main page
 */
@Controller
@RequestMapping("/")
public class IndexController {

    //Main page
    @GetMapping
    public String index(Model model)
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedUser", authentication.getName());

        return "index";
    }
}
