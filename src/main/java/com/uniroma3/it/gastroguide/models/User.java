package com.uniroma3.it.gastroguide.models;


import com.uniroma3.it.gastroguide.constants.StaticURLs;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User implements UserDetails {
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    @Id
    private Long id;

    @Column( name = "username", nullable = false, length = 20)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(nullable = false, unique = true, length = 45)
    @NotBlank(message = "email is required")
    private String email;

    @Column(nullable = false, length = 64)
    @NotEmpty
    @Size(min=8)
    private String password;

    @Column(name = "first_name", nullable = false, length = 20)
    @NotBlank(message = "First Name is required")
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    @NotBlank(message = "Last Name is required")
    private String lastName;

    @Column(name="is_superuser")
    private boolean isSuperuser;

    @Column(name = "enabled")
    private boolean enabled;

    @Column (name="foto",nullable = true)
    private String image;

    @Column
    private boolean verifiedChef;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recipe> recipes;

    public User() {}

    public User(String username, String email, String password, String firstName, String lastName, String image) {
        super();
        System.out.println("sto nel creatore ");
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.enabled=false;
        this.isSuperuser=false;
        this.verifiedChef=false;
    }

    // getters and setters

    public boolean isVerifiedChef() {
        return verifiedChef;
    }

    public void setVerifiedChef(boolean verifiedChef) {
        this.verifiedChef = verifiedChef;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        this.password = hashedPassword;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public Boolean getRole(){ return isSuperuser; }

    public void setImage(String image) {
        this.image = image;
    }

    // equals and hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(image, user.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, firstName, lastName, image);
    }

    // toString

    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '\'' +
                '}';
    }

    public String getFullName() {
        return this.firstName+" "+this.lastName;
    }

    public String getAbsoluteUrl() {
        return StaticURLs.CHEF_DETAIL_URL+this.getFullName();
    }

    public boolean  isVerified(){
        return  this.verifiedChef;
    }
}
