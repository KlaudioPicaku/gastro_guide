package com.uniroma3.it.gastroguide.models.tokens;



import com.uniroma3.it.gastroguide.models.User;
import jakarta.persistence.*;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Entity
public class VerificationToken {
    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    @Column(nullable = false)
    private int burned;

    private Date expiryDate;
    public VerificationToken(){

    }
    public VerificationToken(String token, User user){
        super();
        this.token=token;
        this.user=user;
        this.expiryDate=calculateExpiryDate(1440);
        this.burned=0;
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationToken)) return false;
        VerificationToken that = (VerificationToken) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(user, that.user);
    }
    // standard constructors, getters and setters
    @Override
    public int hashCode() {
        return Objects.hash(id, token, user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public String getToken(){return this.token;}

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public boolean isValid() {
        Date currentDate = new Date();
        return currentDate.before(expiryDate) && this.burned !=1 ;
    }
    public void burnToken(){
        this.burned=1;
    }
}
