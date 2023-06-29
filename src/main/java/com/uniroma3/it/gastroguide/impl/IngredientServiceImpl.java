package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.repositories.IngredientRepository;
import com.uniroma3.it.gastroguide.repositories.RecipeRepository;
import com.uniroma3.it.gastroguide.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("ingredientServiceImpl")
public class IngredientServiceImpl implements IngredientService {
    RecipeRepository recipeRepository;
    IngredientRepository ingredientRepository;


    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository,IngredientRepository ingredientRepository){
        this.ingredientRepository=ingredientRepository;
        this.recipeRepository=recipeRepository;
    }
    @Override
    public List<Recipe> findAllRecipesContainingIngredient(Ingredient ingredient) {
        return recipeRepository.findAllByIngredientsContaining(ingredient);
    }

    @Override
    public List<Ingredient> findAllIngredientsByRecipe(Recipe recipe) {
        return ingredientRepository.findAllByRecipesContaining(recipe);
    }

    @Override
    public List<Ingredient> findAllByNameContaining(String name){
        return ingredientRepository.findAllByNameContaining(name);
    }

    @Override
    public Optional<Ingredient> findByName(String name) {
        return  ingredientRepository.findByName(name);
    }

    @Override
    public void saveOrUpdate(Ingredient ingredient) {
        this.ingredientRepository.save(ingredient);
    }

    @Override
    public List<Ingredient> findByRecipe(Recipe recipe) {
        return ingredientRepository.findAllByRecipesContaining(recipe);
    }

    @Override
    public List<Ingredient> findAll() {
        return this.ingredientRepository.findAll();
    }
}
