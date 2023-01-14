package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.config.CustomUserDetailsManager;
import cz.cvut.tjv.music_store_client.dto.UserDto;
import cz.cvut.tjv.music_store_client.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String users(Model model){return "users";}


    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data
        model.addAttribute("user", new UserDto());
        return "userRegister";
    }



    // handler method to handle user registration form submit request
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        if(userService.findByUsername(userDto.getUsername()) == null)
            result.rejectValue("username", null, "THis username is already registered");

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "redirect:/index";
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userDto.setRole("USER");


        userService.create(userDto);
        return "redirect:/users/register?success";
    }
}

