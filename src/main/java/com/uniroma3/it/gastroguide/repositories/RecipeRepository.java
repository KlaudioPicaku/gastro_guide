package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Ingredient;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Optional<Recipe> findById(Long id);
    List<Recipe> findAllByUser(User user);

    List<Recipe> findAllByTagsContainingIgnoreCase(String tag);


    List<Recipe> findAllByIngredientsAndNameContaining(Ingredient ingredient,String name);


//    List<Recipe> findByNameContainingIgnoreCase(String title);

    List<Recipe> findAllByIngredientsContaining(Ingredient ingredient);


    List<Recipe> findAllByNameContainingIgnoreCase(String term);
}
