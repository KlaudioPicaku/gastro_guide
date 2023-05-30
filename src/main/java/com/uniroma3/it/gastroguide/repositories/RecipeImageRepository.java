package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface RecipeImageRepository extends JpaRepository<RecipeImage,Long> {
    public Optional<RecipeImage> findById(Long Id);

    public RecipeImage findByFilePathContaining(String title);

    public List<RecipeImage> findAll();

    public List<RecipeImage> findByRecipeId(Long recipeId);

    RecipeImage findFirstByRecipeOrderByIdAsc(Recipe recipe);
}
