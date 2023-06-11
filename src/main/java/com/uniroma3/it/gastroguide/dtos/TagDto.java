package com.uniroma3.it.gastroguide.dtos;

import com.uniroma3.it.gastroguide.models.User;
import jakarta.validation.constraints.NotEmpty;

public class TagDto {
    @NotEmpty
    public String name;

    public User userId;

    public TagDto() {}

    public TagDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "name='" + name + '\'' +
                '}';
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
