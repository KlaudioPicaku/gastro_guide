package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {


    List<Step> findAllByRecipeId(Long recipeId);
}
