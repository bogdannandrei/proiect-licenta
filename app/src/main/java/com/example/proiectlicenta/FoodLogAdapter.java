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

public class FoodLogAdapter extends RecyclerView.Adapter<FoodLogAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FoodLog> list;
    private Calendar selectedDate;
    private OnItemClickListener mListener;

    public FoodLogAdapter(Context context, ArrayList<FoodLog> list, Calendar selectedDate) {
        this.context = context;
        this.list = list;
        this.selectedDate = selectedDate;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.diary_entry, parent, false);
        return new ViewHolder(v);
    }

    public interface OnItemClickListener {
        void onItemClick(FoodLog foodLog);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodLog foodLog = list.get(position);

        holder.foodName.setText(foodLog.getFoodName());
        holder.calories.setText(String.valueOf(foodLog.getCalories()));
        holder.servingSize.setText(foodLog.getServingSize()+", ");
        holder.brandName.setText(foodLog.getBrandName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView foodName;
        TextView calories;
        TextView servingSize;
        TextView brandName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.foodNameTv);
            calories = itemView.findViewById(R.id.caloriesTv);
            servingSize = itemView.findViewById(R.id.servingSizeTv);
            brandName = itemView.findViewById(R.id.brandNameTv);

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




