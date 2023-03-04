package com.example.proiectlicenta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link final_reg#newInstance} factory method to
 * create an instance of this fragment.
 */
public class final_reg extends Fragment {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public final_reg() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment final_reg.
     */
    // TODO: Rename and change types and number of parameters
    public static final_reg newInstance(String param1, String param2) {
        final_reg fragment = new final_reg();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_final_reg, container, false);
        final EditText fullname = view.findViewById(R.id.fullname);
        final EditText email = view.findViewById(R.id.email);
        final EditText phone = view.findViewById(R.id.phone);
        final EditText password = view.findViewById(R.id.password);
        final EditText conPassword = view.findViewById(R.id.conpassword);
        final Button nextBtn = view.findViewById(R.id.nextBtn);
        Bundle b = getArguments();
        final String goals = b.getString("goals");
        final String activityLevel = b.getString("activity_level");
        final String gender = b.getString("gender");
        final int age = b.getInt("age");
        final int height = b.getInt("height");
        final int currentWeight = b.getInt("currentWeight");
        final int goalWeight = b.getInt("goalWeight");
        double BMR = 0.0;
        double cal = 0.0;

        switch(gender){
            case "Male":
                BMR = (5 + (10 * currentWeight) + (6.25 * height) - (5 * age));
                break;
            case "Female":
                BMR = ((10 * currentWeight) + (6.25 * height) - (5 * age) - 161);
                break;
        }
        System.out.println("Passed gender= "+gender+", BMR="+BMR+" cal="+cal);

        cal=BMR;
        switch(activityLevel){
            case "BMR (Basal Metabolic Rate)":
                break;
            case "Sedentary (little to no exercise)":
                cal *= 1.4;
                break;
            case "Light (exercise 1-3 times/week)":
                cal *= 1.6;
                break;
            case "Moderate (exercise 4-5 times/week)":
                cal *= 1.8;
                break;
            case "Very active (daily exercise)":
                cal *= 2;
                break;
        }
        System.out.println("Passed actlvl="+activityLevel+", BMR="+BMR+" cal="+cal);

        switch(goals){
            case "Losing weight (0.5 kg/week)":
                cal -= 250;
                break;
            case "Mildly losing weight (0.25 kg/week)":
                cal -=125;
                break;
            case "Maintaining weight":
                break;
            case "Mildly gaining weight (0.25 kg/week)":
                cal+=125;
                break;
            case "Gaining weight (0.5 kg/week)":
                cal += 250;
                break;
        }
        System.out.println("Passed goals="+goals+", BMR="+BMR+" cal="+cal);

        double totalCalories = (Math.round(cal + 0.5));
        Random r = new Random();
        double dailyProtein = Math.round(currentWeight * (2+(2.2-2)*(r.nextDouble())));
        double dailyFats = Math.round(70 + (r.nextDouble() * (100 - 70)));
        double remCals = totalCalories - (dailyProtein * 4) - (dailyFats * 9);
        double dailyCarb = remCals / 4;
        System.out.println("Total calories:" +totalCalories+"\nCarb="+dailyCarb+"\nProtein="+dailyProtein+"\nFats="+dailyFats);

        b.putDouble("cal",totalCalories);
        b.putDouble("carb",dailyCarb);
        b.putDouble("protein",dailyProtein);
        b.putDouble("fats",dailyFats);
        System.out.println("Macros added");

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fullnameTxt = fullname.getText().toString();
                final String emailTxt = email.getText().toString();
                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                if (fullnameTxt.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                } else if (!passwordTxt.equals(conPasswordTxt)) {
                    Toast.makeText(getActivity(), "Passwords are not matching!", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(phoneTxt)) {
                                Toast.makeText(getActivity(), "Phone already registered!", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReference.child("users").child(phoneTxt).child("fullname").setValue(fullnameTxt);
                                databaseReference.child("users").child(phoneTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(phoneTxt).child("password").setValue(passwordTxt);
                                databaseReference.child("users").child(phoneTxt).child("activity_goal").setValue(activityLevel);
                                databaseReference.child("users").child(phoneTxt).child("gender").setValue(gender);
                                databaseReference.child("users").child(phoneTxt).child("age").setValue(age);
                                databaseReference.child("users").child(phoneTxt).child("goal").setValue(goals);
                                databaseReference.child("users").child(phoneTxt).child("height").setValue(height);
                                databaseReference.child("users").child(phoneTxt).child("current_weight").setValue(currentWeight);
                                databaseReference.child("users").child(phoneTxt).child("goal_weight").setValue(goalWeight);
                                databaseReference.child("users").child(phoneTxt).child("total_calories").setValue(totalCalories);
                                databaseReference.child("users").child(phoneTxt).child("daily_carb").setValue(dailyCarb);
                                databaseReference.child("users").child(phoneTxt).child("daily_protein").setValue(dailyProtein);
                                databaseReference.child("users").child(phoneTxt).child("daily_fats").setValue(dailyFats);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Navigation.findNavController(view).navigate(R.id.action_final_reg_to_reg_screen, b);
                }
            }
        });
        return view;
    }
}
