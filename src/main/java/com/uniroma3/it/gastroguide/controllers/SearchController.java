package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.exposed.RecipePublic;
import com.uniroma3.it.gastroguide.models.SearchResult;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.RecipeService;
import com.uniroma3.it.gastroguide.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class SearchController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;


    @GetMapping("/search")
    public String searchResults(@RequestParam(value = "term", required = false) String term,
                                @RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "tag", required = false) String tag,
                                Model model, HttpServletRequest request) {
        List<SearchResult> searchResults = new ArrayList<>();
        Boolean populated=false;
        if(term !=null && term.equals("all")){
            searchResults=recipeService.findAllRecipePublics().stream().map(SearchResult :: new).collect(Collectors.toList());
            populated=true;
        }
        if ((tag == null || (tag !=null && tag.isEmpty())) && !populated) {
            List<RecipePublic> recipePublics = recipeService.searchPublicByTerm(term);
            List<User> users = userService.findByFirstNameContainingIgnoreCaseLastNameContainingIgnoreCase(term);

            searchResults = Stream.concat(
                            recipePublics.stream().map(SearchResult::new),
                            users.stream().map(SearchResult::new)
                    )
                    .collect(Collectors.toList());
        } else if(tag !=null && !tag.isEmpty() && !populated) {
            searchResults = recipeService.findByTagTitle(tag).stream()
                    .map(SearchResult::new)
                    .collect(Collectors.toList());
        }
        model.addAttribute("tag",tag);

        int pageSize = 10;
        int startIndex = (page != null) ? (page - 1) * pageSize : 0;
        int endIndex = Math.min(startIndex + pageSize, searchResults.size());

        List<SearchResult> searchResultsSublist = searchResults.subList(Math.max(startIndex, 0), endIndex);

        int maxNumberOfPages = (int) Math.ceil((double) searchResults.size() / pageSize);

        model.addAttribute("request", request);
        model.addAttribute("searchResults", searchResultsSublist);
        model.addAttribute("page", page);
        model.addAttribute("term", term);
        model.addAttribute("maxNumberOfPages", maxNumberOfPages);

        return "search";
    }


}