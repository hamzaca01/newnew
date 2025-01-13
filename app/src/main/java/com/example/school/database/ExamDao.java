package com.example.school.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;
import java.util.List;


import com.example.school.model.Exam;

@Dao
public interface ExamDao {
    @Insert
    void insert(Exam exam);

    @Query("SELECT * FROM Exam WHERE name = :name AND date = :date AND location = :location LIMIT 1")
    Exam getExamByDetails(String name, String date, String location);  // Check if exam exists

    @Query("SELECT * FROM Exam WHERE " +
            "(:name IS NULL OR name LIKE :name) AND " +
            "(:date IS NULL OR date = :date) AND " +
            "(:location IS NULL OR location LIKE :location)")
    List<Exam> searchExams(String name, String date, String location);

    @Delete
    void delete(Exam exam);

    @Update
    void update(Exam exam);
}

