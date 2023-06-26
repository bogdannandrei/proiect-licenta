package com.example.proiectlicenta;

import static android.content.Context.SENSOR_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Dashboard extends Fragment implements SensorEventListener{
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    DatabaseReference dbrFood;
    DatabaseReference dbrExercise;
    String phoneNumber = "";
    View view;
    TextView hello_tv;
    TextView bottomTv;
    TextView totalCalories;
    TextView tvRightCarb;
    TextView tvRightProtein;
    TextView tvRightFats;
    TextView remCalsTv;
    TextView foodCals;
    TextView exerciseCals;
    ProgressBar calsPb;
    ProgressBar carbPb;
    ProgressBar proteinPb;
    ProgressBar fatsPb;
    TextView exerciseCalories2;
    TextView exerciseTime;
    private static final int PERMISSION_REQUEST_ACTIVITY_RECOGNITION = 1;
    String full_name;
    double total_calories;
    double daily_carb;
    double daily_protein;
    double daily_fats;
    double remainingCalories;
    double protein = 0;
    double carbs = 0;
    double fats = 0;
    double foodCalories = 0;
    double exerciseCalories = 0;
    int exerciseHours = 0;
    int exerciseMinutes = 0;
    int calsPercentage = 0;
    int proteinPercentage = 0;
    int carbPercentage = 0;
    int fatsPercentage = 0;
    int daily_steps = 0;
    float stepCount = 0;
    private Sensor stepSensor;
    private SensorManager sensorManager;
    private TextView stepsTv;
    private TextView stepGoalTv;
    private ProgressBar stepProgressBar;
    int progress = 0;
    Context context;
    String time = "";
    DecimalFormat df = new DecimalFormat("#.##");
    ArrayList<FoodLog> foodList = new ArrayList<FoodLog>();
    ArrayList<FoodLog> todayFoodList = new ArrayList<FoodLog>();

    ArrayList<ExerciseLog> exerciseList = new ArrayList<ExerciseLog>();
    ArrayList<ExerciseLog> todayExerciseList = new ArrayList<ExerciseLog>();
    Calendar calendar = Calendar.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Dashboard() {
        // Required empty public constructor
    }

    private SensorEventListener stepListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    public static Dashboard newInstance(String phoneNumber) {
        Dashboard fragment = new Dashboard();
        Bundle args = new Bundle();
        args.putString("phone", phoneNumber);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_ACTIVITY_RECOGNITION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisiunea a fost acordată, începeți să ascultați pașii
                sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            } else {
                // Permisiunea a fost refuzată, tratați în consecință (afișați un mesaj, dezactivați funcționalitatea etc.)
                Toast.makeText(context, "Nu aveți permisiunea de a accesa pașii", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         phoneNumber = getArguments().getString("phone");
         view = inflater.inflate(R.layout.fragment_dashboard, container, false);
         hello_tv = view.findViewById(R.id.tv);
         bottomTv = view.findViewById(R.id.tv2);
         totalCalories = view.findViewById(R.id.totalCalories);
         tvRightCarb = view.findViewById(R.id.carb_textview_right);
         tvRightProtein = view.findViewById(R.id.protein_textview_right);
         tvRightFats = view.findViewById(R.id.fats_textview_right);
         remCalsTv = view.findViewById(R.id.cal_progress_bar_text);
         foodCals = view.findViewById(R.id.foodCalories);
         exerciseCals = view.findViewById(R.id.exerciseCalories);
         calsPb = view.findViewById(R.id.progress_bar_calories);
         carbPb = view.findViewById(R.id.carb_horizontal_progressbar);
         proteinPb = view.findViewById(R.id.protein_horizontal_progressbar);
         fatsPb = view.findViewById(R.id.fats_horizontal_progressbar);
         exerciseCalories2 = view.findViewById(R.id.exerciseCalories2);
         exerciseTime = view.findViewById(R.id.exerciseTime);
         stepGoalTv = view.findViewById(R.id.stepGoalTv);
        stepsTv = view.findViewById(R.id.stepsTv);
        stepProgressBar = view.findViewById(R.id.stepProgressBar);
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                full_name = snapshot.child(phoneNumber).child("fullname").getValue(String.class);
                total_calories = snapshot.child(phoneNumber).child("total_calories").getValue(int.class);
                daily_protein =  snapshot.child(phoneNumber).child("daily_protein").getValue(int.class);
                daily_carb =  snapshot.child(phoneNumber).child("daily_carb").getValue(int.class);
                daily_fats =  snapshot.child(phoneNumber).child("daily_fats").getValue(int.class);
                daily_steps = snapshot.child(phoneNumber).child("daily_steps").getValue(int.class);

                stepGoalTv.setText("Goal: " + daily_steps);
                System.out.print(daily_steps);

                String[] name_array = full_name.split(" ");
                String first_name = name_array[0];

                if(currentHour >=6 && currentHour < 10){
                    hello_tv.setText("Good morning, " + first_name + "!");
                }
                else if(currentHour >= 10 && currentHour < 18){
                    hello_tv.setText("Hello, " + first_name + "!");
                }
                else{
                    hello_tv.setText("Good evening, " + first_name + "!");
                }

                updateViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        dbrFood = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference(phoneNumber + "_food");
        dbrFood.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodLog fl = dataSnapshot.getValue(FoodLog.class);
                    foodList.add(fl);
                }
                for (FoodLog foodLog : foodList) {
                    if (foodLog.getYear() == calendar.get(Calendar.YEAR) &&
                            foodLog.getMonth() == (calendar.get(Calendar.MONTH)+1) &&
                            foodLog.getDay() == calendar.get(Calendar.DAY_OF_MONTH)) {
                        todayFoodList.add(foodLog);
                    }
                }
                for(FoodLog fl : todayFoodList){
                    foodCalories += fl.getCalories();
                    protein += fl.getProtein();
                    carbs += fl.getCarbs();
                    fats += fl.getFats();
                }
                updateViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });

        dbrExercise = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference(phoneNumber + "_exercise");
        dbrExercise.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ExerciseLog el = dataSnapshot.getValue(ExerciseLog.class);
                    exerciseList.add(el);
                }
                for (ExerciseLog el : exerciseList) {
                    if (el.getYear() == calendar.get(Calendar.YEAR) &&
                            el.getMonth() == (calendar.get(Calendar.MONTH)+1) &&
                            el.getDay() == calendar.get(Calendar.DAY_OF_MONTH)) {
                        todayExerciseList.add(el);
                    }
                }

                for(ExerciseLog el : todayExerciseList){
                    exerciseCalories += el.getCaloriesBurned();
                    time = el.getTime();
                    String[] parts = time.split(":");
                    exerciseHours += Integer.parseInt(parts[0]);
                    exerciseMinutes += Integer.parseInt(parts[1]);
                }

                if (exerciseMinutes >= 60) {
                    exerciseHours += exerciseMinutes / 60;
                    exerciseMinutes %= 60;
                }

                exerciseCalories2.setText(exerciseCalories + " kcal");
                exerciseTime.setText(String.format("%d:%02d", exerciseHours, exerciseMinutes));
                updateViews();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled if needed
            }
        });

        sensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        if (sensorManager != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            if (stepSensor == null) {
                System.out.print("stepSensor e null");
            } else {
                // Verificați permisiunea
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACTIVITY_RECOGNITION)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permisiunea este acordată, începeți să ascultați pașii
                    sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
                } else {
                    // Permisiunea nu este acordată, solicitați permisiunea utilizatorului
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.ACTIVITY_RECOGNITION},
                            PERMISSION_REQUEST_ACTIVITY_RECOGNITION);
                }
            }
        }

        if(currentHour >= 6 && currentHour < 10){
            bottomTv.setText("A nice day begins with a good breakfast!");
        }
        else if(currentHour >= 10 && currentHour < 12){
            bottomTv.setText("Don't forget to drink water!");
        }
        else if(currentHour >= 12 && currentHour < 16){
            bottomTv.setText("Eat your lunch!");
        }
        else if(currentHour >= 16 && currentHour < 22){
            bottomTv.setText("We hope you don't each too much on the dinner!");
        }
        else {
            bottomTv.setText("A good sleep is the key to a healthy lifestyle.");
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Opriți ascultarea pașilor când fragmentul este distrus
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Actualizați TextView-ul cu numărul de pași
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            stepCount = event.values[0];
            stepsTv.setText(String.valueOf(stepCount));
            progress = (int) ((float) stepCount / daily_steps * 100);
            stepProgressBar.setProgress(progress);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void updateViews(){
        totalCalories.setText(String.valueOf(total_calories));
        tvRightProtein.setText(df.format(protein) +  " g / "+ daily_protein+ " g");
        tvRightCarb.setText(df.format(carbs) +  " g / "+ daily_carb+ " g");
        tvRightFats.setText(df.format(fats) +  " g / "+ daily_fats + " g");

        remainingCalories = total_calories - foodCalories  + exerciseCalories;
        remCalsTv.setText(df.format(remainingCalories));
        foodCals.setText(df.format(foodCalories));
        exerciseCals.setText(df.format(exerciseCalories));

        calsPercentage = Long.valueOf(Math.round((foodCalories / total_calories) * 100)).intValue();
        carbPercentage = Long.valueOf(Math.round((carbs / daily_carb) * 100)).intValue();
        proteinPercentage = Long.valueOf(Math.round((protein / daily_protein) * 100)).intValue();
        fatsPercentage = Long.valueOf(Math.round((fats / daily_fats) * 100)).intValue();

        calsPb.setProgress(calsPercentage);
        proteinPb.setProgress(proteinPercentage);
        carbPb.setProgress(carbPercentage);
        fatsPb.setProgress(fatsPercentage);
        progress = (int) ((float) stepCount / daily_steps * 100);
        stepProgressBar.setProgress(progress);
    }
}