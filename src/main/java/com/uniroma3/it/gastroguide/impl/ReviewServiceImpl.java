package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Review;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.repositories.RecipeRepository;
import com.uniroma3.it.gastroguide.repositories.ReviewRepository;
import com.uniroma3.it.gastroguide.repositories.UserRepository;
import com.uniroma3.it.gastroguide.services.ReviewService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("reviewServiceImpl")
public class ReviewServiceImpl implements ReviewService {


    ReviewRepository reviewRepository;


    RecipeRepository recipeRepository;

    UserRepository userRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository, RecipeRepository recipeRepository, UserRepository userRepository) {
        super();
        this.reviewRepository = reviewRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository= userRepository;
    }

    @Override
    public List<Review> findAllByRatingForRecipe(Recipe recipe, Integer rating) {
        return reviewRepository.findByRatingAndRecipe(rating, recipe);
    }

    @Override
    public List<Review> findAllByUser(User user) {
        return reviewRepository.findAllByUser(user);
    }

    @Override
    public List<Review> findAllByRecipe(Recipe recipe){
        return reviewRepository.findAllByRecipe(recipe);
    }

    @Override
    public Review save(Review review) {
        return this.reviewRepository.save(review);
    }



    @Override
    public void saveOrUpdate(Review review) {
        reviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.deleteById(id);

    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> findAllByRating(Integer rating) {
        return reviewRepository.findAllByRating(rating);
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public List<Review> findAllByRecipeAndUser(Recipe recipe, Optional<User> user) {
        return reviewRepository.findAllByUserAndRecipe(user.get(), Optional.ofNullable(recipe));
    }

    @Override
    public List<Review> findAllByRatingAndUser(Integer rating, Optional<User> user) {
        return reviewRepository.findAllByUserAndRating(user.get(),rating);
    }

    @Override
    public List<Review> findAllByUserAndRatingForRecipe(Recipe recipe, Integer rating, Optional<User> user) {
        return reviewRepository.findAllByUserAndRatingAndRecipe(user.get(),rating,recipe);
    }

    @Override
    public List<Review> findAllByRecipeId(Recipe recipe) {
        return reviewRepository.findAllByRecipeId(recipe.getId());
    }

    @Override
    public Collection<Review> findAllByUserAndRecipe(User user, Optional<Recipe> recipe) {
        return reviewRepository.findAllByUserAndRecipe(user,recipe);
    }

}