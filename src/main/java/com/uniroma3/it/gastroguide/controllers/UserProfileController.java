package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.constants.DefaultSaveLocations;
import com.uniroma3.it.gastroguide.constants.GUIconstants;
import com.uniroma3.it.gastroguide.dtos.UserProfileDto;
import com.uniroma3.it.gastroguide.exposed.ReviewPublic;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.Review;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.ReviewService;
import com.uniroma3.it.gastroguide.services.UserService;
import com.uniroma3.it.gastroguide.utils.FileNameGenerator;
import com.uniroma3.it.gastroguide.utils.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class UserProfileController {
    @Autowired
    private UserService userService;



    @PostMapping("/update/profile")
    public ResponseEntity<Object> updateProfilePic(@RequestParam("file") MultipartFile file){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        boolean isAdminOrUser = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") || role.getAuthority().equals("ROLE_USER"));

        if(!isAdminOrUser){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user= userService.getUserByUsername(authentication.getName()).get();

        if(!file.isEmpty()){
            String fileExtension= FileNameGenerator.getFileExtension(file.getOriginalFilename());
            String filename=FileNameGenerator.generateFileName(user,file)+fileExtension;
            String uploadDirectory= DefaultSaveLocations.DEFAULT_USERS_IMAGE_SAVE;
            try {
                if (!file.isEmpty()) {
                    FileUploader.saveFileToLocalDirectory(file, uploadDirectory, filename);
                    userService.deletePreviousImage(user);
                    user.setImage("/"+uploadDirectory+filename);
                    userService.save(user);
                    return ResponseEntity.ok("Profile Picture updated successfully!");
                }
            }catch (IOException e){
                System.out.println("There was a problem Saving the image S K I P Z "+ e);
                e.printStackTrace();
                System.out.println("-------------------");
            }
        }
        return ResponseEntity.badRequest().body("File Field can't be empty.");
    }

    @GetMapping("/get/pfp")
    public String getProfilePic(){
        System.out.println("executed");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return "error loading pfp,user not authenticated";
        }
        boolean isAdminOrUser = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") || role.getAuthority().equals("ROLE_USER"));

        User user = userService.getUserByUsername(authentication.getName()).get();
//        System.out.println(user.toString());

        if(isAdminOrUser){
//            System.out.println("returned");
            return user.getImage();
        }else {
            return GUIconstants.DEFAULT_PROFILE_PICTURE;
        }
    }
}