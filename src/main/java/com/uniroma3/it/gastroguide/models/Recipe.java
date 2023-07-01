package com.uniroma3.it.gastroguide.models;

import com.uniroma3.it.gastroguide.constants.DefaultSaveLocations;
import com.uniroma3.it.gastroguide.constants.StaticURLs;
import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;


    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private List<Step> steps;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "recipe_tag",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();



    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH })
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "recipe",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<RecipeImage> images = new HashSet<>();




    public Recipe(){}

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName(){
        return this.user.getFullName();
    }
    public User getUser(){
        return this.user;
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

    public Long getId() {
        return  this.id;
    }

    @Transient
    public String getCoverPath() {
        List<RecipeImage> sortedImages = new ArrayList<>(this.images);

        Collections.sort(sortedImages, Comparator.comparing(RecipeImage::getId,
                Comparator.nullsFirst(Comparator.naturalOrder())));
        Optional<RecipeImage> filmImage = sortedImages.stream().findFirst();

        if (!filmImage.isPresent() || id == null) return null;

        return "/"+ DefaultSaveLocations.DEFAULT_RECIPE_IMAGE_SAVE + filmImage.get().getFilePath();
    }

    public void setUser(User user) {
        this.user=user;
    }

    public void setTags(Set<Tag> tags) {
        this.tags=tags;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients=ingredients;
    }

    public void setSteps(List<Step> steps) {
        this.steps=steps;
    }

    public void addImage(RecipeImage image) {
        this.images.add(image);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getRecipes().remove(this);
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public String getAuthorAbsoluteUrl(){
        return StaticURLs.CHEF_DETAIL_URL+this.getUser().getFullName();
    }

    public String getAbsoluteUrl(){ return StaticURLs.RECIPE_DETAIL_URL+this.getId(); }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public String getAuthorName(){
        return  this.user.getFullName();
    }

    public List<Step> getSteps() {
        return steps;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", steps=" + steps +
                ", tags=" + tags +
                ", ingredients=" + ingredients +
                '}';
    }

    public Set<RecipeImage> getImages() {
        Set<RecipeImage> sortedImages = new TreeSet<>(Comparator.comparingLong(RecipeImage::getId));
        sortedImages.addAll(this.images);

        return sortedImages;
    }

    public void clearImages() {
        this.images.clear();
    }
}