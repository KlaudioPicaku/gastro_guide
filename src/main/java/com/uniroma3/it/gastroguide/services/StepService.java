package com.uniroma3.it.gastroguide.services;

import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Step;

import java.util.List;
import java.util.Optional;

public interface StepService {
    List<Step> findAllByRecipeId(Long recipeId);

    double getEstimatedDuration(Recipe r);

    void saveOrUpdate(Step step);

    List<Step> findAll();

    Optional<Step> findAllByRecipeAndTitle(Recipe recipe, String title);
}
