package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.constants.MailSubjects;
import com.uniroma3.it.gastroguide.dtos.PasswordResetConfirm;
import com.uniroma3.it.gastroguide.dtos.PasswordResetDto;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.PasswordResetToken;
import com.uniroma3.it.gastroguide.services.EmailService;
import com.uniroma3.it.gastroguide.services.UserService;
import com.uniroma3.it.gastroguide.services.tokens.PasswordResetTokenService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class ResetPasswordController {
    @Autowired
    private UserService userService;


    @Autowired
    private PasswordResetTokenService passwordResetTokenService;


    @Autowired
    private EmailService emailService;

    @GetMapping("/reset-password")
    public String getResetPasswordForm(HttpServletRequest request, Model model){
        model.addAttribute("request",request);
        model.addAttribute("passModel",new PasswordResetDto());
        return "reset_password";
    }

    @PostMapping("/reset/password/insert-code")
    public String getInsertCodeForm(@ModelAttribute("passModel") PasswordResetDto passwordResetDto, BindingResult result, Model modelreturn,HttpServletRequest request){
        if (result.hasErrors()){
            result.rejectValue("userEmail","The email you inserted wasn't valid. Please try again !");
            return "reset_password";
        }
        String email= passwordResetDto.getUserEmail();
        Optional<User> user=userService.getUserByEmail(email);
        if(!user.isPresent() || !user.get().isEnabled()){
            result.rejectValue("userEmail", "user.error","This account doesn't exist or it's not activated.");
            return "reset_pasword";
        }
        if(passwordResetTokenService.findByUser(user.get())!=null &&
                passwordResetTokenService.findByUser(user.get()).isValid()){
            result.rejectValue("userEmail", "mail.sent"," An email was already sent to this account and the code is still valid, check your inbox and/or spam or try again after 14 minutes");
            return "reset_password";
        }
        PasswordResetToken passwordResetToken=passwordResetTokenService.generateResetToken(user.get());
        try {
            emailService.sendPasswordResetEmail(email, MailSubjects.STANDARD_RESET_PASSWORD_SUBJECT,passwordResetToken.getToken());
        } catch (MessagingException e) {
            System.out.println("There was a problem sending the reset email! S K I  P Z !");
        }
        modelreturn.addAttribute("userId",user.get().getId());
        modelreturn.addAttribute("resetModel",new PasswordResetDto());
        modelreturn.addAttribute("request",request);
        return "insert-password-reset-code";
    }

    @PostMapping("/reset/password/")
    public String resetConfirm(@ModelAttribute ("resetModel") PasswordResetDto resetModel,BindingResult result,Model model,HttpServletRequest request){

        String code= resetModel.getCode();
        System.out.println("................");
        System.out.println(code);
        System.out.println("..........");
        if(code.isEmpty() || !passwordResetTokenService.findByToken(code).isValid()){
            result.rejectValue("code","invalid.code","The provide code was invalid or expired");
            model.addAttribute("request",request);
            return "insert-password-reset-code";
        }
        PasswordResetConfirm passwordResetConfirm= new PasswordResetConfirm();
        passwordResetConfirm.setUserId(passwordResetTokenService.findByToken(code).getUser().getId());
        model.addAttribute("passwordResetConfirm",passwordResetConfirm);
        model.addAttribute("request",request);
        return "insert-new-password-form";
    }
    @PostMapping("/update/password/")
    public String resetPassword(@ModelAttribute("passwordResetConfirm") PasswordResetConfirm passwordResetConfirm,BindingResult result,Model model,HttpServletRequest request){
        if(result.hasErrors() || !passwordResetConfirm.getPassword().equals(passwordResetConfirm.getConfirmPassword())){
            result.rejectValue("password", "invalid.password","The password you inserted didn't match or wasn't valid, try again after 14 minutes");
            model.addAttribute("request",request);
            return "insert-new-password-form";
        }
        Optional<User> user = userService.getUserById(passwordResetConfirm.getUserId());
        System.out.println(passwordResetConfirm.getUserId() );
        System.out.println((user!= null && !user.isPresent()) );
        System.out.println( !user.get().isEnabled());
        if((user!= null && !user.isPresent()) || !user.get().isEnabled()){
            result.rejectValue("password", "disabled.account"," Account non existent or disabled. Check the email address you inserted is correct and try again after 14 minutes");
            model.addAttribute("request",request);
            return "insert-new-password-form";
        }
        userService.updateUser(user.get(),passwordResetConfirm.getPassword());
        return "redirect:/login/";
    }

}
