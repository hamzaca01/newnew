package com.example.school.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Exam {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String date;
    public String location;

    public Exam(String name, String date, String location) {
        this.name = name;
        this.date = date;
        this.location = location;
    }

    // Getter and Setter for ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters and Setters for other fields
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

