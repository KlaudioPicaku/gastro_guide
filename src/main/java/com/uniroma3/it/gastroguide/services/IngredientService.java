package com.uniroma3.it.gastroguide.services;

import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.RecipeImage;

import java.util.List;

public interface IngredientService {
    List<Recipe> findAllRecipesContainingIngredient(Ingredient ingredient);

    List<Ingredient> findAllIngredientsByRecipe(Recipe recipe);


}
