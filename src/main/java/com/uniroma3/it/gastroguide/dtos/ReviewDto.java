package com.uniroma3.it.gastroguide.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ReviewDto {
    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    private Integer rating;

    @NotNull
    @NotEmpty
    private String body;

    private Long authorId;
    private Long recipeId;



    // Constructors, getters, and setters
    public ReviewDto(){
    }

    public ReviewDto( String title, Integer rating, String body, Long authorId, Long recipeId) {

        this.title = title;
        this.rating = rating;
        this.body = body;
        this.authorId = authorId;
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getRecipeId() {
        return recipeId;
    }


    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", body='" + body + '\'' +
                ", authorId=" + authorId +
                ", recipeId=" + recipeId +
                '}';
    }
}
