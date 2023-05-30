package com.uniroma3.it.gastroguide.impl.token;


import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;
import com.uniroma3.it.gastroguide.repositories.tokens.VerificationTokenRepository;
import com.uniroma3.it.gastroguide.services.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Override
    public VerificationToken findByToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken.isValid()){
            return verificationToken;
        }
        return  null;
    }

    @Override
    public VerificationToken findByUser(User user) {
        return verificationTokenRepository.getByUser(user);
    }

    @Override
    public void saveOrUpdate(VerificationToken verificationToken) {
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void delete(Long id) {
        verificationTokenRepository.deleteById(id);

    }

}
