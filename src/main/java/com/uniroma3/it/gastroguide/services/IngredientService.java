package com.uniroma3.it.gastroguide.services;

import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.RecipeImage;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    List<Recipe> findAllRecipesContainingIngredient(Ingredient ingredient);

    List<Ingredient> findAllIngredientsByRecipe(Recipe recipe);

    List<Ingredient> findAllByNameContaining(String name);


    Optional<Ingredient> findByName(String name);

    void saveOrUpdate(Ingredient ingredient);
}
