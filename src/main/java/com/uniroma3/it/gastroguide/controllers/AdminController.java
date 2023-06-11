package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.TagDto;
import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.repositories.*;
import com.uniroma3.it.gastroguide.services.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

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
    public String adminPanel(HttpServletRequest request, Model model)
    {
        model.addAttribute("request",request);
        return "admin_panel";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/tag/create")
    public String adminTagCreate(HttpServletRequest request, Model model){
        model.addAttribute("request",request);
        List<TagDto> tagDtos=new ArrayList<>();
        tagDtos.add(new TagDto());
        model.addAttribute("tagDtos",tagDtos);
        return "admin_tag_create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/tag/create")
    public String adminTagPersist(@RequestParam("tagDtos") List<TagDto> tagDtos, HttpServletRequest request, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user= userService.getUserByUsername(auth.getName()).get();
        for(TagDto t: tagDtos){
            t.setUserId(user);
        }
        tagService.bulkCreate(tagDtos);
        return "redirect:/admin/panel";
    }


}
