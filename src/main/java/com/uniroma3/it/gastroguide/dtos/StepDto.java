package com.uniroma3.it.gastroguide.dtos;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class StepDto {
    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String body;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private Double estimatedDuration;
    private Long userId;
    private Long recipeId;

    public StepDto(){
        this.createdOn=LocalDateTime.now();
        this.updatedOn=LocalDateTime.now();
    }

    public StepDto(Long id, String title, String body, LocalDateTime createdOn, LocalDateTime updatedOn, Double estimatedDuration, Long userId, Long recipeId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.estimatedDuration = estimatedDuration;
        this.userId = userId;
        this.recipeId = recipeId;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
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

    public void setCreatedOn() {
        this.createdOn = LocalDateTime.now(ZoneId.systemDefault());
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn() {
        this.updatedOn = LocalDateTime.now(ZoneId.systemDefault());
    }

    public Double getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Double estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "StepDto{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}