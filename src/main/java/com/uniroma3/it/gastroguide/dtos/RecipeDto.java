package com.uniroma3.it.gastroguide.dtos;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class RecipeDto {
    private Long id;
    @NotEmpty
    private String name;

    @NotEmpty
    private String description;
    private Long userId;

    @NotEmpty
    private List<MultipartFile> images;
    @NotEmpty
    private List<IngredientDto> ingredients;

    @NotEmpty
    private List<StepDto> steps;

    private List<TagDto> tagDtos;

    public RecipeDto(){
        this.ingredients= new ArrayList<>();
        this.ingredients.add(new IngredientDto());
        this.steps=new ArrayList<>();
        this.steps.add(new StepDto());
        this.tagDtos=new ArrayList<>();
        tagDtos.add(new TagDto());
    }
    public RecipeDto(Long id, String name, String description, Long userId, List<MultipartFile> images,List<IngredientDto> ingredients,List<StepDto> steps) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.images=images;
        this.ingredients=ingredients;
        this.steps=steps;
    }

    public List<TagDto> getTagDtos() {
        return tagDtos;
    }

    public void setTagDtos(List<TagDto> tagDtos) {
        this.tagDtos = tagDtos;
    }

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public List<StepDto> getSteps() {
        return steps;
    }

    public void setSteps(List<StepDto> steps) {
        this.steps = steps;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RecipeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", images=" + images +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                ", tagDtos=" + tagDtos +
                '}';
    }
}
