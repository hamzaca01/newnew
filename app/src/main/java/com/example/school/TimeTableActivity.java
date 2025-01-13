package com.example.school;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Config.AppDatabase;
import com.example.school.models.TimeTable;

import java.util.List;

public class TimeTableActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TimeTableAdapter adapter;
    private List<TimeTable> timetableList; // Timetable is your model class
    private AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_timetable);

        // Initialize database
        appDatabase = AppDatabase.getInstance(this);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data from database
        loadData();

        // Add Button Navigation
        Button addButton = findViewById(R.id.addTimetableButton);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(TimeTableActivity.this, AddTimetableActivity.class);
            startActivity(intent);
        });
    }

    private void loadData() {
        // Use AsyncTask to fetch data from the database
        new AsyncTask<Void, Void, List<TimeTable>>() {
            @Override
            protected List<TimeTable> doInBackground(Void... voids) {
                return appDatabase.timetableDao().getAllTimetables();
            }

            // bridge bin les donnes mel bd w el list items
            @Override
            protected void onPostExecute(List<TimeTable> timetables) {
                timetableList = timetables;
                if (adapter == null) {
                    adapter = new TimeTableAdapter(TimeTableActivity.this, timetableList); // Pass the correct context here
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateData(timetableList); // Ensure to update adapter data
                }
            }
        }.execute();
    }
}

