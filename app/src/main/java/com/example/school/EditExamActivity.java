package com.example.school;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.DatePickerDialog;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school.database.AppDatabase;
import com.example.school.model.Exam;

public class EditExamActivity extends AppCompatActivity {

    private EditText nameEditText, dateEditText, locationEditText;
    private Button updateButton;

    private int examId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exam);

        // Link UI elements
        nameEditText = findViewById(R.id.editTextExamName);
        dateEditText = findViewById(R.id.editTextExamDate);
        locationEditText = findViewById(R.id.editTextExamLocation);
        updateButton = findViewById(R.id.buttonUpdateExam);

        // Get the exam data passed from SearchExamsActivity
        examId = getIntent().getIntExtra("exam_id", -1);
        String name = getIntent().getStringExtra("exam_name");
        String date = getIntent().getStringExtra("exam_date");
        String location = getIntent().getStringExtra("exam_location");

        // Pre-fill the fields with the current exam details
        nameEditText.setText(name);
        dateEditText.setText(date);
        locationEditText.setText(location);

        // Attach DatePickerDialog to the dateEditText
        dateEditText.setOnClickListener(v -> showDatePickerDialog());

        // Handle Update Button Click
        updateButton.setOnClickListener(v -> updateExam());
    }

    private void showDatePickerDialog() {
        // Get the current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Show DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                EditExamActivity.this, // Updated to the correct context
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Format and set the selected date in the EditText
                    String selectedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    dateEditText.setText(selectedDate);
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void updateExam() {
        String name = nameEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String location = locationEditText.getText().toString();

        if (name.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Exam updatedExam = new Exam(name, date, location);
        updatedExam.setId(examId); // Set the original exam ID for updating

        // Update in Database
        new Thread(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            db.examDao().update(updatedExam);

            runOnUiThread(() -> {
                Toast.makeText(this, "Exam Updated!", Toast.LENGTH_SHORT).show();
                finish(); // Go back to the previous screen
            });
        }).start();
    }
}
