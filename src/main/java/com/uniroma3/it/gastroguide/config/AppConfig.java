package com.uniroma3.it.gastroguide.config;

import com.uniroma3.it.gastroguide.impl.*;
import com.uniroma3.it.gastroguide.repositories.RecipeRepository;
import com.uniroma3.it.gastroguide.repositories.ReviewRepository;
import com.uniroma3.it.gastroguide.repositories.StepRepository;
import com.uniroma3.it.gastroguide.repositories.UserRepository;
import com.uniroma3.it.gastroguide.repositories.tokens.VerificationTokenRepository;
import com.uniroma3.it.gastroguide.services.*;
import com.uniroma3.it.gastroguide.services.tokens.PasswordResetTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public UserService userService(UserRepository userRepository, VerificationTokenRepository verificationTokenRepository, EmailServiceImpl emailService, PasswordResetTokenService passwordResetTokenService) {
        return new UserServiceImpl(userRepository, new BCryptPasswordEncoder(), verificationTokenRepository, emailService,passwordResetTokenService);
    }

    @Bean
    public ReviewService reviewService(ReviewRepository reviewRepository, RecipeRepository recipeRepository, UserRepository userRepository){
        return new ReviewServiceImpl(reviewRepository,recipeRepository,userRepository);
    }

    @Bean
    public StepService stepService(StepRepository stepRepository){
        return new StepServiceImpl(stepRepository);
    }
    @Bean
    public RecipeService recipeService(RecipeRepository recipeRepository, ReviewService reviewService, StepRepository stepRepository, StepService stepService, UserRepository userRepository, ReviewRepository reviewRepository){
        return  new RecipeServiceImpl(recipeRepository, reviewService,
                stepRepository, stepService,
                userRepository, reviewRepository);
    }



}