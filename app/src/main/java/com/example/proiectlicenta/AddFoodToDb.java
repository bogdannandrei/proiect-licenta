package com.example.proiectlicenta;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
public class AddFoodToDb extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("food");
    public static EditText barcode;
    long maxID = 0;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFoodToDb() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddFoodToDb newInstance() {
        AddFoodToDb fragment = new AddFoodToDb();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_add_food_to_db, container, false);
        EditText brandname_et = (EditText) view.findViewById(R.id.brandname_et);
        EditText foodname_et = (EditText) view.findViewById(R.id.foodname_et);
        barcode = (EditText) view.findViewById(R.id.barcode_et);
        EditText calories_et = (EditText) view.findViewById(R.id.calories_et);
        EditText carb_et = (EditText) view.findViewById(R.id.carb_et);
        EditText protein_et = (EditText) view.findViewById(R.id.protein_et);
        EditText fats_et = (EditText) view.findViewById(R.id.fats_et);
        ImageButton barcode_btn = (ImageButton) view.findViewById(R.id.barcode_button);
        Button create_food_btn = (Button) view.findViewById(R.id.create_food_btn);
        Intent scanCode = new Intent(getActivity(), ScanCode_createFood.class);
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
            }
        });
        return view;
    }
}