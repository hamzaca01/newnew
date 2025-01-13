package com.example.school;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.school.Config.AppDatabase;
import com.example.school.models.TimeTable;

import java.util.Calendar;
import java.util.Date;

public class AddTimetableActivity extends AppCompatActivity {

    private EditText etTitle, etDescription;
    private Spinner spinnerType;
    private Button btnStartTime, btnEndTime, btnSave;
    private Calendar startTime, endTime;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // narbtou el activity bel xml mtaaha
        setContentView(R.layout.activity_add_timetable);

        // Initialize Views
        etTitle = findViewById(R.id.etTitle);
        etDescription = findViewById(R.id.etDescription);
        spinnerType = findViewById(R.id.spinnerType);
        btnStartTime = findViewById(R.id.btnStartTime);
        btnEndTime = findViewById(R.id.btnEndTime);
        btnSave = findViewById(R.id.btnSave);

        // Initialize Calendar instances
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();

        // Initialize Room Database
        // zouz anwa3 bd fil android
        // - extern kifma mysql mongo
        // - intern kifma room wela sqlite
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "school_database").build();

        // Populate Spinner with Data
        //
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.timetable_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);

        // Time Picker for Start Time
        btnStartTime.setOnClickListener(v -> showDateTimePicker(startTime, btnStartTime));

        // Time Picker for End Time
        btnEndTime.setOnClickListener(v -> showDateTimePicker(endTime, btnEndTime));

        // Save button logic
        btnSave.setOnClickListener(v -> saveTimetable());
    }

    private void showDateTimePicker(Calendar calendar, Button button) {
        Calendar current = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TimePickerDialog timePicker = new TimePickerDialog(this, (view1, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                button.setText(calendar.getTime().toString());
            }, current.get(Calendar.HOUR_OF_DAY), current.get(Calendar.MINUTE), true);
            timePicker.show();
        }, current.get(Calendar.YEAR), current.get(Calendar.MONTH), current.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    private void saveTimetable() {
        String title = etTitle.getText().toString().trim();
        String description = etDescription.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();


        if (title.isEmpty() || description.isEmpty() || type.isEmpty() || startTime == null || endTime == null) {
            Toast.makeText(this, "Please fill all fields and select both dates", Toast.LENGTH_SHORT).show();
            return;
        }

        TimeTable entry = new TimeTable(title, description, type, startTime.getTime(), endTime.getTime());

        // Schedule an alarm for the event
        long triggerAtMillis = System.currentTimeMillis() + 60000;
        scheduleTimetableAlert(AddTimetableActivity.this, triggerAtMillis, "Timetable Reminder", "Your class starts soon!");
        // Save to Room Database
        new Thread(() -> database.timetableDao().insertTimetable(entry)).start();

        Toast.makeText(this, "Timetable saved successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }




    public void scheduleTimetableAlert(Context context, long triggerAtMillis, String title, String message) {

        Log.d("Time in Millis : ", " time : "+triggerAtMillis);
        try {
            // deux types de intent
             // - ouvrir un app extern
            Intent intent = new Intent(context, AlarmReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("message", message);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            //long triggerAtMillisone = System.currentTimeMillis() + 60000; // 1 minute
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);

            if (alarmManager != null) {
                Log.d("ScheduleAlert", "Alarm set for: " + triggerAtMillis);
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent);
                Toast.makeText(context, "Timetable alert scheduled", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("ScheduleAlert", "AlarmManager is null");
            }

        } catch (Exception e) {
            Log.e("ScheduleAlert", "Error scheduling alarm: " + e.getMessage());
        }
    }







}
