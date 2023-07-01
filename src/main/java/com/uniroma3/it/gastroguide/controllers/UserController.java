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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
                             @RequestParam(name = "order", defaultValue = "asc") String order,
                             HttpServletRequest request,
                             Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user=userService.getUserByUsername(auth.getName());

        model.addAttribute("request",request);
        if(!user.isPresent()){
            return "error/404";
        }
        User userLocal=user.get();
        UserProfileDto userPublic = new UserProfileDto(userLocal.getUsername(),userLocal.getImage(),userLocal.getFirstName(), userLocal.getLastName(),userLocal.isVerified());
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
        model.addAttribute("isOwnerOrAdmin",true);
        return "user_profile";
    }
    @GetMapping("/chef/detail")
    public String getProfile(@RequestParam(name = "name") String name,
                             HttpServletRequest request,
                             Model model) {

        Optional<User> user=userService.getUserByFullName(name);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> userLogged=userService.getUserByUsername(auth.getName());
        boolean isOwnerOrAdmin=userLogged.isPresent() && userLogged.get().getUsername().equals(user.get().getUsername()) ||
                auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        boolean isOwner = userLogged.isPresent() && userLogged.get().getUsername().equals(user.get().getUsername());

        model.addAttribute("request",request);
        if(!user.isPresent()){
            return "error/404";
        }
        User userLocal=user.get();
        UserProfileDto userPublic = new UserProfileDto(userLocal.getUsername(),userLocal.getImage(),userLocal.getFirstName(), userLocal.getLastName(),userLocal.isVerified());

        List<Review> reviews= new ArrayList<>();

        model.addAttribute("ownRecipes",recipeService.findByUser(user.get()));
        model.addAttribute("isOwner",isOwner);
        model.addAttribute("user",userPublic);
        model.addAttribute("isOwnerOrAdmin",isOwnerOrAdmin);
        return "user_public_profile";
    }
    @GetMapping("/reviews/edit")
    public String editReview(@RequestParam("id") Long id,Model model, HttpServletRequest request){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<Review> review = reviewService.findById(id);
        Optional<User> user=userService.getUserByUsername(auth.getName());
        if(user.isPresent() && review.isPresent() && !(user.get().getUsername().equals(review.get().getuser().getUsername()))){
            return "error/403";
        }
        Review reviewLocal=review.get();
        ReviewPublic reviewPublic=new ReviewPublic(reviewLocal.getTitle(),reviewLocal.getBody(),
                reviewLocal.getuser().getUsername(),reviewLocal.getRating(),reviewLocal.getuser().getImage()
                ,reviewLocal.getId(),reviewLocal.getRecipe());
        if(user.isPresent()) {
            User userLocal = user.get();
            UserProfileDto userPublic = new UserProfileDto(userLocal.getUsername(),userLocal.getImage(),userLocal.getFirstName(), userLocal.getLastName(),userLocal.isVerified());
            model.addAttribute("user", userPublic);
            model.addAttribute("review", reviewPublic);
            model.addAttribute("request",request);
            return "edit_review";
        }
        else{
            return "error/404";
        }
    }
    @PostMapping("/edit/review")
    public String editReview(@ModelAttribute("review") ReviewPublic review, BindingResult results){
        System.out.println(review.toString());
        if(results.hasErrors()){
            System.out.println(results.getAllErrors());
            return "redirect:/reviews/edit?id="+review.getReviewId();
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long reviewID=review.getReviewId();
        Optional<Review> reviewLocal = reviewService.findById(reviewID);
        Optional<User> user=userService.getUserByUsername(auth.getName());
        System.out.println("-------------------");
        System.out.println(reviewLocal.isPresent());
        System.out.println(reviewLocal.get().getuser().getUsername());
        System.out.println(user.get().getUsername());
        System.out.println("------------------");
        if(reviewLocal.isPresent() && !reviewLocal.get().getuser().getUsername().equals(user.get().getUsername())){
            return "error/403";
        }
        reviewLocal.get().setRating(review.getReviewRating());
        reviewLocal.get().setBody(review.getReviewBody());
        reviewLocal.get().setTitle(review.getReviewTitle());
        reviewService.save(reviewLocal.get());
        return "redirect:/profile?page=1&recipe=&rating=&order=";
    }

}
