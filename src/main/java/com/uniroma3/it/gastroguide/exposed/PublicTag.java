package com.uniroma3.it.gastroguide.exposed;

public class PublicTag {

    private Long id;

    private String name;


    public PublicTag(){}

    public  PublicTag(Long id,String name){
        this.id=id;
        this.name=name;
    }

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
}
