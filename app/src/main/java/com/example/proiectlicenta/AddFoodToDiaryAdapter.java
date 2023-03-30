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
    ArrayList<Food> filteredList;

    public interface OnItemClickListener {
        void onItemClick(Food food);
    }

    private OnItemClickListener mListener;

    public AddFoodToDiaryAdapter(Context context, ArrayList<Food> list) {
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
        View v = LayoutInflater.from(context).inflate(R.layout.food,parent,false);
        return new ViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Food food = filteredList.get(position);
        holder.foodName.setText(food.getFoodName());
        holder.calories.setText(food.getCalories() + " cal");
        holder.servingSize.setText("100 gram");
        holder.brandName.setText(food.getBrandName());
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
            for (Food food : list) {
                if (food.getFoodName().toLowerCase().contains(text) ||
                        food.getBrandName().toLowerCase().contains(text)) {
                    filteredList.add(food);
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
                mListener.onItemClick(filteredList.get(position));
            }
        }
    }
}
