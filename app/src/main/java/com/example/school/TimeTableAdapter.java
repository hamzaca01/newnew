package com.example.school;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school.Config.AppDatabase;
import com.example.school.models.TimeTable;

import java.util.List;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.ViewHolder> {
    private List<TimeTable> timetableList;
    private Context context;
    private SimpleDateFormat dateFormat;

    public TimeTableAdapter(Context context, List<TimeTable> timetableList) {
        this.context = context;
        this.timetableList = timetableList;
        this.dateFormat = new SimpleDateFormat("hh:mm a, MMM dd yyyy", Locale.getDefault());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timetable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeTable timetable = timetableList.get(position);
        holder.title.setText(timetable.getTitle());
        holder.description.setText(timetable.getDescription());
        holder.type.setText(timetable.getType());
        String formattedStartTime = formatDate(timetable.getStartTime());
        String formattedEndTime = formatDate(timetable.getEndTime());
        holder.start.setText(formattedStartTime);
        holder.end.setText(formattedEndTime);

        // Edit button functionality
        holder.editButton.setOnClickListener(v -> {

            // - navigation entre les activity de l application
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("timeTableId", timetable.getId()); // Pass the ID or data to EditActivity
            context.startActivity(intent);
        });

        // Delete button functionality
        holder.deleteButton.setOnClickListener(v -> {
            TimeTable tt = timetableList.get(position); // Get the timetable to delete

            // Use AppDatabase to delete the timetable
            AppDatabase db = AppDatabase.getInstance(context);
            new Thread(() -> {
                // Delete timetable from the database
                db.timetableDao().deleteTimetableById(tt.getId());

                // Update the UI on the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    timetableList.remove(position); // Remove the item from the list
                    notifyItemRemoved(position); // Notify adapter about the removed item
                    notifyItemRangeChanged(position, timetableList.size()); // Update remaining items
                    Toast.makeText(context, "Timetable deleted successfully", Toast.LENGTH_SHORT).show();
                });
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return timetableList.size();
    }

    private String formatDate(Date date) {
        return date != null ? dateFormat.format(date) : "N/A";
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, type, start, end;
        Button editButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            type = itemView.findViewById(R.id.typeTextView);
            start = itemView.findViewById(R.id.startTimeTextView);
            end = itemView.findViewById(R.id.endTimeTextView);

            editButton = itemView.findViewById(R.id.editButton); // Reference to the edit button
            deleteButton = itemView.findViewById(R.id.deleteButton); // Reference to the delete button
        }
    }

    public void updateData(List<TimeTable> newTimetableList) {
        this.timetableList = newTimetableList;
        notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }
}


