package com.uniroma3.it.gastroguide.services.tokens;

import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.PasswordResetToken;


public interface PasswordResetTokenService {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
    void saveOrUpdate(PasswordResetToken passwordResetToken);
    void delete(Long id);

    PasswordResetToken generateResetToken(User user);

    void burnByUser(User user);
}
