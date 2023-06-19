package com.uniroma3.it.gastroguide.dtos;


import jakarta.validation.constraints.NotEmpty;

public class UserProfileDto {

    @NotEmpty
    private String username;

    private String image;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    public UserProfileDto(){

    }

    public UserProfileDto(String username, String image, String firstName, String lastName) {
        this.username = username;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
