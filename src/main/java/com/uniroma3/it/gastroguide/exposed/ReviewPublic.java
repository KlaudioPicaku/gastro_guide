package com.uniroma3.it.gastroguide.exposed;

import com.uniroma3.it.gastroguide.models.Recipe;

public class ReviewPublic {
        Long reviewId;
        String reviewTitle;
        String reviewBody;

        String reviewAuthor;

        int reviewRating;

        String reviewPic;
        
        Recipe recipe;


        public ReviewPublic(String reviewTitle, String reviewBody, String reviewAuthor, int reviewRating, String reviewPic, Long reviewId, Recipe recipe) {
            this.reviewTitle = reviewTitle;
            this.reviewBody = reviewBody;
            this.reviewAuthor = reviewAuthor;
            this.reviewRating = reviewRating;
            this.reviewPic = reviewPic;
            this.reviewId = reviewId;
            this.recipe = recipe;


        }

        public Recipe getRecipe() {
            return recipe;
        }

        public void setRecipe(Recipe recipe) {
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

        @Override
        public String toString() {
            return "ReviewPublic{" +
                    "reviewId=" + reviewId +
                    ", reviewTitle='" + reviewTitle + '\'' +
                    ", reviewBody='" + reviewBody + '\'' +
                    ", reviewAuthor='" + reviewAuthor + '\'' +
                    ", reviewRating=" + reviewRating +
                    ", reviewPic='" + reviewPic + '\'' +
                    ", recipe=" + recipe +
                    '}';
        }
    }