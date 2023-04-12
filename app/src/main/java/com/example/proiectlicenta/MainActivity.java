package com.example.proiectlicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private static BottomNavigationView bottomNavigationView;
    private FloatingActionButton floatingActionButton;
    private String phone_number;

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle args = new Bundle();
        args.putString("phone", phone_number); // add phone argument
        fragment.setArguments(args); // set fragment arguments
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        floatingActionButton = findViewById(R.id.fab);

        Bundle b = new Bundle();
        phone_number = getIntent().getStringExtra("phone");
        b.putString("phone", phone_number);

        replaceFragment(new Dashboard());


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.dashboard:
                        replaceFragment(Dashboard.newInstance(phone_number));
                        break;
                    case R.id.diary:
                        replaceFragment(Diary.newInstance(phone_number));
                        break;
                    case R.id.exercise:
                        replaceFragment(Exercise.newInstance(phone_number));
                        break;
                    case R.id.settings:
                        replaceFragment(Settings.newInstance(phone_number));
                        break;
                }
                return true;
            }
        });

        // Handle floating action button click
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your action here
                Intent intent = new Intent(MainActivity.this,AddFoodToDiary.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });



    }
}