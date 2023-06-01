package com.uniroma3.it.gastroguide.exposed;

import com.uniroma3.it.gastroguide.constants.StaticURLs;

import java.util.List;
import java.util.Map;

public class RecipePublic {
    private Long id;
    private String coverPath;

    private String name;

    private String authorName;


    private String description;


    private RatingPublic rating;

    private double estimatedDuration;

    private int numberOfSteps;

    private List<String> tags;

    public RecipePublic(Long id,String coverPath, String name, String authorName, String description, String ratingValue,String reviewCount, double estimatedDuration, int numberOfSteps,List<String> tags) {
        this.id=id;
        this.coverPath = coverPath;
        this.name = name;
        this.authorName = authorName;
        this.description = description;
        this.rating = new RatingPublic(id,ratingValue,reviewCount);
        this.estimatedDuration = estimatedDuration;
        this.numberOfSteps = numberOfSteps;
        this.tags=tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getAbsoluteUrl(){ return StaticURLs.RECIPE_DETAIL_URL+this.getId(); }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RatingPublic getRating() {
        return rating;
    }

    public void setRating(RatingPublic rating) {
        this.rating = rating;
    }

    public double getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(double estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

}
