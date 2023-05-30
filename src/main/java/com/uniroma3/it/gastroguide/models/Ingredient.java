package com.uniroma3.it.gastroguide.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "ingredient")
public class Ingredient {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private Double quantity;

        @ManyToMany(mappedBy = "ingredients")
        private List<Recipe> recipes;

        // Constructors, getters, and setters

        public Ingredient() {
        }

        public Ingredient(String name, Double quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        // Getters and setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Double getQuantity() {
            return quantity;
        }

        public void setQuantity(Double quantity) {
            this.quantity = quantity;
        }
    }
