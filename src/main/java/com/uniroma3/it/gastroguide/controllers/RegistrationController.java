package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.UserDto;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
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
    public String showRegistrationForm(HttpServletRequest request, Model model) {
        model.addAttribute("request",request);
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserDto user, @NotNull BindingResult result,HttpServletRequest request, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("request",request);
            return "register";
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword", "Password and confirmation password do not match");
            model.addAttribute("request",request);
            return "register";
        }

        Optional<User> userDetails = userService.getUserByUsername(user.getUsername());
        if (userDetails.isPresent()) {
            result.rejectValue("username", "error.user", "Username is already in use");
            model.addAttribute("request",request);

            return "register";
        }

        userDetails = userService.loadByEmail(user.getEmail());
        if (userDetails.isPresent()) {
            result.rejectValue("email", "error.user", "Email is already in use");
            model.addAttribute("request",request);

            return "register";
        }

        Optional<User> userPresent = userService.getUserByUsername(user.getUsername());
        if (userPresent.isPresent()) {
            result.rejectValue("username", "error.user", "This username is taken");
            model.addAttribute("request",request);

            return "register";
        }

        userService.saveUser(user);
        return "redirect:/login";
    }

}
