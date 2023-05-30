package com.uniroma3.it.gastroguide.utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class JSONIntArrayStringtoArrayListConverter {
public static ArrayList<Integer> convertJsonIntegerStringToIntegerArrayList(String jsonString) throws JsonProcessingException {

    ObjectMapper objectMapper= new ObjectMapper();

    ArrayList<Integer> list = objectMapper.readValue(jsonString, new TypeReference<ArrayList<Integer>>() {});

    return list;
}


}
