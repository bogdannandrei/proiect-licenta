package com.example.proiectlicenta;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddFoodToDiary extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference("food");
    AddFoodToDiaryAdapter adapter;
    ArrayList<Food> list;
    TextView addFoodToDB;
    EditText foodFilter;
    ImageButton barcodeBtn;
    public AddFoodToDiary() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_diary);

        barcodeBtn = findViewById(R.id.barcode_button);
        foodFilter = findViewById(R.id.food_name);
        addFoodToDB = findViewById(R.id.addFoodToDB);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new AddFoodToDiaryAdapter(this,list);
        recyclerView.setAdapter(adapter);

        addFoodToDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddFoodToDiary.this,AddFoodToDb.class);
                startActivity(intent);
            }
        });

        foodFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Food food = dataSnapshot.getValue(Food.class);
                    list.add(food);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        barcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent scanCode = new Intent(AddFoodToDiary.this, ScanCode_addFood.class);
                startActivity(scanCode);
            }
        });

        adapter.setOnItemClickListener(new AddFoodToDiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food food) {
                Intent i = new Intent(AddFoodToDiary.this, LogFood.class);
                i.putExtra("Food", food);
                startActivity(i);
            }
        });
    }
}