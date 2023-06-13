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

public class Exercise extends Fragment {

    DatabaseReference databaseReference;
    private ImageView prevDayButton;
    private ImageView nextDayButton;
    private TextView dateTextView;
    private Calendar calendar;
    private TextView exerciseCalories;
    private ExerciseLogAdapter exerciseLogAdapter;
    private RecyclerView exerciseRecyclerView;
    ArrayList<ExerciseLog> exerciseList = new ArrayList<ExerciseLog>();
    ArrayList<ExerciseLog> filteredExerciseList = new ArrayList<ExerciseLog>();
    String phoneNumber;
    int exerciseSum = 0;

    public Exercise() {

    }

    public static Exercise newInstance(String phoneNumber) {
        Exercise fragment = new Exercise();
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
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference(phoneNumber + "_exercise");
        exerciseCalories = view.findViewById(R.id.exercise_calories);
        exerciseRecyclerView = view.findViewById(R.id.breakfast_recycler_view);
        prevDayButton = view.findViewById(R.id.prev_day_button);
        nextDayButton = view.findViewById(R.id.next_day_button);
        dateTextView = view.findViewById(R.id.date_text_view);


        calendar = Calendar.getInstance();
        updateDateText();
        System.out.println(calendar.get(Calendar.YEAR));
        System.out.println(calendar.get(Calendar.MONTH));
        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ExerciseLog el = dataSnapshot.getValue(ExerciseLog.class);
                    exerciseList.add(el);
                }
                updateRecyclerView();
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
        filteredExerciseList.clear();
        exerciseSum = 0;

        filteredExerciseList = filterByDateAndMealType(exerciseList, calendar, "breakfast");
        exerciseLogAdapter = new ExerciseLogAdapter(getContext(), filteredExerciseList, calendar);
        for(ExerciseLog el : filteredExerciseList){
            exerciseSum += el.getCaloriesBurned();
        }
        exerciseCalories.setText(exerciseSum + " kcal");
        exerciseRecyclerView.setAdapter(exerciseLogAdapter);
        exerciseLogAdapter.notifyDataSetChanged();

    }



    private static ArrayList<ExerciseLog> filterByDateAndMealType(ArrayList<ExerciseLog> list, Calendar selectedDate, String mealType) {
        ArrayList<ExerciseLog> filteredList = new ArrayList<>();

        for (ExerciseLog exerciseLog : list) {
            if (exerciseLog.getYear() == selectedDate.get(Calendar.YEAR) &&
                    exerciseLog.getMonth() == (selectedDate.get(Calendar.MONTH)+1) &&
                    exerciseLog.getDay() == selectedDate.get(Calendar.DAY_OF_MONTH)) {
                filteredList.add(exerciseLog);
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
