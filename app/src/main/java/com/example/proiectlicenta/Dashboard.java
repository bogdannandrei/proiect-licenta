package com.example.proiectlicenta;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Dashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Dashboard extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    DatabaseReference dbrFood;
    String full_name;
    double total_calories;
    double daily_carb;
    double daily_protein;
    double daily_fats;
    double remainingCalories;
    double protein = 0;
    double carbs = 0;
    double fats = 0;
    double foodCalories = 0;
    double exerciseCalories = 0;
    int calsPercentage = 0;
    int proteinPercentage = 0;
    int carbPercentage = 0;
    int fatsPercentage = 0;
    int daily_steps = 0;

    ArrayList<FoodLog> foodList = new ArrayList<FoodLog>();
    ArrayList<FoodLog> todayFoodList = new ArrayList<FoodLog>();
    Calendar calendar = Calendar.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    public static Dashboard newInstance(String phoneNumber) {
        Dashboard fragment = new Dashboard();
        Bundle args = new Bundle();
        args.putString("phone", phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String phoneNumber = getArguments().getString("phone");
        dbrFood = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference(phoneNumber + "_food");
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        TextView hello_tv = view.findViewById(R.id.tv);
        TextView bottomTv = view.findViewById(R.id.tv2);
        TextView totalCalories = view.findViewById(R.id.totalCalories);
        TextView tvRightCarb = view.findViewById(R.id.carb_textview_right);
        TextView tvRightProtein = view.findViewById(R.id.protein_textview_right);
        TextView tvRightFats = view.findViewById(R.id.fats_textview_right);
        TextView remCalsTv = view.findViewById(R.id.cal_progress_bar_text);
        TextView foodCals = view.findViewById(R.id.foodCalories);
        TextView exerciseCals = view.findViewById(R.id.exerciseCalories);
        ProgressBar calsPb = view.findViewById(R.id.progress_bar_calories);
        ProgressBar carbPb = view.findViewById(R.id.carb_horizontal_progressbar);
        ProgressBar proteinPb = view.findViewById(R.id.protein_horizontal_progressbar);
        ProgressBar fatsPb = view.findViewById(R.id.fats_horizontal_progressbar);
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        dbrFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodLog fl = dataSnapshot.getValue(FoodLog.class);
                    foodList.add(fl);
                }
                for (FoodLog foodLog : foodList) {
                    if (foodLog.getYear() == calendar.get(Calendar.YEAR) &&
                            foodLog.getMonth() == (calendar.get(Calendar.MONTH)+1) &&
                            foodLog.getDay() == calendar.get(Calendar.DAY_OF_MONTH)) {
                        todayFoodList.add(foodLog);
                    }
                }
                for(FoodLog fl : todayFoodList){
                    foodCalories += fl.getCalories();
                    protein += fl.getProtein();
                    carbs += fl.getCarbs();
                    fats += fl.getFats();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });


         databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    full_name = snapshot.child(phoneNumber).child("fullname").getValue(String.class);
                    total_calories = snapshot.child(phoneNumber).child("total_calories").getValue(int.class);
                    daily_protein =  snapshot.child(phoneNumber).child("daily_protein").getValue(int.class);
                    daily_carb =  snapshot.child(phoneNumber).child("daily_carb").getValue(int.class);
                    daily_fats =  snapshot.child(phoneNumber).child("daily_fats").getValue(int.class);
                    daily_steps = snapshot.child(phoneNumber).child("daily_steps").getValue(int.class);

                    String[] name_array = full_name.split(" ");
                    String first_name = name_array[0];

                    if(currentHour >=6 && currentHour < 10){
                    hello_tv.setText("Good morning, " + first_name + "!");
                    }
                    else if(currentHour >= 10 && currentHour < 18){
                    hello_tv.setText("Hello, " + first_name + "!");
                    }
                    else{
                    hello_tv.setText("Good evening, " + first_name + "!");
                    }

                    totalCalories.setText(String.valueOf(total_calories));
                    tvRightProtein.setText(protein +  " g / "+ daily_protein+ " g");
                    tvRightCarb.setText(carbs +  " g / "+ daily_carb+ " g");
                    tvRightFats.setText(fats +  " g / "+ daily_fats + " g");

                    remainingCalories = total_calories - foodCalories  + exerciseCalories;
                    remCalsTv.setText(String.valueOf(remainingCalories));
                    foodCals.setText(String.valueOf(foodCalories));
                    exerciseCals.setText(String.valueOf(exerciseCalories));

                    calsPercentage = Long.valueOf(Math.round((foodCalories / total_calories) * 100)).intValue();
                    carbPercentage = Long.valueOf(Math.round((carbs / daily_carb) * 100)).intValue();
                    proteinPercentage = Long.valueOf(Math.round((protein / daily_protein) * 100)).intValue();
                    fatsPercentage = Long.valueOf(Math.round((fats / daily_fats) * 100)).intValue();

                    calsPb.setProgress(calsPercentage);
                    proteinPb.setProgress(proteinPercentage);
                    carbPb.setProgress(carbPercentage);
                    fatsPb.setProgress(fatsPercentage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        if(currentHour >= 6 && currentHour < 10){
            bottomTv.setText("A nice day begins with a good breakfast!");
        }
        else if(currentHour >= 10 && currentHour < 12){
            bottomTv.setText("Don't forget to drink water!");
        }
        else if(currentHour >= 12 && currentHour < 16){
            bottomTv.setText("Eat your lunch!");
        }
        else if(currentHour >= 16 && currentHour < 22){
            bottomTv.setText("We hope you don't each too much on the dinner!");
        }
        else {
            bottomTv.setText("A good sleep is the key to a healthy lifestyle.");
        }

        return view;
    }
}
 /*



 */