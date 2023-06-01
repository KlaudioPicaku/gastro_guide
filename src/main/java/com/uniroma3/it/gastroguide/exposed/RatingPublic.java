package com.uniroma3.it.gastroguide.exposed;

public class RatingPublic {

    private Long recipeId;

    private String ratingValue;

    private String reviewCount;


    public RatingPublic(Long recipeId,String ratingValue,String reviewCount){
        this.recipeId=recipeId;
        this.ratingValue=ratingValue;
        this.reviewCount=reviewCount;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }
}
