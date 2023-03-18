package com.example.proiectlicenta;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AddFoodToDiaryAdapter extends RecyclerView.Adapter<AddFoodToDiaryAdapter.ViewHolder> {

    Context context;
    ArrayList<Food> list;

    public AddFoodToDiaryAdapter(Context context, ArrayList<Food> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.food,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Food food = list.get(position);
        holder.foodName.setText(food.getFoodName());
        holder.calories.setText(food.getCalories() + " cal");
        holder.servingSize.setText("100 gram");
        holder.brandName.setText(food.getBrandName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

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
        }
    }
}
