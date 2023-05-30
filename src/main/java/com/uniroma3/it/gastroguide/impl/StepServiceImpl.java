package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.models.Step;
import com.uniroma3.it.gastroguide.repositories.StepRepository;
import com.uniroma3.it.gastroguide.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
