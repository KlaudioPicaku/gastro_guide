package com.uniroma3.it.gastroguide.models.tokens;


import com.uniroma3.it.gastroguide.models.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "burned")
    private Integer burned;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public PasswordResetToken(){

    }
    public PasswordResetToken(String token, User user) {
        super();
        this.token = token;
        this.expirationDate = calculateExpiryDate(14);
        this.user = user;
        this.burned=0;
    }
    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
    }

    public Long getId(){
        return this.id;
    }
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }
    public boolean isValid() {
        if(this.expirationDate!=null && this.burned==0) {
            Date currentDate = new Date();
            Date expirationDateTime = Date.from(expirationDate.atZone(ZoneId.systemDefault()).toInstant());
            return currentDate.before(expirationDateTime) && this.burned != 1 ;
        }
        return  false;

    }
    public void setUser(User user) {
        this.user = user;
    }

    public void burn() {
        this.burned=1;
    }
}
