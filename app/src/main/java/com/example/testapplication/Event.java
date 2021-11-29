package com.example.testapplication;

public class Event {
    public String id = "";
    public String title = "";
    public String place = "";
    public String type = "";
    public String dateTime= "";
    public int capacity = 0;
    public double budget = 0;
    public String email= "";
    public String phone= "";
    public String description= "";


    public Event(String id,String title, String place, String type, String dateTime, int capacity, double budget, String email, String phone, String description) {
        this.id = id;
        this.title = title;
        this.place = place;
        this.type = type;
        this.dateTime = dateTime;
        this.capacity = capacity;
        this.budget = budget;
        this.email = email;
        this.phone = phone;
        this.description = description;
    }
}
