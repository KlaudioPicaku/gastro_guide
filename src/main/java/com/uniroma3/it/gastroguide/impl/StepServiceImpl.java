package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Step;
import com.uniroma3.it.gastroguide.repositories.StepRepository;
import com.uniroma3.it.gastroguide.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("stepServiceImpl")
public class StepServiceImpl implements StepService {

    StepRepository stepRepository;

    @Autowired
    public StepServiceImpl(StepRepository stepRepository){
        this.stepRepository=stepRepository;
    }
    @Override
    public List<Step> findAllByRecipeId(Long recipeId) {
        return stepRepository.findAllByRecipeId(recipeId);
    }

    @Override
    public double getEstimatedDuration(Recipe r) {
        double totalTime=findAllByRecipeId(r.getId()).stream().mapToDouble(Step::getEstimatedDuration).sum();
        return totalTime;
    }

    @Override
    public void saveOrUpdate(Step step) {
        this.stepRepository.save(step);
    }

    @Override
    public List<Step> findAll() {
        return stepRepository.findAll();
    }

    @Override
    public Optional<Step> findAllByRecipeAndTitle(Recipe recipe, String title) {
        return stepRepository.findByRecipeAndTitle(recipe,title);
    }
}
