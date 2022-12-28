package com.example.push1_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CalPageItems {

    String todo;
    String location;
    String date;
    String Lat;
    String Lng;
    HashMap<String, Integer> details;

    public CalPageItems(String todo, String location, String date, String Lat, String Lng, LinkedHashMap<String, Integer> details) {
        this.todo = todo;
        this.location = location;
        this.date = date;
        this.Lat = Lat;
        this.Lng = Lng;
        this.details = details;
    }


}
