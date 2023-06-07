package com.example.proiectlicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("exercise");
    private static BottomNavigationView bottomNavigationView;
    private FloatingActionButton fab;
    private FloatingActionButton fabFood;
    private FloatingActionButton fabExercise;
    private View fabButtons;
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
        View overlayView = findViewById(R.id.overlay_view);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fab = findViewById(R.id.fab);
        fabFood = findViewById(R.id.fab_food);
        fabExercise = findViewById(R.id.fab_exercise);
        fabButtons = findViewById(R.id.fab_layout);

        Bundle b = new Bundle();
        phone_number = getIntent().getStringExtra("phone");
        b.putString("phone", phone_number);

        replaceFragment(new Dashboard());

        /* List<ExerciseClass> exerciseList = new ArrayList<>();
        long maxID = 0;
        exerciseList.add(new ExerciseClass("Running (8 mph)", 861));
        exerciseList.add(new ExerciseClass("Cycling (moderate effort)", 508));
        exerciseList.add(new ExerciseClass("Jumping Rope", 861));
        exerciseList.add(new ExerciseClass("Swimming (freestyle, vigorous)", 715));
        exerciseList.add(new ExerciseClass("Rowing (moderate effort)", 584));
        exerciseList.add(new ExerciseClass("Elliptical Training", 651));
        exerciseList.add(new ExerciseClass("High-Intensity Interval Training (HIIT)", 700));
        exerciseList.add(new ExerciseClass("Aerobic Dance", 407));
        exerciseList.add(new ExerciseClass("Boxing (sparring)", 647));
        exerciseList.add(new ExerciseClass("Stair Climbing", 657));
        exerciseList.add(new ExerciseClass("Cross-Country Skiing", 800));
        exerciseList.add(new ExerciseClass("Kickboxing", 585));
        exerciseList.add(new ExerciseClass("Jumping Jacks", 500));
        exerciseList.add(new ExerciseClass("Treadmill (brisk walking)", 314));
        exerciseList.add(new ExerciseClass("Hiking (moderate)", 440));
        exerciseList.add(new ExerciseClass("Zumba", 500));
        exerciseList.add(new ExerciseClass("Basketball (competitive)", 728));
        exerciseList.add(new ExerciseClass("Football (touch or flag)", 573));
        exerciseList.add(new ExerciseClass("Tennis (singles)", 584));
        exerciseList.add(new ExerciseClass("Soccer", 728));
        exerciseList.add(new ExerciseClass("Volleyball (competitive)", 455));
        exerciseList.add(new ExerciseClass("Badminton", 455));
        exerciseList.add(new ExerciseClass("Jumping on Trampoline", 228));
        exerciseList.add(new ExerciseClass("Hula Hooping", 300));
        exerciseList.add(new ExerciseClass("Pilates", 236));
        exerciseList.add(new ExerciseClass("Yoga", 236));
        exerciseList.add(new ExerciseClass("Paddleboarding", 410));
        exerciseList.add(new ExerciseClass("Surfing", 210));
        exerciseList.add(new ExerciseClass("Rock Climbing", 637));
        exerciseList.add(new ExerciseClass("Dancing (high impact)", 455));
        exerciseList.add(new ExerciseClass("Skating (inline or roller)", 410));
        exerciseList.add(new ExerciseClass("Skiing (downhill)", 455));
        exerciseList.add(new ExerciseClass("Snowboarding", 455));
        exerciseList.add(new ExerciseClass("Martial Arts (vigorous)", 728));
        exerciseList.add(new ExerciseClass("Gardening", 292));
        exerciseList.add(new ExerciseClass("Cleaning (heavy effort)", 455));
        exerciseList.add(new ExerciseClass("Carrying Groceries", 273));
        exerciseList.add(new ExerciseClass("Cooking (standing)", 182));
        exerciseList.add(new ExerciseClass("Ironing", 182));
        exerciseList.add(new ExerciseClass("Playing with Kids", 364));
        exerciseList.add(new ExerciseClass("Walking (brisk pace)", 314));
        exerciseList.add(new ExerciseClass("Golfing (carrying clubs)", 314));
        exerciseList.add(new ExerciseClass("Bowling", 182));
        exerciseList.add(new ExerciseClass("Table Tennis", 273));
        exerciseList.add(new ExerciseClass("Frisbee", 273));
        exerciseList.add(new ExerciseClass("Fishing (standing)", 182));
        exerciseList.add(new ExerciseClass("Kayaking", 364));
        exerciseList.add(new ExerciseClass("Canoeing", 364));
        exerciseList.add(new ExerciseClass("Paddleboat", 273));

        fabExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < exerciseList.size(); i++) {
                    ExerciseClass exercise = exerciseList.get(i);
                    String exerciseKey = String.valueOf(i);
                    DatabaseReference exerciseRef = databaseReference.child(exerciseKey);
                    exerciseRef.setValue(exercise);
                }
                Toast.makeText(MainActivity.this, "Exercises added!", Toast.LENGTH_SHORT).show();
            }
        });
        */

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fabButtons.getVisibility() == View.GONE) {
                    fabButtons.setVisibility(View.VISIBLE);
                }
                else{
                    fabButtons.setVisibility(View.GONE);
                }
                if (overlayView.getVisibility() == View.VISIBLE) {
                    overlayView.setVisibility(View.INVISIBLE);
                } else {
                    overlayView.setVisibility(View.VISIBLE);
                }
            }
        });

        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your action here
                Intent intent = new Intent(MainActivity.this,AddFoodToDiary.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        fabExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddFoodToDiary.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });



    }
}