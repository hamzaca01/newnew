package com.example.school.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "timetable")
public class TimeTable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String type; // e.g., "Course" or "Exam"
    private Date startTime; // Epoch time
    private Date endTime; // Epoch time

    public TimeTable(String title, String description, String type, Date startTime, Date endTime) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeTable() {
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public void setId(int id) {
        this.id = id;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }


}
