package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;
import com.uniroma3.it.gastroguide.services.tokens.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class ActivationController {
    @Autowired
    VerificationTokenService verificationTokenService;
    @GetMapping("/activation/success")
    public String showSuccessView(@RequestParam("token") String token,HttpServletRequest request,Model model) {
        model.addAttribute("request",request);
            return "activation_success";
    }

    @GetMapping("/activation/failed")
    public String showActivationFailed(@RequestParam("token") String token,HttpServletRequest request,Model model) {
        model.addAttribute("request",request);
        return "activation_failed";
    }

}
