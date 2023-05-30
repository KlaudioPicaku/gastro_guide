package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.RecipeRating;
import com.uniroma3.it.gastroguide.dtos.ReviewDto;
import com.uniroma3.it.gastroguide.dtos.ReviewPublicRestDto;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Review;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.ReviewService;
import com.uniroma3.it.gastroguide.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ReviewController {
    UserService userService;

    ReviewService reviewService;
    RecipeService recipeService;


    @Autowired
    public ReviewController(UserService userService, ReviewService reviewService, RecipeService recipeService){
        this.userService=userService;
        this.reviewService=reviewService;
        this.recipeService=recipeService;

    }


    //    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping(path = "/api/reviews/create")
    public ResponseEntity<String> createReview(
            @Valid @ModelAttribute("review") ReviewDto reviewDto,
            BindingResult bindingResult) {

        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Validation errors occurred");
        }
        System.out.println(reviewDto.toString());
        System.out.println(bindingResult.hasErrors());


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        boolean isAdminOrUser = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") || role.getAuthority().equals("ROLE_USER"));

        if(!isAdminOrUser){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<User> user=  userService.getUserByUsername(authentication.getName());
        Optional<Recipe> recipe= recipeService.findById(reviewDto.getRecipeId());
        if (recipe != null && user.isPresent()) {

            Review review = new Review(reviewDto.getTitle(), reviewDto.getRating(), reviewDto.getBody(), user.get(), recipe.get());
            System.out.println(review.toString());
            reviewService.save(review);
            System.out.println(review.toString());

        }else{
            return ResponseEntity.badRequest().body("There was a problem fetching the author or film entity ");
        }
        return ResponseEntity.ok("Review saved successfully");
    }
    @GetMapping("/review-count")
    public RecipeRating getReviewCount(@RequestParam(name = "recipe") int recipeId){
        double average = reviewService.findAllByRecipeId(recipeService.findById((long) recipeId).get()).stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);
        if (average>0) {
            return new RecipeRating(String.valueOf(Math.round(average*100.0)/100.0),reviewService.findAllByRecipe(recipeService.findById((long)recipeId).get()).size());
        }

        return new RecipeRating("No ratings",0);
    }

    @GetMapping("/api/reviews/load")
    public List<ReviewPublicRestDto> reviewDisplay(@RequestParam(name = "page") int page,
                                                   @RequestParam(name = "recipe") int recipeId,
                                                   @RequestParam(name = "rating", required = false) Integer rating) {
        Recipe recipe = recipeService.findById((long) recipeId).get();
        List<Review> reviews;

        if (rating == null) {
            reviews = reviewService.findAllByRecipe(recipe);
        } else {
            reviews = reviewService.findAllByRatingForRecipe(recipe, rating);
        }

        List<ReviewPublicRestDto> reviewsPublic = reviews.stream()
                .map(r -> new ReviewPublicRestDto(r.getTitle(), r.getBody(), r.getuser().getUsername(), r.getRating(), r.getuser().getImage(),r.getId()))
                .collect(Collectors.toList());

        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, reviewsPublic.size());
        if(startIndex<=endIndex) {
            return reviewsPublic.subList(startIndex, endIndex);
        }
        else{
            return null;
        }
    }


}

