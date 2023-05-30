package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.repositories.UserRepository;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private RecipeService recipeService;

    private ReviewService reviewService;

    private UserRepository userRepository;

    @Autowired
    public HomeController(RecipeService recipeService,UserRepository userRepository,ReviewService reviewService) {
        this.recipeService = recipeService;
        this.userRepository=userRepository;
        this.reviewService=reviewService;
    }

    @GetMapping(value = "/")
    public String home(Model model) {
        return "home";
    }
}
