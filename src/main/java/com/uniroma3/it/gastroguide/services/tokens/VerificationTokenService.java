package com.uniroma3.it.gastroguide.services.tokens;

import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;

public interface VerificationTokenService {
    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);
    void saveOrUpdate(VerificationToken verificationToken);
    void delete(Long id);
}
