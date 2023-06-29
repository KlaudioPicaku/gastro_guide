package com.uniroma3.it.gastroguide.repositories;

import com.uniroma3.it.gastroguide.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> getUserById(Long id);

    List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String term, String term1);

    Optional<User> findByFirstNameContainingAndLastNameContaining(String firstName, String lastName);
}