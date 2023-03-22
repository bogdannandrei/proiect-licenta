package com.example.proiectlicenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class LogFood extends AppCompatActivity {
    TextView foodName;
    TextView brandName;
    EditText numberOfServingsEt;
    TextView caloriesTv;
    TextView carbsTv;
    TextView proteinTv;
    TextView fatsTv;
    Food f;

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
                f = (Food) extras.getSerializable("Food");
            }
        }
        foodName = findViewById(R.id.foodName);
        brandName = findViewById(R.id.brandName);
        numberOfServingsEt = findViewById(R.id.nrOfServingsEt);
        caloriesTv = findViewById(R.id.caloriesTv);
        carbsTv = findViewById(R.id.carbsTv);
        proteinTv = findViewById(R.id.proteinTv);
        fatsTv = findViewById(R.id.fatsTv);

        foodName.setText(f.getFoodName().toString());
        brandName.setText(f.getBrandName().toString());

        double servingSize = 1;


        caloriesTv.setText(String.valueOf(f.getCalories()) + " cal");
        carbsTv.setText(String.valueOf(f.getCarbs()) + " g");
        proteinTv.setText(String.valueOf(f.getProtein()) + " g");
        fatsTv.setText(String.valueOf(f.getFats()) + " g");

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
                    double nrOfServings = Double.parseDouble(charSequence.toString());
                    caloriesTv.setText(String.valueOf(f.getCalories() * servingSize * nrOfServings) + " cal");
                    carbsTv.setText(String.valueOf(f.getCarbs() * servingSize * nrOfServings) + " g");
                    proteinTv.setText(String.valueOf(f.getProtein() * servingSize * nrOfServings) + " g");
                    fatsTv.setText(String.valueOf(f.getFats() * servingSize * nrOfServings ) + " g");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}