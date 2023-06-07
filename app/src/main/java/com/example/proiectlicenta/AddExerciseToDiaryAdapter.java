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
    ArrayList<ExerciseClass> filteredListBreakfast;

    public interface OnItemClickListener {
        void onItemClick(ExerciseClass exercise);
    }

    private OnItemClickListener mListener;

    public AddExerciseToDiaryAdapter(Context context, ArrayList<ExerciseClass> list) {
        this.context = context;
        this.list = list;
        this.filteredListBreakfast = new ArrayList<>(list);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.food,parent,false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ExerciseClass ex = filteredListBreakfast.get(position);
    }

    @Override
    public int getItemCount() {
        return filteredListBreakfast.size();
    }

    public void filter(String text) {
        filteredListBreakfast.clear();
        if (text.isEmpty()) {
            filteredListBreakfast.addAll(list);
        } else {
            text = text.toLowerCase();
            for (ExerciseClass ex : list) {
                if (ex.getName().toLowerCase().contains(text)) {
                    filteredListBreakfast.add(ex);
                }
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView foodName;
        TextView calories;
        TextView servingSize;
        TextView brandName;

        private OnItemClickListener mListener;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);

            foodName = itemView.findViewById(R.id.foodNameTv);
            calories = itemView.findViewById(R.id.caloriesTv);
            servingSize = itemView.findViewById(R.id.servingSizeTv);
            brandName = itemView.findViewById(R.id.brandNameTv);

            mListener = listener;

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && mListener != null) {
                mListener.onItemClick(filteredListBreakfast.get(position));
            }
        }
    }
}
