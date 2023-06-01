package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.impl.UserServiceImpl;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;
import com.uniroma3.it.gastroguide.services.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;


    @Autowired
    private VerificationTokenService verificationTokenService;



    @GetMapping("/verify-token")
    public String verifyToken(@RequestParam("token") String token) {
        VerificationToken verificationTokenLocal = verificationTokenService.findByToken(token);
        if (verificationTokenLocal == null || !verificationTokenLocal.isValid()) {
            // Handle invalid token
            return "redirect:/activation/failed?token=" + token;
        } else {
            User user = verificationTokenLocal.getUser();
            user.setEnabled(true);
            userService.save(user);
            verificationTokenLocal.burnToken();
            verificationTokenService.saveOrUpdate(verificationTokenLocal);
            // Handle successful verification
            return "redirect:/activation/success?token=" + token;
        }
    }
}
