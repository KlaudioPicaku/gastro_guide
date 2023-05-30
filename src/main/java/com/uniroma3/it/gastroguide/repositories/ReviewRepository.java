package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Review;
import com.uniroma3.it.gastroguide.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByRatingAndRecipe(Integer rating, Recipe recipe);

    List<Review> findAllByUser(User user);

    List<Review> findAllByRecipe(Recipe recipe);

    List<Review> findAllByUserAndRecipe(User user, Optional<Recipe> recipe);

    List<Review> findAllByRating(Integer rating);

    List<Review> findAllByUserAndRating(User user, Integer rating);


    List<Review> findAllByUserAndRatingAndRecipe(User user, Integer rating, Recipe recipe);

    List<Review> findAllByRecipeId(Long recipeId);

    Review save(Review review);

    void deleteById(Long id);

    Optional<Review> findById(Long id);
}
