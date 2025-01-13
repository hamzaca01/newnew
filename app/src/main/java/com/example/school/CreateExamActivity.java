package com.example.school;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school.database.AppDatabase;
import com.example.school.model.Exam;

import java.util.Calendar;

public class CreateExamActivity extends AppCompatActivity {
    private EditText nameEditText, dateEditText, locationEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        // Link UI elements
        nameEditText = findViewById(R.id.editTextExamName);
        dateEditText = findViewById(R.id.editTextExamDate);
        locationEditText = findViewById(R.id.editTextExamLocation);
        saveButton = findViewById(R.id.buttonSaveExam);

        // Set DatePicker on dateEditText
        dateEditText.setOnClickListener(v -> showDatePickerDialog());

        // Handle Save Button Click
        saveButton.setOnClickListener(v -> saveExam());
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                CreateExamActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format and set the selected date
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    dateEditText.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void saveExam() {
        String name = nameEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String location = locationEditText.getText().toString();

        if (name.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if an exam with the same name, date, and location already exists
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            Exam existingExam = db.examDao().getExamByDetails(name, date, location);

            if (existingExam != null) {
                // If the exam already exists, show an error message
                runOnUiThread(() -> {
                    Toast.makeText(CreateExamActivity.this, "This exam already exists!", Toast.LENGTH_SHORT).show();
                });
            } else {
                // Otherwise, save the new exam
                Exam exam = new Exam(name, date, location);
                db.examDao().insert(exam);

                runOnUiThread(() -> {
                    Toast.makeText(CreateExamActivity.this, "Exam Saved!", Toast.LENGTH_SHORT).show();
                    finish(); // Go back to the previous screen
                });
            }
        }).start();
    }
}
