package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.ReviewDto;
import com.uniroma3.it.gastroguide.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewFormController {

    UserService userService;
    @Autowired
    public ReviewFormController(UserService userService){
        this.userService=userService;

    }

    @GetMapping("/load-review-form")
    public String loadReviewForm(Model model){
        model.addAttribute("review",new ReviewDto());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "error/403";
        }
//        model.addAttribute("loggedInUserId",userService.getUserByUsername(authentication.getName()).getId());
        return "fragments/review_form_fragment";
    }
    @GetMapping("/load-reviews")
    public String loadReviewsModal(){
        return "fragments/modals/reviews_modal";
    }
}
