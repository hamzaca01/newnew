package com.example.school;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Dashboard extends AppCompatActivity {

    CardView classWorkCard;
    CardView homeWorkCard;
    CardView assignmentCard;
    CardView attendenceCard;
    CardView timeTableCard;
    CardView holidayCard;
    CardView noticeBoardCard;
    CardView bookdCard;
    CardView activityCard;
    CardView feeduesCard;
    CardView resultCard;
    CardView schoolBusCard;
    CardView navigationCard;
    CardView onlineClassCard;
    CardView tourCard;

    CardView examCard;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_dashboard);

        classWorkCard = findViewById(R.id.classWorkCard);
        homeWorkCard = findViewById(R.id.homeWorkCard);
        assignmentCard = findViewById(R.id.assignmentCard);
        attendenceCard = findViewById(R.id.attendenceCard);
        timeTableCard = findViewById(R.id.timeTableCard);
        holidayCard = findViewById(R.id.holidayCard);
        noticeBoardCard = findViewById(R.id.noticeBoardCard);
        bookdCard = findViewById(R.id.booksCard);
        activityCard = findViewById(R.id.activityCard);
        feeduesCard = findViewById(R.id.feeduesCard);
        resultCard = findViewById(R.id.resultCard);
        schoolBusCard = findViewById(R.id.schoolBusCard);
        navigationCard = findViewById(R.id.navigationCard);
        onlineClassCard = findViewById(R.id.onlineClassCard);
        tourCard = findViewById(R.id.tourCard);
        examCard = findViewById(R.id.examCard);

        // Add click listener for the Exam Card
        examCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ExamHomePageActivity.class);
                startActivity(intent);
            }
        });

        classWorkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, ClassWork.class);
                startActivity(intent);
            }
        });
        classWorkCard.setOnHoverListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_HOVER_ENTER) {
                view.setPointerIcon(PointerIcon.getSystemIcon(this, PointerIcon.TYPE_HAND));
            } else if (motionEvent.getAction() == MotionEvent.ACTION_HOVER_EXIT) {
                view.setPointerIcon(PointerIcon.getSystemIcon(this, PointerIcon.TYPE_ARROW));
            }
            return false; // Allow other listeners to react
        });

        homeWorkCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, HomeWork.class);
                startActivity(intent);
            }
        });
        assignmentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Assignment.class);
                startActivity(intent);
            }
        });
        attendenceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Attendence.class);
                startActivity(intent);
            }
        });
        timeTableCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, TimeTable.class);
                startActivity(intent);
            }
        });
        holidayCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Holiday.class);
                startActivity(intent);
            }
        });
        noticeBoardCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, NoticeBoard.class);
                startActivity(intent);
            }
        });
        bookdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Books.class);
                startActivity(intent);
            }
        });
        activityCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Activity.class);
                startActivity(intent);
            }
        });
        feeduesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Feedues.class);
                startActivity(intent);
            }
        });
        resultCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Result.class);
                startActivity(intent);
            }
        });
        schoolBusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, SchoolBus.class);
                startActivity(intent);
            }
        });
        navigationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Navigation.class);
                startActivity(intent);
            }
        });
        onlineClassCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, OnlineClass.class);
                startActivity(intent);
            }
        });
        tourCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, Tour.class);
                startActivity(intent);
            }
        });
    }

}