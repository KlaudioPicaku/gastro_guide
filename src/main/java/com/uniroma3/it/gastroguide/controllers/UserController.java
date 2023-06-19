package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.UserProfileDto;
import com.uniroma3.it.gastroguide.exposed.ReviewPublic;
import com.uniroma3.it.gastroguide.impl.UserServiceImpl;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Review;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.ReviewService;
import com.uniroma3.it.gastroguide.services.tokens.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private VerificationTokenService verificationTokenService;

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private ReviewService reviewService;



    @GetMapping("/verify-token")
    public String verifyToken(@RequestParam("token") String token) {
        VerificationToken verificationTokenLocal = verificationTokenService.findByToken(token);
        if (verificationTokenLocal == null || !verificationTokenLocal.isValid()) {
            // Handle invalid token
            return "redirect:/activation/failed?token=" + token;
        } else {
            User user = verificationTokenLocal.getUser();
            user.setEnabled(true);
            userService.save(user);
            verificationTokenLocal.burnToken();
            verificationTokenService.saveOrUpdate(verificationTokenLocal);
            // Handle successful verification
            return "redirect:/activation/success?token=" + token;
        }
    }
    @GetMapping("/profile")
    public String getProfile(@RequestParam(name = "page") int page,
                             @RequestParam(name = "recipe", required = false) Long recipeId,
                             @RequestParam(name = "rating", required = false) Integer rating,
                             @RequestParam(name = "order", defaultValue = "asc") String order, HttpServletRequest request,
                             Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user=userService.getUserByUsername(auth.getName());

        model.addAttribute("request",request);
        if(!user.isPresent()){
            return "error/404";
        }
        User userLocal=user.get();
        UserProfileDto userPublic = new UserProfileDto(userLocal.getUsername(),userLocal.getImage(),userLocal.getFirstName(), userLocal.getLastName());
        Recipe recipe=null;
        if(recipeId!=null) {
            recipe = recipeService.findById(recipeId).orElse(null);
//            System.out.println(film.toString());
        }

        List<Review> reviews= new ArrayList<>();
        if (page<1){
            return  "error/422";
        }
        if (rating == null && recipe != null) {
            reviews = reviewService.findAllByRecipeAndUser(recipe,user);
            model.addAttribute("currentRecipe", recipe);
            model.addAttribute("currentRating", null);
        } else if (rating != null && recipe == null){
            reviews = reviewService.findAllByRatingAndUser(rating,user);
            model.addAttribute("currentRecipe", null);
        }else if(rating !=null && recipe !=null) {
            reviews=reviewService.findAllByUserAndRatingForRecipe(recipe,rating,user);
            model.addAttribute("currentRecipe", recipe);

        }else{
            reviews=reviewService.findAllByUser(user.get());
            model.addAttribute("currentRecipe", null);
        }

        // ordinamento per data di creazione
        if (order.equals("asc") && !reviews.isEmpty()) {
            reviews.sort(Comparator.comparing(Review::getCreatedOn));
        } else if (order.equals("desc") && !reviews.isEmpty()) {
            reviews.sort(Comparator.comparing(Review::getCreatedOn).reversed());

        }

        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, reviews.size());

        List<Review> reviewSublist;
        if (startIndex <= endIndex) {
            reviewSublist=reviews.subList(startIndex, endIndex);
        } else {
            reviewSublist= Collections.emptyList();
        }
        int maxNumberOfPages=0;
        if(reviewSublist.size()>0){
            maxNumberOfPages=(int) Math.ceil((double) reviews.size() / pageSize);;
        }

        List<ReviewPublic> reviewsPublic = reviewSublist.stream()
                .map(r -> new ReviewPublic(r.getTitle(), r.getBody(), r.getuser().getUsername(), r.getRating(), r.getuser().getImage(),r.getId(),r.getRecipe()))
                .collect(Collectors.toList());
        model.addAttribute("ownRecipes",recipeService.findByUser(user.get()));
        model.addAttribute("reviews",reviewsPublic);
        model.addAttribute("page",page);
        model.addAttribute("maxNumberOfPages",maxNumberOfPages);
        model.addAttribute("currentOrdering",order);
        model.addAttribute("currentRating", rating);
        List<Recipe> recipes=recipeService.findAll();
        model.addAttribute("recipes",recipes);
        model.addAttribute("user",userPublic);
        return "user_profile";
    }

}
