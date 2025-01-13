package com.example.school.Config;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import android.content.Context;

import com.example.school.Dao.TimetableDao;
import com.example.school.models.TimeTable;


// Define the AppDatabase class
@Database(entities = {TimeTable.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    // Singleton instance
    private static volatile AppDatabase INSTANCE;

    // DAO for accessing Timetable data
    public abstract TimetableDao timetableDao();

    // Method to get the database instance
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create the database
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "school_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

    // tesna3 instance men appdatabase bech nestaamlouha fil activity
    public static AppDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    // Create the database
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "school_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}

