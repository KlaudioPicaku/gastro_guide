package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;
import com.uniroma3.it.gastroguide.services.tokens.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ActivationController {
    @Autowired
    VerificationTokenService verificationTokenService;
    @GetMapping("/activation/success")
    public String showSuccessView(@RequestParam("token") String token) {
            return "activation_success";
    }

    @GetMapping("/activation/failed")
    public String showActivationFailed(@RequestParam("token") String token){ return "activation_failed";}

}
