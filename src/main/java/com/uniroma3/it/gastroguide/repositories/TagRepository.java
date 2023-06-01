package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


    List<Tag> findAllByRecipeId(Long recipeId);

    Optional<Tag> findAllByTitleContainingIgnoreCase(String title);
}
