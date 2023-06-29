package com.uniroma3.it.gastroguide.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {

    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;

    @Column(name = "title", nullable = false, length = 256)
    private String title;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
    @ManyToMany(mappedBy = "tags")
    private Set<Recipe> recipes = new HashSet<>();

    public Tag(){}

    public Tag(String title,User userId){
        this.title=title;
        this.user=userId;
    }
    public Tag(String title, Recipe recipe) {
        this.title = title;
        this.recipes.add(recipe);
    }

    public Long getId() {
        return id;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Recipe> getRecipe() {
        return this.recipes;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "title='" + title + '\'' +
                '}';
    }

    public void setRecipe(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
    public void addRecipe(Recipe recipe){
        this.recipes.add(recipe);
    }
    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
        recipe.getTags().remove(this);
    }

    public void removeTagFromRecipes() {
        for (Recipe recipe : recipes) {
            recipe.getTags().remove(this);
        }
        recipes.clear();
    }
}
