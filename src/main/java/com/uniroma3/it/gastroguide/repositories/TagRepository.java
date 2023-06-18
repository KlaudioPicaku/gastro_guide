package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


    @Query("SELECT t FROM Tag t JOIN t.recipes r WHERE r.id = :recipeId")
    List<Tag> findByRecipeId(@Param("recipeId") Long recipeId);

    Optional<Tag> findAllByTitleContainingIgnoreCase(String title);

    Optional<Tag> findByTitle(String name);

    List<Tag> findAllByUserIsNotNull();

}
