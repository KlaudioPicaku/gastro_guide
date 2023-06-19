package com.uniroma3.it.gastroguide.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.uniroma3.it.gastroguide.constants.DefaultSaveLocations;
import com.uniroma3.it.gastroguide.dtos.IngredientDto;
import com.uniroma3.it.gastroguide.dtos.RecipeDto;
import com.uniroma3.it.gastroguide.dtos.StepDto;
import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.RecipeImage;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.*;
import com.uniroma3.it.gastroguide.utils.FileNameGenerator;
import com.uniroma3.it.gastroguide.utils.FileUploader;
import com.uniroma3.it.gastroguide.utils.JSONIntArrayStringtoArrayListConverter;
import jakarta.servlet.http.HttpServletRequest;
import org.h2.engine.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.extras.springsecurity6.auth.Authorization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @Autowired
    TagService tagService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    StepService stepService;

    @Autowired
    RecipeImageService recipeImageService;



    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @GetMapping("/recipe/create")
    public String getRecipeForm(HttpServletRequest request, Model model){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(auth);
        model.addAttribute("request",request);
        model.addAttribute("recipe",new RecipeDto());
        model.addAttribute("tags",tagService.findAll());
        return "recipe_create_form";
    }

    @PreAuthorize("hasRole('USER') OR hasRole('ADMIN')")
    @PostMapping("/recipe/create")
    public String createRecipe(@ModelAttribute("recipe") RecipeDto recipe, HttpServletRequest request, BindingResult result,Model model){
        if (result.hasErrors()){
            model.addAttribute("request",request);
            return "recipe_create_form";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user= userService.getUserByUsername(auth.getName()).get();
        recipe.setUserId(user.getId());
        System.out.println(recipe.toString());
//        for (StepDto st: recipe.getSteps()){
//            System.out.println(st.toString());
//        }
        recipeService.createFromDto(recipe);

        return "redirect:/";
    }

    @GetMapping("/recipe/detail")
    public String recipeDetail(@RequestParam("id") Long id,HttpServletRequest request, Model model){
        Optional<Recipe> recipe=recipeService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user= userService.findByUsername(authentication.getName());


        model.addAttribute("request",request);
        boolean reviewLeft= false;

        if(user.isPresent()){
            reviewLeft= reviewService.findAllByUserAndRecipe(user.get(),recipe).size() > 0 ;
        }

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") );
        if(isAdmin) {
            model.addAttribute("reviewLeft", false);

        }else{
            model.addAttribute("reviewLeft", reviewLeft);
        }

        int number_of_reviews = reviewService.findAllByRecipe(recipe.get()).size();

        model.addAttribute("number_of_reviews",number_of_reviews);

        if (recipe !=null){
            model.addAttribute("recipe", recipe.get());

            return "recipe_detail";
        }
        return "error/404";
    }
    @GetMapping("/chef/recipe/edit")
    public String recipeEdit(@RequestParam("id") Long id,HttpServletRequest request,Model model){
        Recipe recipe=recipeService.findById((long)id).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN") );
        System.out.println(authentication.getName());
        if(!authentication.getName().equals(recipe.getUser().getUsername())) {
            return "error/403";
        }
        if(recipe!=null){

            model.addAttribute("recipe",recipe);
            model.addAttribute("request",request);
            model.addAttribute("tags",tagService.findAll());
        }
        return "recipe_edit_form";
    }
    @PostMapping("/chef/recipe/edit")
    public String recipeEditPost(@ModelAttribute ("Recipe") Recipe recipe,
                               @RequestParam("selectedImageIds") String selectedImageIds,
                               @RequestParam("uploadedImages") List<MultipartFile> images,
                               @RequestParam("recipeId") Long recipeId,
                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "/recipe_edit_form";
        }
        recipe.setId(recipeId);
        ArrayList<Integer> removedImages= new ArrayList<>();


        try {
            removedImages= JSONIntArrayStringtoArrayListConverter.convertJsonIntegerStringToIntegerArrayList(selectedImageIds);
        } catch (JsonProcessingException e) {
            System.out.println("No images removed S K I P Z !");
        }
        System.out.println(recipe.getId());
        System.out.println();
        System.out.println(removedImages);
        System.out.println(recipe.toString());
        System.out.println();
        if(!removedImages.isEmpty()){
            for(Integer imageId:removedImages){
                System.out.println("to delete "+imageId);
                RecipeImage toRemove=recipeImageService.findById((long)imageId).get();
                recipeImageService.delete(toRemove);
            }
        }
        try{
            String uploadDirectory= DefaultSaveLocations.DEFAULT_RECIPE_IMAGE_SAVE;

            if (images!=null && !images.isEmpty()){
                for (MultipartFile file: images) {
                    if (!file.getOriginalFilename().isEmpty()) {
                        String fileExtension = FileNameGenerator.getFileExtension(file.getOriginalFilename());
                        String filename = FileNameGenerator.generateFileName(recipe, file) + fileExtension;
                        FileUploader.saveFileToLocalDirectory(file, uploadDirectory, filename);
                        RecipeImage recipeImage = new RecipeImage(filename, recipe);
                        recipe.addImage(recipeImageService.save(recipeImage));
                        recipeService.saveOrUpdate(recipe);
                    }
                }
            }
        }catch (IOException e){
            System.out.println("There was a problem Saving the image S K I P Z "+ e);
            System.out.print("CAUTION: Deleting uploaded files");
            e.printStackTrace();
            System.out.println("-------------------");
        }

        recipeService.saveOrUpdate(recipe);
        return "redirect:/recipe/detail?id="+recipe.getId();
    }

}
