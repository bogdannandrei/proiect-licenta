package com.example.proiectlicenta;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Settings extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://proiectlicenta-32b5d-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

    Button logOut;
    Button btnSaveSettings;
    EditText etModifySteps;
    EditText etChangeName;
    Spinner spinnerChangeGender;
    EditText etChangePassword;

    public Settings() {
    }

    public static Settings newInstance(String phoneNumber) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putString("phone_number", phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        String phoneNumber = getArguments().getString("phone");
        String[] genders = {"Male" , "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, genders);
        logOut = view.findViewById(R.id.logOut);
        btnSaveSettings = view.findViewById(R.id.btnSaveSettings);
        etModifySteps = view.findViewById(R.id.etModifySteps);
        etChangeName = view.findViewById(R.id.etChangeName);
        spinnerChangeGender = view.findViewById(R.id.spinnerChangeGender);
        etChangePassword = view.findViewById(R.id.etChangePassword);
        spinnerChangeGender.setAdapter(adapter);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });

        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etModifySteps.getText().length() > 0){
                    databaseReference.child("users").child(phoneNumber).child("daily_steps").setValue(Integer.parseInt(etModifySteps.getText().toString()));
                }

                if(etChangeName.getText().length() > 0){
                    databaseReference.child("users").child(phoneNumber).child("fullname").setValue(etChangeName.getText().toString());
                }

                String selectedGender = spinnerChangeGender.getSelectedItem().toString();
                databaseReference.child("users").child(phoneNumber).child("gender").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String gender = snapshot.getValue(String.class);
                            if(!selectedGender.equals(gender)){
                                databaseReference.child("users").child(phoneNumber).child("gender").setValue(selectedGender);
                            }

                        } else {
                            databaseReference.child("users").child(phoneNumber).child("gender").setValue(selectedGender);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Tratează eroarea în caz de eșec în citirea datelor
                    }
                });

                if(etChangePassword.getText().length() > 0){
                    databaseReference.child("users").child(phoneNumber).child("password").setValue(etChangePassword.getText().toString());
                }
                Toast.makeText(requireContext(), "Settings updated successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // ...

    private void logoutUser() {
        // Șterge informațiile de autentificare din Shared Preferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("phone");
        editor.apply();

        // Redirecționează utilizatorul către ecranul de autentificare (LoginActivity)
        Intent intent = new Intent(requireActivity(), Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        requireActivity().finish();
    }
// ...

}