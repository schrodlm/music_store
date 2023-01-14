package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.config.CustomUserDetailsManager;
import cz.cvut.tjv.music_store_client.dto.UserDto;
import cz.cvut.tjv.music_store_client.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String users(Model model){
        model.addAttribute("allUsers", userService.readAll());
        return "users";
    }



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
        userDto.setRole("USER");
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));


        userService.create(userDto);
        return "redirect:/users/register?success";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam String usrname, Model model)
    {
        userService.setActiveUser(usrname);
        model.addAttribute("user", userService.readOne().orElseThrow());
        return "userEdit";
    }

    @PostMapping("/edit")
    public String submitEditUser(@ModelAttribute UserDto userDto, BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("error",true);
            model.addAttribute("errorMsg", bindingResult.getAllErrors().get(0).getDefaultMessage());
            model.addAttribute("user", userService.readOne().orElseThrow());
            return "userEdit";
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        try{
            userService.update(userDto);
        }
        catch (BadRequestException e)
        {
            model.addAttribute("error",true);
            model.addAttribute("errorMsg", e.getMessage());
        }

        model.addAttribute("user", userDto);
        return "redirect:/users";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam long id, RedirectAttributes redirectAttributes )
    {
        userService.setActiveUser(id);
        UserDto tmp = userService.readOne().orElseThrow();
        String s = tmp.getUsername() + "was deleted!";
        redirectAttributes.addFlashAttribute("deleted", true);
        redirectAttributes.addFlashAttribute("deleteMessage", s);

        userService.delete();

        return "redirect:/users";
    }
}

