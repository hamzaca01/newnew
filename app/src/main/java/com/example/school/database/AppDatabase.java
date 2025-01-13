package com.example.school.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.school.model.Exam;

// AppDatabase should be a singleton to avoid multiple instances
@Database(entities = {Exam.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    // Define the DAO method that will access the Exam table
    public abstract ExamDao examDao();

    private static volatile AppDatabase INSTANCE;

    // Singleton method to get the database instance
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Initialize the database with Room
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "exam_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
