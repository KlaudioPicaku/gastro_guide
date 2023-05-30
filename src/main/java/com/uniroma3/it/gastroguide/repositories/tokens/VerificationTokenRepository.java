package com.uniroma3.it.gastroguide.repositories.tokens;

import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    public VerificationToken getByUser(User user);

}
