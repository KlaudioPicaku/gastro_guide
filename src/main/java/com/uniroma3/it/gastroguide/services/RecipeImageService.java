package com.uniroma3.it.gastroguide.services;

import com.uniroma3.it.gastroguide.models.RecipeImage;

import java.util.List;
import java.util.Optional;

public interface RecipeImageService {
    List<RecipeImage> findAll();

    void saveOrUpdate(RecipeImage recipeImage);

    void delete(RecipeImage recipeImage);

    Optional<RecipeImage> findById(Long Id);

    List<RecipeImage> findByRecipeId(Long recipeId);
}
