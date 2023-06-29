package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.dtos.TagDto;
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
        return tagRepository.findByRecipeId(recipeId);
    }

    @Override
    public Optional<Tag> findByTitleContainingIgnoreCase(String title) {
        return tagRepository.findAllByTitleContainingIgnoreCase(title);
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public List<Tag> findAllByUserIsNotNull() {
        return tagRepository.findAllByUserIsNotNull();
    }

    @Override
    public Optional<Tag> findByTitle(String name) {
        return tagRepository.findByTitle(name);
    }

    @Override
    public void saveOrUpdate(Tag tag) {
        this.tagRepository.save(tag);
    }

    @Override
    public void bulkCreate(List<TagDto> tagDtos) {
        for (TagDto td: tagDtos) {
            Tag t=new Tag(td.getName(),td.getUserId());
            tagRepository.save(t);
        }
    }

    @Override
    public void deleteByName(String name) {
        Tag tag=tagRepository.findByTitle(name).get();
        tag.removeTagFromRecipes();
        tagRepository.delete(tag);
    }
}
