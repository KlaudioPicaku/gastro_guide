package com.uniroma3.it.gastroguide.repositories.tokens;

import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByToken(String token);

    public List<PasswordResetToken> getByUser(User user);

    PasswordResetToken save(PasswordResetToken passwordResetToken);

    void deleteById(Long id);

    List<PasswordResetToken> findByUser(User user);
}
