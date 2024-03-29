package com.example.proiectlicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
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

public class LogFood extends AppCompatActivity {
    DatabaseReference databaseReference;
    Button logFoodBtn;
    TextView foodName;
    TextView brandName;
    EditText numberOfServingsEt;
    TextView caloriesTv;
    TextView carbsTv;
    TextView proteinTv;
    TextView fatsTv;
    Spinner mealTypeSpinner;
    Food f;
    FoodLog fl = new FoodLog();
    String phoneNumber;
    long maxID = 0;
    double nrOfServings;
    String selectedSpinnerValue;
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_food);

        Intent intent = getIntent();

        // Check if the intent is not null
        if (intent != null) {
            // Get the extras from the intent
            Bundle extras = intent.getExtras();

            // Check if the extras are not null
            if (extras != null) {
                // Get the food name from the extras
                phoneNumber = extras.getString("phone");
                f = (Food) extras.getSerializable("Food");
            }
        }

        String[] mealType = {"Breakfast", "Lunch", "Dinner", "Snacks"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mealType);
        logFoodBtn = findViewById(R.id.logFood);
        foodName = findViewById(R.id.foodName);
        brandName = findViewById(R.id.brandName);
        numberOfServingsEt = findViewById(R.id.nrOfServingsEt);
        caloriesTv = findViewById(R.id.caloriesTv);
        carbsTv = findViewById(R.id.carbsTv);
        proteinTv = findViewById(R.id.proteinTv);
        fatsTv = findViewById(R.id.fatsTv);
        mealTypeSpinner = findViewById(R.id.mealTypeSpinner);
        mealTypeSpinner.setAdapter(adapter);

        foodName.setText(f.getFoodName().toString());
        if(f.getBrandName() == null) {
            brandName.setText("");
        } else {
            brandName.setText(f.getBrandName().toString());
        }

        double servingSize = 1;

        caloriesTv.setText(String.valueOf(f.getCalories()) + " cal");
        carbsTv.setText(String.valueOf(f.getCarbs()) + " g");
        proteinTv.setText(String.valueOf(f.getProtein()) + " g");
        fatsTv.setText(String.valueOf(f.getFats()) + " g");

        String tableName = phoneNumber + "_food";
        databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference().child(tableName);
        numberOfServingsEt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    // Anulăm evenimentul de apăsare a tastei Enter
                    return true;
                }
                return false;
            }
        });
        numberOfServingsEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().matches("")){
                    caloriesTv.setText("0 cal");
                    carbsTv.setText("0 g");
                    proteinTv.setText("0 g");
                    fatsTv.setText("0 g");
                }else{
                    nrOfServings = Double.parseDouble(charSequence.toString());
                    caloriesTv.setText(String.valueOf(df.format(f.getCalories() * servingSize * nrOfServings)) + " cal");
                    carbsTv.setText(String.valueOf(df.format(f.getCarbs() * servingSize * nrOfServings)) + " g");
                    proteinTv.setText(String.valueOf(df.format(f.getProtein() * servingSize * nrOfServings)) + " g");
                    fatsTv.setText(String.valueOf(df.format(f.getFats() * servingSize * nrOfServings)) + " g");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mealTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSpinnerValue = adapterView.getItemAtPosition(i).toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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



        logFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nrOfServings = Double.parseDouble(numberOfServingsEt.getText().toString());
                LocalDate date = LocalDate.now();
                fl.setFood(f);
                fl.setFoodLogID((int)maxID+1);
                fl.setNumberOfServings(nrOfServings);
                fl.setYear(date.getYear());
                fl.setMonth(date.getMonthValue());
                fl.setDay(date.getDayOfMonth());
                fl.setMealType(selectedSpinnerValue);
                fl.setCalories(Double.parseDouble(df.format(f.getCalories() * servingSize * nrOfServings).replace(",",".")));
                fl.setCarbs(Double.parseDouble(df.format(f.getCarbs() * servingSize * nrOfServings).replace(",",".")));
                fl.setProtein(Double.parseDouble(df.format(f.getProtein() * servingSize * nrOfServings).replace(",",".")));
                fl.setFats(Double.parseDouble(df.format(f.getFats() * servingSize * nrOfServings).replace(",",".")));
                fl.setServingSize("100 gram");

                databaseReference.child(String.valueOf(maxID+1)).setValue(fl);
                Toast.makeText(LogFood.this, "Food added to diary succesfully.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}