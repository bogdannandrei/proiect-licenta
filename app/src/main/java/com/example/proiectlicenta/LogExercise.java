package com.example.proiectlicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class LogExercise extends AppCompatActivity {
    DatabaseReference databaseReference;
    Button logExerciseBtn;
    TextView exerciseName;
    TextView totalCalories;
    TextView calsBurned;
    EditText time;
    ExerciseClass e;
    ExerciseLog el = new ExerciseLog();
    String phoneNumber;
    long maxID = 0;
    double nrOfServings;
    double caloriesBurned;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_exercise);

        Intent intent = getIntent();

        // Check if the intent is not null
        if (intent != null) {
            // Get the extras from the intent
            Bundle extras = intent.getExtras();

            // Check if the extras are not null
            if (extras != null) {
                // Get the food name from the extras
                phoneNumber = extras.getString("phone");
                e = (ExerciseClass) extras.getSerializable("Exercise");
            }
        }

        logExerciseBtn = findViewById(R.id.logExercise);
        exerciseName = findViewById(R.id.exerciseName);
        calsBurned = findViewById(R.id.calsBurned);
        totalCalories = findViewById(R.id.totalCalories);
        totalCalories.setText(String.valueOf(e.getCaloriesPerHour()));
        time = findViewById(R.id.time);

        exerciseName.setText(e.getName().toString());

        double servingSize = 1;

        calsBurned.setText(String.valueOf(e.getCaloriesPerHour()));

        String tableName = phoneNumber + "_exercise";
        databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference().child(tableName);

        time.addTextChangedListener(new TextWatcher() {
            private boolean isEditing;
            private boolean shouldDeleteAll;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEditing) {
                    isEditing = false;
                    return;
                }

                String input = s.toString();

                if (input.length() == 1) {
                    // Automatically add ":" after the hour digit
                    isEditing = true;
                    s.append(":");
                    return;
                }

                String[] parts = input.split(":");

                if (parts.length > 2) {
                    // Handle invalid duration input here (e.g., display an error message)
                    return;
                }

                if (parts.length == 2) {
                    String hours = parts[0];
                    String minutes = parts[1];

                    if (hours.length() == 1 && minutes.length() > 2) {
                        // Ensure the minutes portion doesn't exceed two digits
                        isEditing = true;
                        s.replace(s.length() - 1, s.length(), "");
                        return;
                    }
                }

                if (shouldDeleteAll) {
                    // Handle case where the user deletes all characters after the colon
                    isEditing = true;
                    shouldDeleteAll = false;
                    s.clear();
                    return;
                }

                if (parts.length == 2 && parts[1].isEmpty()) {
                    // Set flag to delete all characters if the user presses delete after the colon
                    shouldDeleteAll = true;
                }

                double caloriesBurned = calculateCaloriesBurned(input);
                totalCalories.setText(String.valueOf(df.format(caloriesBurned)));
            }
        });




        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                    maxID=(snapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        logExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate date = LocalDate.now();
                el.setExerciseName(e.getName());
                el.setTime(time.getText().toString());
                el.setCaloriesBurned(Double.parseDouble(totalCalories.getText().toString()));
                el.setYear(date.getYear());
                el.setMonth(date.getMonthValue());
                el.setDay(date.getDayOfMonth());
                databaseReference.child(String.valueOf(maxID+1)).setValue(el);
                Toast.makeText(LogExercise.this, "Exercise added to diary succesfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private double calculateCaloriesBurned(String durationInput) {
        String[] parts = durationInput.split(":");
        if (parts.length != 2) {
            // Handle invalid duration input here (e.g., display an error message)
            return 0.0;
        }

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int totalMinutes = hours * 60 + minutes;
            double caloriesBurned = (totalMinutes / 60.0) * e.getCaloriesPerHour();
            return caloriesBurned;
        } catch (NumberFormatException e) {
            // Handle parsing error here (e.g., display an error message)
            return 0.0;
        }
    }

    private boolean handleDeleteKey(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
            time.setText("");
            return true;
        }
        return false;
    }

}