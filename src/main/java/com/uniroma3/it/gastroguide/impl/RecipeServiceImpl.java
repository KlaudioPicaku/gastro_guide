package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.models.*;
import com.uniroma3.it.gastroguide.repositories.RecipeRepository;
import com.uniroma3.it.gastroguide.repositories.ReviewRepository;
import com.uniroma3.it.gastroguide.repositories.StepRepository;
import com.uniroma3.it.gastroguide.repositories.UserRepository;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.ReviewService;
import com.uniroma3.it.gastroguide.services.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
@Qualifier("recipeServiceImpl")
public class RecipeServiceImpl implements RecipeService {


    private RecipeRepository recipeRepository;

    private ReviewService reviewService;

    private StepRepository stepRepository;

    private StepService stepService;

    private UserRepository userRepository;

    private ReviewRepository reviewRepository;


    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,ReviewService reviewService,StepRepository stepRepository,StepService stepService,UserRepository userRepository,ReviewRepository reviewRepository){
        super();
        this.recipeRepository=recipeRepository;
        this.reviewService=reviewService;
        this.stepService=stepService;
        this.stepRepository=stepRepository;
        this.userRepository=userRepository;
        this.reviewRepository=reviewRepository;

    }



    @Override
    public List<Recipe> findAll() {return recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));}

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public List<Recipe> findByNameContainingIgnoreCase(String name) {
       return recipeRepository.findAllByNameContainingIgnoreCase(name);
    }

    @Override
    public void saveOrUpdate(Recipe recipe) {
        recipeRepository.save(recipe);
    }


    @Override
    public void delete(Long id) {

//        List<Review> reviews= reviewRepository.findAllByFilm(filmRepository.findById(id).get());
//        for(Review r : reviews){
//            reviewRepository.delete(r);
//        }
        Recipe recipe = recipeRepository.findById(id).get();
        recipeRepository.delete(recipe);
    }

    @Override
    public List<Recipe> findByUser(User user){
        return recipeRepository.findAllByUser(user);
    }

    @Override
    public String getAverageRating(Recipe recipe) {
        List<Review> reviews = reviewService.findAllByRecipe(recipe);
        OptionalDouble averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average();

        if (averageRating.isPresent()) {
            double average = averageRating.getAsDouble();

            return String.valueOf( Math.round(average*100.0)/100.0);
        }

        return "No Ratings";
    }

    @Override
    public Double getAveragDoubleRating(Recipe recipe) {
        List<Review> reviews = reviewService.findAllByRecipe(recipe);
        OptionalDouble averageRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .average();
        if(averageRating.isPresent()){
            return (Math.round(averageRating.getAsDouble()*100.0)/100.0);

        }
        return null;
    }


    @Override
    public List<Recipe> findByIngredient(Ingredient ingredient) {
        return recipeRepository.findAllByIngredientsContaining(ingredient);
    }

    @Override
    public List<Recipe> findTopThree() {
        List<Recipe> recipes = this.findAll();
        //tenendo conto che getAverageDouble può ritornare null, in quel caso la media delle review sarà 0
        recipes.sort(Comparator.comparingDouble(film -> {
            Double averageRating = getAveragDoubleRating((Recipe) film);
            return (averageRating != null) ? averageRating : 0.0;
        }).reversed());

        return recipes.subList(0, Math.min(recipes.size(), 3));
    }

    @Override
    public List<Recipe> searchByTerm(String term) {
        return recipeRepository.findAllByNameContainingIgnoreCase(term);
    }

    public List<Step> filmDescriptionByFilm(Recipe recipe){
        return stepService.findAllByRecipeId(recipe.getId());
    }
}
