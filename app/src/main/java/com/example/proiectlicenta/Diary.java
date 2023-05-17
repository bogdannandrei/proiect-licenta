package com.example.proiectlicenta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Diary extends Fragment {

    DatabaseReference databaseReference;
    private ImageView prevDayButton;
    private ImageView nextDayButton;
    private TextView dateTextView;
    private Calendar calendar;
    private FoodLogAdapter breakfastAdapter;
    private RecyclerView breakfastRecyclerView;
    private RecyclerView lunchRecyclerView;
    private RecyclerView dinnerRecyclerView;
    private RecyclerView snacksRecyclerView;

    ArrayList<FoodLog> breakfastList = new ArrayList<FoodLog>();
    ArrayList<FoodLog> filteredList = new ArrayList<FoodLog>();
    String phoneNumber;


    public Diary() {

    }

    public static Diary newInstance(String phoneNumber) {
        Diary fragment = new Diary();
        Bundle args = new Bundle();
        args.putString("phone_number", phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getString("phone");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference(phoneNumber + "_food");
        breakfastRecyclerView = view.findViewById(R.id.breakfast_recycler_view);
        lunchRecyclerView = view.findViewById(R.id.lunch_recycler_view);
        dinnerRecyclerView = view.findViewById(R.id.dinner_recycler_view);
        snacksRecyclerView = view.findViewById(R.id.snacks_recycler_view);
        prevDayButton = view.findViewById(R.id.prev_day_button);
        nextDayButton = view.findViewById(R.id.next_day_button);
        dateTextView = view.findViewById(R.id.date_text_view);

        calendar = Calendar.getInstance();
        updateDateText();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

        breakfastAdapter = new FoodLogAdapter(getContext(), filteredList, calendar);
        breakfastRecyclerView.setAdapter(breakfastAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        breakfastRecyclerView.setLayoutManager(layoutManager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodLog fl = dataSnapshot.getValue(FoodLog.class);
                    breakfastList.add(fl);
                }
                filteredList = filterByDateAndMealType(breakfastList, calendar, "breakfast");
                breakfastAdapter = new FoodLogAdapter(getContext(), filteredList, calendar);
                breakfastRecyclerView.setAdapter(breakfastAdapter);
                breakfastAdapter.notifyDataSetChanged();
                for(FoodLog fl : breakfastList){
                    System.out.println(fl.toString());
                }
                System.out.println("FL");
                for(FoodLog fl : filteredList){
                    System.out.println(fl.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });

        prevDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                updateDateText();
                updateRecyclerView();
            }
        });
        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                updateDateText();
                updateRecyclerView();
            }
        });

        return view;
    }

    private void updateRecyclerView() {
        filteredList.clear();
        filteredList = filterByDateAndMealType(breakfastList, calendar, "breakfast");
        breakfastAdapter = new FoodLogAdapter(getContext(), filteredList, calendar);
        breakfastRecyclerView.setAdapter(breakfastAdapter);
        breakfastAdapter.notifyDataSetChanged();
    }



    private static ArrayList<FoodLog> filterByDateAndMealType(ArrayList<FoodLog> list, Calendar selectedDate, String mealType) {
        ArrayList<FoodLog> filteredList = new ArrayList<>();

        for (FoodLog foodLog : list) {
            if (foodLog.getYear() == selectedDate.get(Calendar.YEAR) &&
                    foodLog.getMonth() == (selectedDate.get(Calendar.MONTH)+1) &&
                    foodLog.getDay() == selectedDate.get(Calendar.DAY_OF_MONTH) &&
                    foodLog.getMealType().equalsIgnoreCase(mealType)) {
                filteredList.add(foodLog);
            }
        }

        return filteredList;
    }

    private void updateDateText() {
        // Format the current date as a string
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        // Set the date string to the date text view
        dateTextView.setText(dateString);
    }
}
