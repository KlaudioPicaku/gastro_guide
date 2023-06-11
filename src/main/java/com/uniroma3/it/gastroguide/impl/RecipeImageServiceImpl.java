package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.models.Recipe;
import com.uniroma3.it.gastroguide.models.RecipeImage;
import com.uniroma3.it.gastroguide.repositories.RecipeImageRepository;
import com.uniroma3.it.gastroguide.services.RecipeImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service

public class RecipeImageServiceImpl implements RecipeImageService {

    @Autowired
    private RecipeImageRepository recipeImageRepository;

    @Override
    public List<RecipeImage> findAll() {
        return recipeImageRepository.findAll();
    }
    @Override
    public void delete(RecipeImage recipeImage) {
        String filePath="";
        if(recipeImage!=null){
            filePath=recipeImage.getRelativeUrl();
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

        recipeImageRepository.delete(recipeImage);
    }
    @Override
    public void saveOrUpdate(RecipeImage recipeImage) {
        RecipeImage previous = recipeImageRepository.findByFilePathContaining(recipeImage.getFilePath());
        if(previous!=null){
            this.delete(previous);
        }
        recipeImageRepository.save(recipeImage);
    }

    @Override
    public Optional<RecipeImage> findById(Long Id) {
        return recipeImageRepository.findById(Id);
    }

    @Override
    public List<RecipeImage> findByRecipeId(Long recipeId) {
       return recipeImageRepository.findByRecipeId(recipeId);
    }

    @Override
    public RecipeImage save(RecipeImage recipeImage) {
        return recipeImageRepository.save(recipeImage);
    }
}
