package com.example.school;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school.Config.AppDatabase;
import com.example.school.models.TimeTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EditActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private Spinner spinnerType;
    private Button editStartTimeButton, editEndTimeButton, saveButton, cancelButton;
    private Date startTime, endTime;
    private SimpleDateFormat dateTimeFormat;
    private TimeTable currentTimeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Initialize views
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        spinnerType = findViewById(R.id.spinnerType);
        editStartTimeButton = findViewById(R.id.editStartTimeButton);
        editEndTimeButton = findViewById(R.id.editEndTimeButton);
        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);

        dateTimeFormat = new SimpleDateFormat("hh:mm a, MMM dd yyyy", Locale.getDefault());

        // Setup spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.timetable_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        // Get the TimeTable ID passed from the intent
        int timeTableId = getIntent().getIntExtra("timeTableId", -1); // Default value set to -1
        if (timeTableId != -1) {
            // Fetch the TimeTable from the database using the ID
            currentTimeTable = fetchTimeTableById(timeTableId);
            if (currentTimeTable != null) {
                populateFields(currentTimeTable);
            }
        }

        // Button click listeners for date/time pickers
        editStartTimeButton.setOnClickListener(v -> showDateTimePicker(true));
        editEndTimeButton.setOnClickListener(v -> showDateTimePicker(false));

        // Save button listener
        saveButton.setOnClickListener(v -> {
            saveChanges();
            finish(); // Return to the previous activity
        });

        // Cancel button listener
        cancelButton.setOnClickListener(v -> finish());
    }

    private TimeTable fetchTimeTableById(int id) {
        Future<TimeTable> future = Executors.newSingleThreadExecutor().submit(() -> {
            return AppDatabase.getInstance(this).timetableDao().findById(id);
        });
        try {
            return future.get(); // Synchronous blocking call on a background thread
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void populateFields(TimeTable timetable) {
        editTitle.setText(timetable.getTitle());
        editDescription.setText(timetable.getDescription());
        spinnerType.setSelection(((ArrayAdapter) spinnerType.getAdapter())
                .getPosition(timetable.getType()));

        startTime = timetable.getStartTime();
        endTime = timetable.getEndTime();

        if (startTime != null) {
            editStartTimeButton.setText(dateTimeFormat.format(startTime));
        }
        if (endTime != null) {
            editEndTimeButton.setText(dateTimeFormat.format(endTime));
        }
    }

    private void showDateTimePicker(boolean isStartTime) {
        Calendar calendar = Calendar.getInstance();

        // Initialize with current date/time or previously set date/time
        if (isStartTime && startTime != null) {
            calendar.setTime(startTime);
        } else if (!isStartTime && endTime != null) {
            calendar.setTime(endTime);
        }

        // Date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    // Time picker dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            this,
                            (timeView, hourOfDay, minute) -> {
                                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                calendar.set(Calendar.MINUTE, minute);

                                Date selectedDate = calendar.getTime();

                                if (isStartTime) {
                                    startTime = selectedDate;
                                    editStartTimeButton.setText(dateTimeFormat.format(startTime));
                                } else {
                                    endTime = selectedDate;
                                    editEndTimeButton.setText(dateTimeFormat.format(endTime));
                                }
                            },
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            false // Use 12-hour format
                    );
                    timePickerDialog.show();
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void saveChanges() {
        new Thread(() -> {
            // Update the currentTimeTable object with the new values
            currentTimeTable.setTitle(editTitle.getText().toString());
            currentTimeTable.setDescription(editDescription.getText().toString());
            currentTimeTable.setType(spinnerType.getSelectedItem().toString());
            currentTimeTable.setStartTime(startTime);
            currentTimeTable.setEndTime(endTime);

            // Save the changes to the database
            AppDatabase.getInstance(this).timetableDao().updateTimetable(currentTimeTable);
        }).start();

    }
}


