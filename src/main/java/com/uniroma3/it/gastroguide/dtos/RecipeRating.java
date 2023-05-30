package com.uniroma3.it.gastroguide.dtos;

public class RecipeRating {
    private String average;
    private int ratings;

    public RecipeRating(String average, int ratings) {

            this.average = average;
        this.ratings = ratings;
}

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public int getRatings() {
        return ratings;
    }

    public void setRatings(int ratings) {
        this.ratings = ratings;
    }
}

