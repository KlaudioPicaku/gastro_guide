package com.uniroma3.it.gastroguide.dtos;

import com.uniroma3.it.gastroguide.annotations.ValidPassword;

public class PasswordResetConfirm {
    private Long userId;
    @ValidPassword
    private  String password;

    @ValidPassword
    private String confirmPassword;

    public PasswordResetConfirm(){

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public PasswordResetConfirm(Long userId,String password, String confirmPassword) {
        this.userId=userId;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}

