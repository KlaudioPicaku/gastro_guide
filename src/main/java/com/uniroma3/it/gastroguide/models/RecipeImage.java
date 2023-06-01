package com.uniroma3.it.gastroguide.models;


import com.uniroma3.it.gastroguide.constants.DefaultSaveLocations;
import jakarta.persistence.*;

@Entity
@Table(name = "recipe_image")
public class RecipeImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public RecipeImage(){}
    public RecipeImage(String filePath, Recipe recipe) {
        super();
        this.filePath = filePath;
        this.recipe = recipe;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    @Transient
    public String getUrl(){
        if (this == null || id == null) return null;

        return "/"+ DefaultSaveLocations.DEFAULT_RECIPE_IMAGE_SAVE + this.filePath;
    }
    @Transient
    public String getRelativeUrl(){
        if (this == null || id == null) return null;

        return DefaultSaveLocations.DEFAULT_RECIPE_IMAGE_SAVE + this.filePath;
    }

    public Long getId() {
        return id;
    }
}
