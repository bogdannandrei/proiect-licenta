package com.example.proiectlicenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;

public class VerifyFood extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("food");
    Button back;
    Button verifyFood;
    Food f;
    TextView caloriesTv;
    TextView carbsTv;
    TextView proteinTv;
    TextView fatsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_food);

        Intent intent = getIntent();

        // Check if the intent is not null
        if (intent != null) {
            // Get the extras from the intent
            Bundle extras = intent.getExtras();

            // Check if the extras are not null
            if (extras != null) {
                // Get the food name from the extras
                f = (Food) extras.getSerializable("Food");
            }
        }

        back = findViewById(R.id.back);
        verifyFood = findViewById(R.id.verifyFood);
        caloriesTv = findViewById(R.id.caloriesTv);
        carbsTv = findViewById(R.id.carbsTv);
        proteinTv = findViewById(R.id.proteinTv);
        fatsTv = findViewById(R.id.fatsTv);

        caloriesTv.setText(String.valueOf(f.getCalories()) + " cal");
        proteinTv.setText(String.valueOf(f.getProtein()) + " g");
        carbsTv.setText(String.valueOf(f.getCarbs()) + " g");
        fatsTv.setText(String.valueOf(f.getFats()) + " g");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        verifyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(String.valueOf(f.getFoodID())).child("verified").setValue(true);
                Toast.makeText(VerifyFood.this, "Food verified!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}