package com.example.school.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.school.models.TimeTable;

import java.util.List;

@Dao
public interface TimetableDao {

    @Insert
    void insertTimetable(TimeTable timetable);

    @Query("SELECT * FROM timetable")
    List<TimeTable> getAllTimetables();

    @Query("SELECT * FROM timetable WHERE id = :id")
    TimeTable findById(int id);

    @Update
    void updateTimetable(TimeTable timetable);

    @Delete
    void deleteTimetable(TimeTable timetable);

    @Query("DELETE FROM timetable WHERE id = :id")
    void deleteTimetableById(int id);
}
