package com.uniroma3.it.gastroguide.impl;

import com.uniroma3.it.gastroguide.constants.DefaultSaveLocations;
import com.uniroma3.it.gastroguide.dtos.IngredientDto;
import com.uniroma3.it.gastroguide.dtos.RecipeDto;
import com.uniroma3.it.gastroguide.dtos.StepDto;
import com.uniroma3.it.gastroguide.dtos.TagDto;
import com.uniroma3.it.gastroguide.models.*;
import com.uniroma3.it.gastroguide.repositories.RecipeRepository;
import com.uniroma3.it.gastroguide.services.*;
import com.uniroma3.it.gastroguide.utils.FileNameGenerator;
import com.uniroma3.it.gastroguide.utils.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Qualifier("recipeServiceImpl")
public class RecipeServiceImpl implements RecipeService {


    private RecipeRepository recipeRepository;

    private ReviewService reviewService;


    private StepService stepService;


    private UserService userService;

    private IngredientService ingredientService;

    private TagService tagService;

    private RecipeImageService recipeImageService;


    @Autowired
    public RecipeServiceImpl(ReviewService reviewService, StepService stepService,
                             UserService userService, RecipeRepository recipeRepository,
                             IngredientService ingredientService, TagService tagService,
                             RecipeImageService recipeImageService){
        super();
        this.reviewService=reviewService;
        this.stepService=stepService;
        this.recipeRepository=recipeRepository;
        this.userService=userService;
        this.ingredientService=ingredientService;
        this.tagService=tagService;
        this.recipeImageService=recipeImageService;
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
    public Double getAverageDoubleRating(Recipe recipe) {
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
    public List<Recipe> getLatest3() {
        return recipeRepository.findAll(Sort.by(Sort.Direction.DESC, "id")).stream()
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public void createFromDto(RecipeDto recipeDto) {
        Recipe recipe=new Recipe(recipeDto.getName(),recipeDto.getDescription());
        List<Step> steps= new ArrayList<>();
        List<Ingredient> ingredients= new ArrayList<>();
        Set<Tag> tags=new HashSet<>();
        User user = userService.getUserById(recipeDto.getUserId()).get();
        recipe.setUser(user);
        for(StepDto s: recipeDto.getSteps()){
            Step step=new Step();
            step.setTitle(s.getTitle());
            step.setBody(s.getBody());
            step.setRecipe(recipe);
            step.setEstimatedDuration(s.getEstimatedDuration());
            step.setUser(user);
            steps.add(step);
            stepService.saveOrUpdate(step);
        }
        for(IngredientDto i: recipeDto.getIngredients()){
            Optional<Ingredient> optionalIngredient=ingredientService.findByName(i.getName());
            Ingredient ingredient;
            if(!optionalIngredient.isPresent()) {
                ingredient = new Ingredient();
                ingredient.setName(i.getName());
                ingredient.setQuantity(i.getQuantity());
            }else{
                ingredient = optionalIngredient.get();
            }
            ingredient.addRecipe(recipe);
            ingredientService.saveOrUpdate(ingredient);
            ingredients.add(ingredient);
        }

        for(TagDto t: recipeDto.getTagDtos()){
            Optional<Tag> tagOptional = tagService.findByTitle(t.getName());
            Tag tag;
            if (!tagOptional.isPresent()){
                tag = new Tag();
                tag.setTitle(t.getName());
            }else{
                tag = tagOptional.get();
            }
            tag.addRecipe(recipe);
            tags.add(tag);
            tagService.saveOrUpdate(tag);
        }
        List<MultipartFile> files= recipeDto.getImages();
        String uploadDirectory= DefaultSaveLocations.DEFAULT_RECIPE_IMAGE_SAVE;
        recipe.setTags(tags);
        recipe.setSteps(steps);
        recipe.setIngredients(ingredients);

        Recipe savedRecipe= recipeRepository.save(recipe);

        try{
            if (files!=null){
                for (MultipartFile file: files) {
                    String fileExtension= FileNameGenerator.getFileExtension(file.getOriginalFilename());
                    String filename=FileNameGenerator.generateFileName(recipeDto,file)+fileExtension;
                    FileUploader.saveFileToLocalDirectory(file,uploadDirectory,filename);
                    RecipeImage recipeImage= new RecipeImage(filename,savedRecipe);
                    savedRecipe.addImage( recipeImageService.save(recipeImage));
                    recipeRepository.save(savedRecipe);
                }
            }
        }catch (IOException e){
            System.out.println("There was a problem Saving the image S K I P Z "+ e);
            System.out.print("CAUTION: Deleting Film Instance!");
            recipeRepository.delete(savedRecipe);
            e.printStackTrace();
            System.out.println("-------------------");
        }

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
            Double averageRating = getAverageDoubleRating((Recipe) film);
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
