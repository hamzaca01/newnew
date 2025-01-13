package com.example.school;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.DatePickerDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.database.AppDatabase;
import com.example.school.model.Exam;

import java.util.Calendar;
import java.util.List;

public class SearchExamsActivity extends AppCompatActivity {

    private EditText nameEditText, dateEditText, locationEditText;
    private Button searchButton, modifyButton, deleteButton;
    private RecyclerView recyclerView;
    private ExamAdapter examAdapter;
    private Exam selectedExam = null; // Store the selected exam

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display_exam);

        // Initialize UI elements
        nameEditText = findViewById(R.id.editTextSearchName);
        dateEditText = findViewById(R.id.editTextSearchDate);
        locationEditText = findViewById(R.id.editTextSearchLocation);
        searchButton = findViewById(R.id.buttonSearch);
        modifyButton = findViewById(R.id.buttonModifyExam);
        deleteButton = findViewById(R.id.buttonDeleteExam);
        recyclerView = findViewById(R.id.recyclerViewExams);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initially disable Modify and Delete buttons
        modifyButton.setEnabled(false);
        deleteButton.setEnabled(false);

        // Set up click listener for the Date field
        dateEditText.setOnClickListener(v -> showDatePickerDialog());

        searchButton.setOnClickListener(v -> searchExams());

        // Handle Delete Button Click
        deleteButton.setOnClickListener(v -> deleteExam());

        // Handle Modify Button Click
        modifyButton.setOnClickListener(v -> {
            if (selectedExam != null) {
                // Pass the selected exam to EditExamActivity
                Intent intent = new Intent(SearchExamsActivity.this, EditExamActivity.class);
                intent.putExtra("exam_id", selectedExam.getId()); // Pass exam ID to EditExamActivity
                intent.putExtra("exam_name", selectedExam.getName());
                intent.putExtra("exam_date", selectedExam.getDate());
                intent.putExtra("exam_location", selectedExam.getLocation());
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SearchExamsActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format and set the selected date in the EditText
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    dateEditText.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void searchExams() {
        String name = nameEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        String searchName = name.isEmpty() ? null : "%" + name + "%";
        String searchDate = date.isEmpty() ? null : date; // Exact match for date
        String searchLocation = location.isEmpty() ? null : "%" + location + "%";

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<Exam> exams = db.examDao().searchExams(searchName, searchDate, searchLocation);

            runOnUiThread(() -> {
                if (exams.isEmpty()) {
                    Toast.makeText(this, "No exams found for the given criteria.", Toast.LENGTH_SHORT).show();
                }
                examAdapter = new ExamAdapter(exams, this::onExamSelected);
                recyclerView.setAdapter(examAdapter);
            });
        }).start();
    }

    private void onExamSelected(Exam exam) {
        selectedExam = exam; // Set the selected exam
        modifyButton.setEnabled(true); // Enable Modify button
        deleteButton.setEnabled(true); // Enable Delete button
    }

    private void deleteExam() {
        if (selectedExam == null) {
            Toast.makeText(this, "No exam selected to delete.", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            db.examDao().delete(selectedExam);

            runOnUiThread(() -> {
                Toast.makeText(this, "Exam deleted successfully!", Toast.LENGTH_SHORT).show();
                selectedExam = null; // Clear the selected exam
                modifyButton.setEnabled(false); // Disable Modify button
                deleteButton.setEnabled(false); // Disable Delete button
                searchExams(); // Refresh the exam list
            });
        }).start();
    }
}
