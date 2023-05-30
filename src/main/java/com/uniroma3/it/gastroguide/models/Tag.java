package com.uniroma3.it.gastroguide.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
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
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    public Tag(){}
    public Tag(String title, Recipe recipe) {
        this.title = title;
        this.recipe = recipe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
