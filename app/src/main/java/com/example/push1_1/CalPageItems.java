package com.example.push1_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CalPageItems {

    public String todo;
    String location;
    String date;
    HashMap<String, Integer> details;

    public CalPageItems(String todo, String location, String date, LinkedHashMap<String, Integer> details) {
        this.todo = todo;
        this.location = location;
        this.date = date;
        this.details = details;
    }


}
