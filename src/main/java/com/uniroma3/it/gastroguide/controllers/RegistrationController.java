package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.UserDto;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;


    @ModelAttribute("user")
    public UserDto userDto() {
        return new UserDto();
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") UserDto user, BindingResult result) {
//        System.out.println(user.toString());
        if (result.hasErrors()) {
            return "register";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Password and confirmation password do not match");
        }
        Optional<User> userDetails=null;
        if (userService!=null){
            System.out.println("is not null");
        }
        try {
            userDetails = userService.getUserByUsername(user.getUsername());
            if(userDetails.isPresent()) {
                result.rejectValue("username", "error.user", "Username is already in use");
                return "register";
            }
        } catch (UsernameNotFoundException ex) {}
        userDetails=userService.loadByEmail(user.getEmail());
        if( userDetails.isPresent() ){
            result.rejectValue("email", "error.user", "Email is already in use");
            return "register";
        }
        Optional<User> userPresent=userService.getUserByUsername(user.getUsername());

        if (userPresent.isPresent()){
            result.rejectValue("username", "error.user", "This username is taken");
            return "register";
        }


        userService.saveUser(user);
        return "/login";
    }
}
