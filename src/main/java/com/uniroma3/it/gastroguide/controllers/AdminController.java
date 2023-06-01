package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.repositories.*;
import com.uniroma3.it.gastroguide.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    RecipeService recipeService;
     StepService stepService;
     TagService tagService;
     ReviewService reviewService;
     UserService userService;
     IngredientService ingredientService;

     TagRepository tagRepository;
     StepRepository stepRepository;
     RecipeRepository recipeRepository;
     ReviewRepository reviewRepository;
     IngredientRepository ingredientRepository;
     UserRepository userRepository;
    @Autowired
    public AdminController(
            RecipeService recipeService,
            StepService stepService,
            TagService tagService,
            ReviewService reviewService,
            UserService userService,
            IngredientService ingredientService,
            TagRepository tagRepository,
            StepRepository stepRepository,
            RecipeRepository recipeRepository,
            ReviewRepository reviewRepository,
            IngredientRepository ingredientRepository,
            UserRepository userRepository)
    {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.ingredientRepository=ingredientRepository;
        this.ingredientService=ingredientService;
        this.recipeRepository=recipeRepository;
        this.recipeService=recipeService;
        this.stepRepository=stepRepository;
        this.stepService=stepService;
        this.tagRepository=tagRepository;
        this.tagService=tagService;
        this.userRepository=userRepository;
        this.userService=userService;
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/panel")
    public String adminPanel(){
        return "admin_panel";
    }


}
