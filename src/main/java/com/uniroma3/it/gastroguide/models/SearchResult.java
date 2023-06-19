package com.uniroma3.it.gastroguide.models;

import com.uniroma3.it.gastroguide.exposed.RecipePublic;

public class SearchResult {
    private String name;

    private String imagePath;


    private String absoluteUrl;

    private String description;

    public SearchResult(){}

    public SearchResult(RecipePublic recipe){
        this.name=recipe.getName();
        this.imagePath= recipe.getCoverPath();
        this.absoluteUrl=recipe.getAbsoluteUrl();
        this.description =recipe.getDescription();
    }

    public SearchResult(User user){
        this.name=user.getFullName();
        this.imagePath=user.getImage();
        this.absoluteUrl=user.getAbsoluteUrl();
        this.description=user.getDescription();
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }
}