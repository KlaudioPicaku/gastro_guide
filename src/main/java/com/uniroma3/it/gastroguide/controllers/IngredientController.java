package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.services.IngredientService;
import com.uniroma3.it.gastroguide.services.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class IngredientController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientService ingredientService;

    @GetMapping("/ingredients/recipe")
    public String getFilmCast(@RequestParam("id") Long recipeId,
                              @RequestParam("page") Integer page,
                              HttpServletRequest request,
                              Model model){
        Optional<Recipe> recipe= recipeService.findById(recipeId);

        if(!recipe.isPresent()){
            return  "error/404";
        }
        List<Ingredient> ingredients= ingredientService.findByRecipe(recipe.get());
        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, ingredients.size());
        List<Ingredient> ingredientSublist;
        if (startIndex <= endIndex) {
            ingredientSublist=ingredients.subList(startIndex, endIndex);
        } else {
            ingredientSublist= Collections.emptyList();
        }
        int maxNumberOfPages=0;
        if(ingredientSublist.size()>0){
            maxNumberOfPages=(int) Math.ceil((double) ingredients.size() / pageSize);;
        }
        model.addAttribute("request",request);
        model.addAttribute("page",page);
        model.addAttribute("recipe",recipe.get());
        model.addAttribute("maxNumberOfPages",maxNumberOfPages);
        model.addAttribute("ingredients",ingredientSublist);
        return "recipe_ingredients";
    }
    @GetMapping("/ingredients/list_view")
    public String getFilmCast(@RequestParam("page") Integer page,
                              HttpServletRequest request,
                              Model model){


        List<Ingredient> ingredients= ingredientService.findAll();
        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, ingredients.size());
        List<Ingredient> ingredientSublist;
        if (startIndex <= endIndex) {
            ingredientSublist=ingredients.subList(startIndex, endIndex);
        } else {
            ingredientSublist= Collections.emptyList();
        }
        int maxNumberOfPages=0;
        if(ingredientSublist.size()>0){
            maxNumberOfPages=(int) Math.ceil((double) ingredients.size() / pageSize);;
        }
        model.addAttribute("request",request);
        model.addAttribute("page",page);
        model.addAttribute("maxNumberOfPages",maxNumberOfPages);
        model.addAttribute("ingredients",ingredientSublist);
        return "recipe_ingredients";
    }

}
