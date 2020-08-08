package com.example.pomodoro_app;

// This class is used for the population of Progress's pie chart.
public class Category {
    private String name; // The name of the category
    private int frequency; // How many times the category occurred for the day

    public Category(String name, int frequency){
        this.name = name;
        this.frequency = frequency;
    }

    public String GetName(){
        return this.name;
    }

    public Integer GetFrequency(){
        return this.frequency;
    }

    public void SetName(String name){
        this.name = name;
    }

    public void SetFrequency(Integer frequency){
        this.frequency = frequency;
    }
}
