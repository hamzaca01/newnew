package com.example.school;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ExamHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_home_page); // Link to your layout

        Button createExamButton = findViewById(R.id.buttonCreateExam);
        Button searchExamButton = findViewById(R.id.buttonSearchExam);
        Button openCameraButton = findViewById(R.id.buttonOpenCamera);


        createExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamHomePageActivity.this, CreateExamActivity.class);
                startActivity(intent);
            }
        });

        searchExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamHomePageActivity.this, SearchExamsActivity.class);
                startActivity(intent);
            }
        });

        // Open Camera Button Click Listener
        openCameraButton.setOnClickListener(v -> openCamera());
    }

    private void openCamera() {
        // Intent to open the camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Check if the camera is available
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(cameraIntent);

            // Show a notification (Toast) when the camera opens
            Toast.makeText(this, "Scan QR codes to learn more about the exam", Toast.LENGTH_LONG).show();
        } else {
            // If no camera is available, notify the user
            Toast.makeText(this, "No camera available on this device", Toast.LENGTH_SHORT).show();
        }
    }
}
