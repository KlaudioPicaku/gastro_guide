package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameContaining(String ingredientName);

    List<Ingredient> findAllByRecipesContaining(Recipe recipe);

    Optional<Ingredient> findByName(String name);

    Ingredient save(Ingredient ingredient);

}
