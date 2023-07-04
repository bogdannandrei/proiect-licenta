package com.example.proiectlicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminPanel extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference("food");
    Button logOut;
    AdminAdapter adapter;
    ArrayList<Food> list;
    ArrayList<Food> unverifiedList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        logOut = findViewById(R.id.logOut);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                unverifiedList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    list.add(food);
                }
                for (Food f : list) {
                    if (!f.isVerified()) {
                        unverifiedList.add(f);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            });

            list = new ArrayList<>();
            unverifiedList = new ArrayList<>();
            adapter = new AdminAdapter(this, unverifiedList);
            recyclerView.setAdapter(adapter);


            logOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logoutUser();
                }
            });

            adapter.setOnItemClickListener(new AdminAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Food food) {
                Intent i = new Intent(AdminPanel.this, VerifyFood.class);
                i.putExtra("Food", food);
                startActivity(i);
                   // Toast.makeText(AdminPanel.this, "Food clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private void logoutUser() {
            // Șterge informațiile de autentificare din Shared Preferences
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("phone");
            editor.apply();

            // Redirecționează utilizatorul către ecranul de autentificare (LoginActivity)
            Intent intent = new Intent(this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }