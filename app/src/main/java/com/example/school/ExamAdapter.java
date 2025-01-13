package com.example.school;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.school.model.Exam;

import java.util.List;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    private List<Exam> examList;
    private OnExamClickListener onExamClickListener;

    public interface OnExamClickListener {
        void onExamClick(Exam exam);
    }

    public ExamAdapter(List<Exam> examList, OnExamClickListener onExamClickListener) {
        this.examList = examList;
        this.onExamClickListener = onExamClickListener;
    }

    @Override
    public ExamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exam_item, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamViewHolder holder, int position) {
        Exam exam = examList.get(position);
        holder.nameTextView.setText(exam.getName());
        holder.dateTextView.setText(exam.getDate());
        holder.locationTextView.setText(exam.getLocation());

        // Set click listener for the exam item
        holder.itemView.setOnClickListener(v -> onExamClickListener.onExamClick(exam));
    }

    @Override
    public int getItemCount() {
        return examList.size();
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, dateTextView, locationTextView;

        public ExamViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewExamName);
            dateTextView = itemView.findViewById(R.id.textViewExamDate);
            locationTextView = itemView.findViewById(R.id.textViewExamLocation);
        }
    }
}
