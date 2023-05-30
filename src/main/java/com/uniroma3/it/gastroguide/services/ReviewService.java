package com.uniroma3.it.gastroguide.services;

import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Review;
import com.uniroma3.it.gastroguide.models.User;

import java.util.List;
import java.util.Optional;

public interface ReviewService {


    List<Review> findAllByRatingForRecipe(Recipe recipe, Integer Rating);

    List<Review> findAllByUser(User user);

    List<Review> findAllByRecipe(Recipe recipe);

    Review save(Review review);

    void saveOrUpdate(Review review);

    void delete(Long id);

    List<Review> findAll();

    List<Review> findAllByRating(Integer rating);

    Optional<Review> findById(Long id);

    List<Review> findAllByRecipeAndUser(Recipe recipe, Optional<User> user);

    List<Review> findAllByRatingAndUser(Integer rating, Optional<User> user);

    List<Review> findAllByUserAndRatingForRecipe(Recipe recipe, Integer rating, Optional<User> user);

    List<Review> findAllByRecipeId(Recipe recipe);
}
