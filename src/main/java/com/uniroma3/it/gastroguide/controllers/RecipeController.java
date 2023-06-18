package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.IngredientDto;
import com.uniroma3.it.gastroguide.dtos.RecipeDto;
import com.uniroma3.it.gastroguide.dtos.StepDto;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.ReviewService;
import com.uniroma3.it.gastroguide.services.TagService;
import com.uniroma3.it.gastroguide.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.extras.springsecurity6.auth.Authorization;

import java.util.Optional;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @Autowired
    TagService tagService;

    @Autowired
    ReviewService reviewService;


    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/recipe/create")
    public String getRecipeForm(HttpServletRequest request, Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth);
        model.addAttribute("request",request);
        model.addAttribute("recipe",new RecipeDto());
        model.addAttribute("tags",tagService.findAll());
        return "recipe_create_form";
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PostMapping("/recipe/create")
    public String createRecipe(@ModelAttribute("recipe") RecipeDto recipe, HttpServletRequest request, BindingResult result,Model model){
        if (result.hasErrors()){
            model.addAttribute("request",request);
            return "recipe_create_form";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user= userService.getUserByUsername(auth.getName()).get();
        recipe.setUserId(user.getId());
        System.out.println(recipe.toString());
//        for (StepDto st: recipe.getSteps()){
//            System.out.println(st.toString());
//        }
        recipeService.createFromDto(recipe);

        return "redirect:/";
    }

    @GetMapping("/recipe/detail")
    public String filmDetail(@RequestParam("id") Long id,HttpServletRequest request, Model model){
        Optional<Recipe> recipe=recipeService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user= userService.findByUsername(authentication.getName());


        model.addAttribute("request",request);
        boolean reviewLeft= false;

        if(user.isPresent()){
            reviewLeft= reviewService.findAllByUserAndRecipe(user.get(),recipe).size() > 0 ;
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") );
        if(isAdmin) {
            model.addAttribute("reviewLeft", false);

        }else{
            model.addAttribute("reviewLeft", reviewLeft);
        }

        int number_of_reviews = reviewService.findAllByRecipe(recipe.get()).size();

        model.addAttribute("number_of_reviews",number_of_reviews);

        if (recipe !=null){
            model.addAttribute("recipe", recipe.get());

            return "recipe_detail";
        }
        return "error/404";
    }

}
