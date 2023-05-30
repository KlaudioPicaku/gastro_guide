package com.uniroma3.it.gastroguide.impl.token;


import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.PasswordResetToken;
import com.uniroma3.it.gastroguide.repositories.tokens.PasswordResetTokenRepository;
import com.uniroma3.it.gastroguide.services.tokens.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PasswordResetTokenImpl implements PasswordResetTokenService {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;


    @Override
    public PasswordResetToken findByToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken.isValid()){
            return passwordResetToken;
        }
        return  null;
    }

    @Override
    public PasswordResetToken findByUser(User user) {
        List<PasswordResetToken> tokens=passwordResetTokenRepository.getByUser(user);
        for(PasswordResetToken t:tokens){
            if(t.isValid()){
                return  t;
            }
        }

        PasswordResetToken token=new PasswordResetToken();
        token.burn();
        return  token;
    }

    @Override
    public void saveOrUpdate(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public void delete(Long id) {
        passwordResetTokenRepository.deleteById(id);
    }

    @Override
    public PasswordResetToken generateResetToken(User user) {
        String token =  UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        token=token.toUpperCase();
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
        return passwordResetToken;
    }

    @Override
    public void burnByUser(User user) {
        List<PasswordResetToken> passwordResetTokens = passwordResetTokenRepository.findByUser(user);

        for(PasswordResetToken p: passwordResetTokens){
            p.burn();
        }
        passwordResetTokenRepository.saveAll(passwordResetTokens);

    }
}
