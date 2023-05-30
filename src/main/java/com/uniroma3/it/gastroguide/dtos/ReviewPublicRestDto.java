package com.uniroma3.it.gastroguide.dtos;

import com.uniroma3.it.gastroguide.models.Recipe;

public class ReviewPublicRestDto {
    Long reviewId;
    String reviewTitle;
    String reviewBody;

    String reviewAuthor;

    int reviewRating;

    String reviewPic;

    Recipe recipe;


    public ReviewPublicRestDto(String reviewTitle, String reviewBody, String reviewAuthor, int reviewRating, String reviewPic, Long reviewId) {
        this.reviewTitle = reviewTitle;
        this.reviewBody = reviewBody;
        this.reviewAuthor = reviewAuthor;
        this.reviewRating = reviewRating;
        this.reviewPic=reviewPic;
        this.reviewId=reviewId;


    }

    public Recipe getFilm() {
        return recipe;
    }

    public void setFilm(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getReviewPic() {
        return reviewPic;
    }

    public void setReviewPic(String reviewPic) {
        this.reviewPic = reviewPic;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }
}
