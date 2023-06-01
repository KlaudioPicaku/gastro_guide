package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.models.Tag;
import com.uniroma3.it.gastroguide.repositories.RecipeRepository;
import com.uniroma3.it.gastroguide.repositories.TagRepository;
import com.uniroma3.it.gastroguide.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {


    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository repository){
        this.tagRepository=repository;
    }
    @Override
    public List<Tag> findAllByRecipeId(Long recipeId) {
        return tagRepository.findAllByRecipeId(recipeId);
    }

    @Override
    public Optional<Tag> findByTitleContainingIgnoreCase(String title) {
        return tagRepository.findAllByTitleContainingIgnoreCase(title);
    }
}
