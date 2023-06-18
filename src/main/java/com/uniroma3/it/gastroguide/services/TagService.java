package com.uniroma3.it.gastroguide.services;

import com.uniroma3.it.gastroguide.dtos.TagDto;
import com.uniroma3.it.gastroguide.models.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    List<Tag> findAllByRecipeId(Long recipeId);

    Optional<Tag> findByTitleContainingIgnoreCase(String title);

    List<Tag> findAll();

    List<Tag> findAllByUserIsNotNull();

    Optional<Tag> findByTitle(String name);

    void saveOrUpdate(Tag tag);

    void bulkCreate(List<TagDto> tagDtos);

}
