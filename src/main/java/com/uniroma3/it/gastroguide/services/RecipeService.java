package com.uniroma3.it.gastroguide.services;

import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.User;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    public List<Recipe> findTopThree();
    public List<Recipe> findByIngredient(Ingredient ingredient);

    List<Recipe> findAll();

    Optional<Recipe> findById(Long id);

    List<Recipe> findByNameContainingIgnoreCase(String Name);

    void saveOrUpdate(Recipe recipe);

    void delete(Long Id);

    List<Recipe> searchByTerm(String term);

    public List<Recipe> findByUser(User user);
    public String getAverageRating(Recipe recipe);
    public Double getAverageDoubleRating(Recipe recipe);


    List<Recipe> getLatest3();
}
