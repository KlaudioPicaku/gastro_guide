package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.exposed.RecipePublic;
import com.uniroma3.it.gastroguide.models.SearchResult;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AutocompleteController {
    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;



    @GetMapping("/api/searchbar/autocomplete")
    @ResponseBody
    public ResponseEntity<List<SearchResult>> searchResultResponseEntity(@RequestParam("term") String term){
        List<SearchResult> searchResults= new ArrayList<>();

        List<RecipePublic> recipePublics = recipeService.searchPublicByTerm(term);

        for(RecipePublic r: recipePublics){
            searchResults.add(new SearchResult(r));
        }

        List<User> users = userService.findByFirstNameContainingIgnoreCaseLastNameContainingIgnoreCase(term);

        for(User u: users){
            searchResults.add(new SearchResult(u));
        }

        return ResponseEntity.ok(searchResults);

    }

}

