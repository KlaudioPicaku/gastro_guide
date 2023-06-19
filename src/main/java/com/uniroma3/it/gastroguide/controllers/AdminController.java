package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.TagDto;
import com.uniroma3.it.gastroguide.exposed.ReviewPublic;
import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.Review;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.repositories.*;
import com.uniroma3.it.gastroguide.services.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/reviews/delete_confirm")
    public String confirmDeleteReview(@RequestParam("id")Long id, Model model, RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request){
        Optional<Review> review= reviewService.findById(id);

        if(!review.isPresent()){
            return "error/404";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user=userService.getUserByUsername(auth.getName());

        if(!user.isPresent()){
            return "error/404";
        }

        boolean isAdmin=auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if(!(user.get().getUsername().equals(review.get().getuser().getUsername())) && !isAdmin){
            return "error/403";
        }
        Review reviewdto=review.get();
        ReviewPublic reviewPublic=new ReviewPublic(reviewdto.getTitle(),reviewdto.getBody(),
                reviewdto.getuser().getUsername(),reviewdto.getRating(),reviewdto.getuser().getImage()
                ,reviewdto.getId(),reviewdto.getRecipe());
        model.addAttribute("object", reviewPublic);

        String referer = request.getHeader("Referer");
        if (referer != null) {
            session.setAttribute("previousUrl", referer);
        }
        model.addAttribute("request",request);
        System.out.println(session.getAttribute("previousUrl").toString());
        return "review_delete_confirm";

    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PostMapping("/reviews/delete")
    public String deleteReview(@RequestParam("id")Long id, HttpSession session, HttpServletRequest request){
        Optional<Review> review= reviewService.findById(id);
        String previousUrl = (String) session.getAttribute("previousUrl");
        if(!review.isPresent()){
            return "error/404";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user=userService.getUserByUsername(auth.getName());

        boolean isAdmin=auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));

        if(user.isPresent() && !(user.get().getUsername().equals(review.get().getuser().getUsername())) && !isAdmin){
            return "error/403";
        }
        reviewService.delete(review.get().getId());

        if (previousUrl != null) {
            session.removeAttribute("previousUrl");
            return "redirect:" + previousUrl;
        }


        return "redirect:/review/list_view?page=1&film=&rating=&order=";
    }


}
