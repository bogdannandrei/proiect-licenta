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

public class AddExerciseToDiary extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference("exercise");
    AddExerciseToDiaryAdapter adapter;
    ArrayList<ExerciseClass> list;
    EditText exerciseFilter;
    public AddExerciseToDiary() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise_to_diary);

        exerciseFilter = findViewById(R.id.exercise_name);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String phoneNumber = getIntent().getStringExtra("phone");
        list = new ArrayList<>();
        adapter = new AddExerciseToDiaryAdapter(this,list);
        recyclerView.setAdapter(adapter);


        exerciseFilter.addTextChangedListener(new TextWatcher() {
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
                    ExerciseClass ex = dataSnapshot.getValue(ExerciseClass.class);
                    list.add(ex);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter.setOnItemClickListener(new AddExerciseToDiaryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExerciseClass exercise) {
                Intent i = new Intent(AddExerciseToDiary.this, LogExercise.class);
                i.putExtra("phone", phoneNumber);
                i.putExtra("Exercise", exercise);
                startActivity(i);
            }
        });
    }
}