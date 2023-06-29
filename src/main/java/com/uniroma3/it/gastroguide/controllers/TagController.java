package com.uniroma3.it.gastroguide.controllers;

import com.uniroma3.it.gastroguide.dtos.TagDto;
import com.uniroma3.it.gastroguide.exposed.ReviewPublic;
import com.uniroma3.it.gastroguide.models.Review;
import com.uniroma3.it.gastroguide.models.Tag;
import com.uniroma3.it.gastroguide.models.User;
import com.uniroma3.it.gastroguide.services.TagService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TagController {

    @Autowired
    TagService tagService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/tags/list_view")
    public String tagListView(@RequestParam(name= "page") int page, HttpServletRequest request, Model model){
        List<Tag> tags= tagService.findAll();
        int pageSize = 10;
        int startIndex = (page - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, tags.size());

        List<Tag> tagSublist;
        if (startIndex <= endIndex) {
            tagSublist=tags.subList(startIndex, endIndex);
        } else {
            tagSublist= Collections.emptyList();
        }
        int maxNumberOfPages=0;
        if(tagSublist.size()>0){
            maxNumberOfPages=(int) Math.ceil((double) tagSublist.size() / pageSize);;
        }

        List<TagDto> tagSublistPublic = tagSublist.stream().map(r -> new TagDto(r.getTitle())).collect(Collectors.toList());
        model.addAttribute("tags",tagSublistPublic);
        model.addAttribute("page",page);
        model.addAttribute("maxNumberOfPages",maxNumberOfPages);
        model.addAttribute("request",request);
        return "tag_list";

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/tag/delete_confirm")
    public String tagRemoveConfirm(@RequestParam(name="tag") String tag, HttpSession session, HttpServletRequest request, Model model){

        Optional<Tag> tagOptional= tagService.findByTitle(tag);

        if(!tagOptional.isPresent()){
            return "error/404";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();


        Tag tagObj=tagOptional.get();
        TagDto tagDto=new TagDto(tagObj.getTitle());
        model.addAttribute("object", tagDto);

        String referer = request.getHeader("Referer");
        if (referer != null) {
            session.setAttribute("previousUrl", referer);
        }
        model.addAttribute("request",request);
        System.out.println(session.getAttribute("previousUrl").toString());
        return "tag_delete_confirm";

    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/tags/delete")
    public String tagDelete(@RequestParam(name="name") String name){

        tagService.deleteByName(name);
        return "redirect:/tags/list_view?page=1";
    }
}
