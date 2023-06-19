package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {


    List<Step> findAllByRecipeId(Long recipeId);

    Optional<Step> findByRecipeAndTitle(Recipe recipe, String title);
}
