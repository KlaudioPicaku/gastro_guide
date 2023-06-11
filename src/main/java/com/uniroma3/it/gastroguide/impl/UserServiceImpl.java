package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.constants.GUIconstants;
import com.uniroma3.it.gastroguide.constants.MailSubjects;
import com.uniroma3.it.gastroguide.dtos.UserDto;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.models.tokens.VerificationToken;
import com.uniroma3.it.gastroguide.repositories.UserRepository;
import com.uniroma3.it.gastroguide.repositories.tokens.VerificationTokenRepository;
import com.uniroma3.it.gastroguide.services.UserService;
import com.uniroma3.it.gastroguide.services.tokens.PasswordResetTokenService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Qualifier("userServiceImpl")
public class UserServiceImpl implements UserService,UserDetailsService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private VerificationTokenRepository verificationTokenRepository;

    private EmailServiceImpl emailService;
    private PasswordResetTokenService passwordResetTokenService;


    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           VerificationTokenRepository verificationTokenRepository,
                           EmailServiceImpl emailService, PasswordResetTokenService passwordResetTokenService) {
        super();
        this.passwordEncoder=bCryptPasswordEncoder;
        this.verificationTokenRepository=verificationTokenRepository;
        this.emailService=emailService;
        this.userRepository = userRepository;
        this.passwordResetTokenService=passwordResetTokenService;
    }

    @Override
    public VerificationToken generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Override
    public void saveUser(@NotNull UserDto user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        User newUser=new User(user.getUsername(),user.getEmail(),encodedPassword,user.getFirstName(),user.getLastName(), GUIconstants.DEFAULT_PROFILE_PICTURE);
        userRepository.save(newUser);
        VerificationToken verificationToken = this.generateVerificationToken(newUser);
        emailService.sendVerificationEmail(user.getEmail(), MailSubjects.STANDARD_ACTIVATE_ACCOUNT_SUBJECT,verificationToken.getToken());
    }
    @Override
    public void save(User user){
        userRepository.save(user);

    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void updateUser(User user, String password) {
        user.setPassword(password);
        userRepository.save(user);
        passwordResetTokenService.burnByUser(user);
    }

    @Override
    public boolean deletePreviousImage(User user) {
        boolean notDefaultPic= !(userRepository.findById(user.getId()).get().getImage().equals(GUIconstants.DEFAULT_PROFILE_PICTURE));

        if (notDefaultPic) {
            String absoluteImageUrl=user.getImage();
            String filePath="";
            if(absoluteImageUrl!=null || !absoluteImageUrl.isEmpty()){
                filePath=absoluteImageUrl;
            }
            if(!filePath.isEmpty()){
                String baseDirectory = System.getProperty("user.dir");
                Path path = Paths.get(baseDirectory,filePath);
//            System.out.println(path);

                try {
                    Files.delete(path);
                    System.out.println("File deleted successfully.");
                } catch (IOException e) {
                    System.out.println("Failed to delete the file: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        return false;
    }




    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean passwordMatch(User user, String inputPassword){
        if (user!=null){
            if(!inputPassword.isEmpty()){
                return passwordEncoder.matches(inputPassword,user.getPassword());
            }
        }
        return false;
    }
    @Override
    public Optional<User> loadByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException {
        Optional<User> user= userRepository.findByUsername(username);
        if (!user.isPresent()){
            throw new UsernameNotFoundException("Invalid username or password");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.isPresent() && user.get().getRole()){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        if (user.isPresent() && !user.get().isEnabled()) {
            System.out.println("USER IS NOT ENABLED ! S K I P Z !");
            throw new DisabledException("Account is not enabled");
        }


        return  new org.springframework.security.core.userdetails.User(user.get().getUsername(),user.get().getPassword(),authorities);
    }
}
