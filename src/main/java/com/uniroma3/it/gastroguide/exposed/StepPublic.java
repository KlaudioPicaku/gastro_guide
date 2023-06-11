package com.uniroma3.it.gastroguide.exposed;

import com.uniroma3.it.gastroguide.models.Step;

public class StepPublic {

    String name;

    String description;


    Double estimatedDuration;


    public StepPublic(){}


    public StepPublic(String name, String description, Double estimatedDuration) {
        this.name = name;
        this.description = description;
        this.estimatedDuration = estimatedDuration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Double estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
}
