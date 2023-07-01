package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.UserDto;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

//    @Autowired
//    OAuth2AuthorizedClientService clientService;


    @GetMapping("/login")
    public String showLoginForm(Model model,HttpServletRequest request) {
        System.out.println("get eseguita");
        model.addAttribute("user", new UserDto());
        model.addAttribute("request",request);
        return "login";
    }

    @PostMapping("/login")
    public String loginMethod(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, HttpSession session, Model model, HttpServletRequest request) {
        System.out.println("post eseguita");
        if (bindingResult.hasErrors()) {
            model.addAttribute("request",request);
            return "login";
        }

        Optional<User> user = userService.getUserByUsername(userDto.getUsername());
        System.out.println(user);


        if (!user.isPresent() || !userService.passwordMatch(user.get(), userDto.getPassword())) {
            model.addAttribute("loginError", true);
            model.addAttribute("request",request);
            return "login";
        }

        if (!user.get().isEnabled()) {
            model.addAttribute("emailNotVerified", true);
            System.out.println("--------------");
            System.out.println("not verified");
            System.out.println("--------------");
            model.addAttribute("request",request);
            return "login";
        }

        session.setAttribute("user", user);
        model.addAttribute("request",request);

        return "redirect:/home";
    }

}