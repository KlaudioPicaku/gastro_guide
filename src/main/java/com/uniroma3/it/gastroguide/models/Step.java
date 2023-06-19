package com.uniroma3.it.gastroguide.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "steps")
public class Step {
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    @Id
    private Long id;

    @Column(name = "title", nullable = false, length = 256)
    private String title;


    @Column(name = "body", nullable = false, length = 1764)
    private String body;

    @Column(name = "createdOn", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "updatedOn", nullable = false)
    private LocalDateTime updatedOn;

    @Column(name="estimatedDuration")
    private Double estimatedDuration;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;



    public Step(){
        this.createdOn=LocalDateTime.now();
        this.updatedOn=LocalDateTime.now();

    }
    public Step(String title, String body, LocalDateTime createdOn, LocalDateTime updatedOn, User user, Recipe recipe,Double estimatedDuration) {
        this.title = title;
        this.body = body;
        this.createdOn = LocalDateTime.now();
        this.updatedOn =  LocalDateTime.now();
        this.user = user;
        this.recipe = recipe;
        this.estimatedDuration=estimatedDuration;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", createdOn=" + createdOn +
                ", updatedOn=" + updatedOn +
                ", user=" + user +
                ", recipe=" + recipe +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Double getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Double estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
}
