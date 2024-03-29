package cz.cvut.tjv.music_store_client.web;

import cz.cvut.tjv.music_store_client.config.CustomUserDetailsManager;
import cz.cvut.tjv.music_store_client.dto.UserDto;
import cz.cvut.tjv.music_store_client.service.UserService;
import jakarta.validation.Valid;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.Optional;

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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("allUsers", userService.readAll());
        return "users";
    }



    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        // create model object to store form data

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());
        model.addAttribute("user", new UserDto());
        return "userRegister";
    }



    // handler method to handle user registration form submit request
    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){
        if(userService.findByUsername(userDto.getUsername()).isPresent()) {
            model.addAttribute("usernameExists", true);
            return "userRegister";
        }

        if(result.hasErrors()){
            model.addAttribute("user", userDto);
            return "userRegister";
        }
        userDto.setRole("USER");
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        if(userDto.getUsername().equals("admin"))
            userDto.setRole("ADMIN");

        userService.create(userDto);
        return "redirect:/users/register?success";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam String usrname, Model model)
    {
        userService.setActiveUser(usrname);

        //user doesnt exist
        if(userService.readOne().isEmpty())
            return "redirect:/";

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("loggedUser", authentication.getName());

        //user trying to access this page is not admin and is trying to access different account
        if(!userService.findByUsername(authentication.getName()).orElseThrow().getRole().equals("ADMIN") && !authentication.getName().equals(usrname))
        {

            return "redirect:/";
        }


        model.addAttribute("user", userService.readOne().orElseThrow());
        return "userEdit";
    }

    @PostMapping("/edit")
    public String submitEditUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes)
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("user",userDto);
            return "userEdit";
        }
        //userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        try{
            userService.update(userDto);
        }
        catch (BadRequestException e)
        {
            redirectAttributes.addFlashAttribute("error",true);
            redirectAttributes.addFlashAttribute("errorMsg", e.getMessage());
        }

        model.addAttribute("user", userDto);

        redirectAttributes.addFlashAttribute("changedUserTrue", true);
        redirectAttributes.addFlashAttribute("changedUserMsg", "User details were successfully changed!");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(userService.findByUsername(authentication.getName()).orElseThrow().getRole().equals("ADMIN"))
            return "redirect:/users";
        else
            return "redirect:/";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam long id, RedirectAttributes redirectAttributes )
    {
        userService.setActiveUser(id);
        UserDto tmp = userService.readOne().orElseThrow();

        String s = tmp.getUsername() + " was deleted!";

        boolean isAdmin = false;
        if(tmp.getRole().equals("ADMIN"))
        {
            s = "User is ADMIN and cannot be deleted!";
            isAdmin = true;
        }

        redirectAttributes.addFlashAttribute("deleted", true);
        redirectAttributes.addFlashAttribute("deleteMessage", s);

        if(!isAdmin)
            userService.delete();

        return "redirect:/users";
    }

    @PostMapping("/liked/{id}")
    public String addLike(@PathVariable("id") Integer productId,  Model model, RedirectAttributes redirectAttributes)
    {


        UserDto loggedUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

        //User already has this item as favourite
        if(loggedUser.getLikedProducts().contains(productId))
        {
            redirectAttributes.addFlashAttribute("favouriteFail", true);
            return "redirect:/products";
        }


        Collection<Integer> newLiked = loggedUser.getLikedProducts();
        newLiked.add(productId);

        userService.update(loggedUser);


        redirectAttributes.addFlashAttribute("favouriteSuccess", true);

        return "redirect:/products";
    }


}

