package com.example.proiectlicenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddExerciseToDiaryAdapter extends RecyclerView.Adapter<AddExerciseToDiaryAdapter.ViewHolder> {

    Context context;
    ArrayList<ExerciseClass> list;
    ArrayList<ExerciseClass> filteredList;

    public interface OnItemClickListener {
        void onItemClick(ExerciseClass exercise);
    }

    private OnItemClickListener mListener;

    public AddExerciseToDiaryAdapter(Context context, ArrayList<ExerciseClass> list) {
        this.context = context;
        this.list = list;
        this.filteredList = new ArrayList<>(list);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.exercise_class,parent,false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ExerciseClass ex = filteredList.get(position);
        holder.exerciseName.setText(ex.getName());
        holder.caloriesPerHour.setText("Calories per Hour: " + ex.getCaloriesPerHour());
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public void filter(String text) {
        filteredList.clear();
        if (text.isEmpty()) {
            filteredList.addAll(list);
        } else {
            text = text.toLowerCase();
            for (ExerciseClass ex : list) {
                if (ex.getName().toLowerCase().contains(text)) {
                    filteredList.add(ex);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView exerciseName;
        TextView caloriesPerHour;

        private OnItemClickListener mListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            exerciseName = itemView.findViewById(R.id.exerciseName);
            caloriesPerHour = itemView.findViewById(R.id.caloriesPerHour);

            mListener = listener;

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && mListener != null) {
                mListener.onItemClick(filteredList.get(position));
            }
        }
    }
}
