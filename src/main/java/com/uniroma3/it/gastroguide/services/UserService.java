package com.uniroma3.it.gastroguide.services;

import com.uniroma3.it.gastroguide.dtos.UserDto;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    VerificationToken generateVerificationToken(User user);
    Optional<User> getUserByUsername(String username);
    void saveUser(UserDto user);

    void save(User user);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long id);

    void updateUser(User user, String password);

    boolean deletePreviousImage(User user);

    boolean passwordMatch(User user, String password);

    Optional<User> loadByEmail(String email);

    Optional<User> findByUsername(String name);

    List<User> findByFirstNameContainingIgnoreCaseLastNameContainingIgnoreCase(String term);

//    void processOAuth2User(OAuth2AuthenticationToken authenticationToken);
}
