package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.exposed.RecipePublic;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Tag;
import com.uniroma3.it.gastroguide.repositories.UserRepository;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.ReviewService;
import com.uniroma3.it.gastroguide.services.StepService;
import com.uniroma3.it.gastroguide.services.TagService;
import jakarta.servlet.http.HttpServletRequest;
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
    private StepService stepService;

    private TagService tagService;

    @Autowired
    public HomeController(RecipeService recipeService, UserRepository userRepository,
                          ReviewService reviewService, StepService stepService, TagService tagService) {
        this.recipeService = recipeService;
        this.userRepository=userRepository;
        this.reviewService=reviewService;
        this.stepService=stepService;
        this.tagService=tagService;
    }

    @GetMapping(value = "/")
    public String home(HttpServletRequest request, Model model) {
        List<Recipe> recipes = recipeService.findAll();

        // Sort recipes by average rating (null values will be treated as the maximum value)
        Comparator<Recipe> ratingComparator = Comparator.comparingDouble(recipe -> {
            Double averageRating = recipeService.getAverageDoubleRating(recipe);
            return averageRating != null ? -averageRating : Double.MAX_VALUE;
        });
        recipes.sort(Comparator.nullsLast(ratingComparator));

        List<Recipe> top5recipes = recipes.stream().limit(5).collect(Collectors.toList());
        List<RecipePublic> publicRecipes = new LinkedList<>();
        createRecipePublic(top5recipes, publicRecipes);

        List<Recipe> latest3 = recipeService.getLatest3();
        List<RecipePublic> carouselRecipes = new ArrayList<>();
        createRecipePublic(latest3, carouselRecipes);
//        System.out.println(publicRecipes.size());
//        for(RecipePublic r : publicRecipes){
//            System.out.println(r.getCoverPath());
//        }
        model.addAttribute("recipes", publicRecipes);
        model.addAttribute("carouselRecipes", carouselRecipes);
        model.addAttribute("request", request);
        return "home";
    }


    private void createRecipePublic(List<Recipe> source, List<RecipePublic> destination) {
        for (Recipe r:source) {
            String ratingValue= recipeService.getAverageRating(r);
            String reviewCount=String.valueOf(reviewService.findAllByRecipe(r).stream().count());
            double estimatedDuration=stepService.getEstimatedDuration(r);
            List<String> tags = tagService.findAllByRecipeId(r.getId())
                    .stream()
                    .map(Tag::getTitle)
                    .map(tag -> tag.replaceAll("[\\[\\]\"]", "").trim())
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toList());
            destination.add(new RecipePublic(r.getId(),r.getCoverPath(),r.getName(),
                    r.getUserName(),r.getDescription(),ratingValue,reviewCount,
                    estimatedDuration,stepService.findAllByRecipeId(r.getId()).size(),tags,r.getUser().getImage(),r.getUser().getAbsoluteUrl(),r.getUser().isVerified()));
        }
    }
}
