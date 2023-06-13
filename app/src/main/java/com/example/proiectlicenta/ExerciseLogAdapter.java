package com.example.proiectlicenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;

public class ExerciseLogAdapter extends RecyclerView.Adapter<ExerciseLogAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ExerciseLog> list;
    private Calendar selectedDate;
    private OnItemClickListener mListener;

    public ExerciseLogAdapter(Context context, ArrayList<ExerciseLog> list, Calendar selectedDate) {
        this.context = context;
        this.list = list;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.exercise_entry, parent, false);
        return new ViewHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(ExerciseLog exerciseLog);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExerciseLog exerciseLog = list.get(position);

        holder.exerciseName.setText(exerciseLog.getExerciseName());
        holder.calories.setText(String.valueOf(exerciseLog.getCaloriesBurned()));
        holder.time.setText(exerciseLog.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView exerciseName;
        TextView calories;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseNameTv);
            calories = itemView.findViewById(R.id.caloriesTv);
            time = itemView.findViewById(R.id.boldTimeTv);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && mListener != null) {
                mListener.onItemClick(list.get(position));
            }
        }
    }
}




