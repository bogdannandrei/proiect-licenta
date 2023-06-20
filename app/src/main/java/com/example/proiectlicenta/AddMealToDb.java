package com.example.proiectlicenta;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

public class AddMealToDb extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference("food");
    private EditText mealName;
    private Spinner spinnerFoods;
    private EditText editTextGrams;
    private LinearLayout containerAdditionalFood;
    private ArrayList<Food> foodList;
    private ArrayAdapter<Food> spinnerAdapter;
    private ArrayList<Spinner> additionalSpinners = new ArrayList<>();
    EditText editTextAdditionalGrams;
    TextView caloriesView;
    TextView carbsView;
    TextView proteinView;
    TextView fatsView;
    Button saveMeal;
    Food food = new Food();
    double mealCalories = 0;
    double mealCarbs = 0;
    double mealProtein = 0;
    double  mealFats = 0;
    DecimalFormat df;
    long maxID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal_to_db);

        mealName = findViewById(R.id.editTextMealName);
        spinnerFoods = findViewById(R.id.spinnerFood);
        editTextGrams = findViewById(R.id.editTextGrams);
        containerAdditionalFood = findViewById(R.id.containerAdditionalFood);

        caloriesView = findViewById(R.id.textViewCaloriesValue);
        carbsView = findViewById(R.id.textViewCarbsValue);
        proteinView = findViewById(R.id.textViewProteinValue);
        fatsView = findViewById(R.id.textViewFatsValue);
        saveMeal = findViewById(R.id.buttonSaveMeal);

        foodList = new ArrayList<>();
        df = new DecimalFormat("#.##");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodList.clear(); // Clear previous data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    if (food != null) {
                        foodList.add(food);
                    }
                }
                spinnerAdapter.notifyDataSetChanged(); // Notify adapter of data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFoods.setAdapter(spinnerAdapter);
        additionalSpinners.add(spinnerFoods);

        editTextGrams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Apelati metoda pentru a actualiza valorile
                updateMealValues();
            }

            @Override
            public void afterTextChanged(Editable s) {
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

        Button buttonAddFood = findViewById(R.id.buttonAddFood);
        buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFoodSelection();
            }
        });

        saveMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodname_text = mealName.getText().toString();
                if(foodname_text.isEmpty()){
                    Toast.makeText(AddMealToDb.this, "Meal name must not be empty!", Toast.LENGTH_SHORT).show();
                }
                food.setFoodName(foodname_text);
                food.setBarcode(null);
                food.setVerified(false);
                food.setCalories(Double.parseDouble(df.format(mealCalories).replace(",",".")));
                food.setCarbs(Double.parseDouble(df.format(mealCarbs).replace(",",".")));
                food.setProtein(Double.parseDouble(df.format(mealProtein).replace(",",".")));
                food.setFats(Double.parseDouble(df.format(mealFats).replace(",",".")));

                /* System.out.println(Double.parseDouble(df.format(mealCalories).replace(",",".")));
                System.out.println(Double.parseDouble(df.format(mealCarbs).replace(",",".")));
                System.out.println(Double.parseDouble(df.format(mealProtein).replace(",",".")));
                System.out.println(Double.parseDouble(df.format(mealFats).replace(",","."))); */

                databaseReference.child(String.valueOf(maxID+1)).setValue(food);
                Toast.makeText(AddMealToDb.this, "Meal added to database succesfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void addFoodSelection() {
        // Inflate food selection layout
        LayoutInflater inflater = LayoutInflater.from(this);
        View foodSelectionView = inflater.inflate(R.layout.food_selection_item, null);

        Spinner spinnerAdditionalFood = foodSelectionView.findViewById(R.id.spinnerAdditionalFood);
        editTextAdditionalGrams = foodSelectionView.findViewById(R.id.editTextAdditionalGrams);
        ImageButton buttonRemoveFood = foodSelectionView.findViewById(R.id.buttonRemoveFood);

        // Set adapter for additional spinner
        ArrayAdapter<Food> additionalSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, foodList);
        additionalSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdditionalFood.setAdapter(additionalSpinnerAdapter);
        additionalSpinners.add(spinnerAdditionalFood);

        // Find the index of the default food selection view
        int defaultFoodIndex = containerAdditionalFood.getChildCount() - 2;

        // Add food selection view to container after the default food selection view
        containerAdditionalFood.addView(foodSelectionView, defaultFoodIndex + 1);
        editTextAdditionalGrams.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Apelati metoda pentru a actualiza valorile
                updateMealValues();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



        // Remove food selection on button click
        buttonRemoveFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                containerAdditionalFood.removeView(foodSelectionView);
                additionalSpinners.remove(spinnerAdditionalFood);
                updateMealValues();
            }
        });
    }

    private void updateMealValues() {
        double grams = 0.0;
        String gramsText = editTextGrams.getText().toString();
        if (!TextUtils.isEmpty(gramsText)) {
            try {
                grams = Double.parseDouble(gramsText);
            } catch (NumberFormatException e) {
                grams = 0.0;
                e.printStackTrace();
            }
        }

        double totalGrams = grams; // Totalul gramajului introdus de utilizator
        double totalCalories = 0.0;
        double totalCarbs = 0.0;
        double totalProtein = 0.0;
        double totalFats = 0.0;

        // Calculați valorile pentru gramajul principal
        if (!Double.isNaN(grams)) {
            Food selectedFood = (Food) spinnerFoods.getSelectedItem();
            if (selectedFood != null) {
                double foodCalories = selectedFood.getCalories();
                double foodCarbs = selectedFood.getCarbs();
                double foodProtein = selectedFood.getProtein();
                double foodFats = selectedFood.getFats();

                totalCalories += (grams / 100) * foodCalories;
                totalCarbs += (grams / 100) * foodCarbs;
                totalProtein += (grams / 100) * foodProtein;
                totalFats += (grams / 100) * foodFats;
            }
        }

        // Calculați valorile pentru gramajele suplimentare
        for (Spinner spinner : additionalSpinners) {
            View spinnerView = (View) spinner.getParent();
            EditText editTextAdditionalGrams = spinnerView.findViewById(R.id.editTextAdditionalGrams);
            if (editTextAdditionalGrams != null) {
                String additionalGramsText = editTextAdditionalGrams.getText().toString();
                if (!TextUtils.isEmpty(additionalGramsText)) {
                    try {
                        double additionalGrams = Double.parseDouble(additionalGramsText);
                        if (!Double.isNaN(additionalGrams)) {
                            Food additionalFood = (Food) spinner.getSelectedItem();
                            if (additionalFood != null) {
                                double additionalFoodCalories = additionalFood.getCalories();
                                double additionalFoodCarbs = additionalFood.getCarbs();
                                double additionalFoodProtein = additionalFood.getProtein();
                                double additionalFoodFats = additionalFood.getFats();

                                totalGrams += additionalGrams;
                                totalCalories += (additionalGrams / 100) * additionalFoodCalories;
                                totalCarbs += (additionalGrams / 100) * additionalFoodCarbs;
                                totalProtein += (additionalGrams / 100) * additionalFoodProtein;
                                totalFats += (additionalGrams / 100) * additionalFoodFats;
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Calculați valorile nutriționale finale, realizând media ponderată
        mealCalories = totalCalories / totalGrams * 100;
        mealCarbs = totalCarbs / totalGrams * 100;
        mealProtein = totalProtein / totalGrams * 100;
        mealFats = totalFats / totalGrams * 100;

        caloriesView.setText(df.format(mealCalories));
        carbsView.setText(df.format(mealCarbs));
        proteinView.setText(df.format(mealProtein));
        fatsView.setText(df.format(mealFats));
    }




}
