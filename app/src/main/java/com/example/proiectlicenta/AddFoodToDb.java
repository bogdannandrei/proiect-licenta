package com.example.proiectlicenta;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFoodToDb#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFoodToDb extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("food");
    public static EditText barcode;
    long maxID = 0;

    public AddFoodToDb() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_db);
        EditText brandname_et = (EditText) findViewById(R.id.brandname_et);
        EditText foodname_et = (EditText) findViewById(R.id.foodname_et);
        barcode = (EditText) findViewById(R.id.barcode_et);
        EditText calories_et = (EditText) findViewById(R.id.calories_et);
        EditText carb_et = (EditText) findViewById(R.id.carb_et);
        EditText protein_et = (EditText) findViewById(R.id.protein_et);
        EditText fats_et = (EditText) findViewById(R.id.fats_et);
        ImageButton barcode_btn = (ImageButton) findViewById(R.id.barcode_button);
        Button create_food_btn = (Button) findViewById(R.id.create_food_btn);
        Intent scanCode = new Intent(AddFoodToDb.this, ScanCode_createFood.class);
        Food food = new Food();
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


        barcode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(scanCode);
            }
        });
        create_food_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String brandname_text = brandname_et.getText().toString().trim();
                String foodname_text = foodname_et.getText().toString().trim();
                String barcode_text = barcode.getText().toString().trim();
                double calories = Integer.parseInt(calories_et.getText().toString().trim());
                double carbs = Double.parseDouble(carb_et.getText().toString().trim());
                double protein = Double.parseDouble(protein_et.getText().toString().trim());
                double fats = Double.parseDouble(fats_et.getText().toString().trim());

                food.setBrandName(brandname_text);
                food.setFoodName(foodname_text);
                food.setBarcode(barcode_text);
                food.setCalories(calories);
                food.setCarbs(carbs);
                food.setProtein(protein);
                food.setFats(fats);
                databaseReference.child(String.valueOf(maxID+1)).setValue(food);
                Toast.makeText(AddFoodToDb.this, "Food added to database succesfully.", Toast.LENGTH_SHORT).show();

            }
        });
    }
}