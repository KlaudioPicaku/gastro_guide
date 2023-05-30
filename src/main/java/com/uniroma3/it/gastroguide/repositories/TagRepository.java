package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {


}
