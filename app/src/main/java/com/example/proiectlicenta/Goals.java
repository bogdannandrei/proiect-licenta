package com.example.proiectlicenta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Goals extends Fragment {
    Bundle b = new Bundle();
    RadioGroup radioGroup;
    RadioButton loseWeightBtn, maintainWeightBtn, gainWeightBtn;
    Button nextBtn;

    public Goals() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_goals, container, false);

        radioGroup = rootView.findViewById(R.id.goalsRadioGroup);
        loseWeightBtn = rootView.findViewById(R.id.loseWeightBtn);
        maintainWeightBtn = rootView.findViewById(R.id.maintainWeightBtn);
        gainWeightBtn = rootView.findViewById(R.id.gainWeightBtn);
        nextBtn = rootView.findViewById(R.id.nextBtn);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               int id = radioGroup.getCheckedRadioButtonId();
               RadioButton rb = rootView.findViewById(id);
               String rbText = rb.getText().toString();

               if(rbText.equals("Losing weight")){
                    b.putString("goal","lose weight");
               }
               else if(rbText.equals("Maintaining weight")){
                   b.putString("goal","maintain weight");
               }
               else if(rbText.equals("Gaining weight")){
                   b.putString("goal","gain weight");
               }
               else{
                   Toast.makeText(getActivity(), "Please select your goal!", Toast.LENGTH_SHORT).show();
               }
                Toast.makeText(getActivity(), b.getString("goal"), Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }
}