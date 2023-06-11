package com.uniroma3.it.gastroguide.dtos;

import jakarta.validation.constraints.NotEmpty;

public class IngredientDto {

    private double quantity;

    @NotEmpty
    private String name;

   public IngredientDto(){

   }

    public IngredientDto(double quantity, String name) {
        this.quantity = quantity;
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IngredientDto{" +
                "quantity=" + quantity +
                ", name='" + name + '\'' +
                '}';
    }
}
