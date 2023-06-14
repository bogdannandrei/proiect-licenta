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
    ArrayList<Food> filteredListBreakfast;

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }

    private OnItemClickListener mListener;

    public AddFoodToDiaryAdapter(Context context, ArrayList<Food> list) {
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

        Food food = filteredListBreakfast.get(position);
        holder.foodName.setText(food.getFoodName());
        holder.calories.setText(String.valueOf(food.getCalories()));
        holder.servingSize.setText("100 gram");
        holder.brandName.setText(food.getBrandName());
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
            for (Food food : list) {
                String foodName = food.getFoodName();
                String brandName = food.getBrandName();

                if (foodName != null && foodName.toLowerCase().contains(text)) {
                    filteredListBreakfast.add(food);
                } else if (brandName != null && brandName.toLowerCase().contains(text)) {
                    filteredListBreakfast.add(food);
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

            foodName = itemView.findViewById(R.id.foodName);
            calories = itemView.findViewById(R.id.caloriesNumber);
            servingSize = itemView.findViewById(R.id.servingSize);
            brandName = itemView.findViewById(R.id.brandName);

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
