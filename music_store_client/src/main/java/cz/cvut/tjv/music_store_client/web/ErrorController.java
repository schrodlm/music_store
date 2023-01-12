package cz.cvut.tjv.music_store_client.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    public String error(Model model)
    {
        return "error";
    }
}
