package com.example.proiectlicenta;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import java.time.LocalDate;
import java.time.Period;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Additional_information#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Additional_information extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Additional_information() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Additional_information.
     */
    // TODO: Rename and change types and number of parameters
    public static Additional_information newInstance(String param1, String param2) {
        Additional_information fragment = new Additional_information();
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
        View view = inflater.inflate(R.layout.fragment_additional_information, container, false);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.genderRadioGroup);
        DatePicker cal = (DatePicker) view.findViewById(R.id.calendar);
        Button nextBtn = (Button) view.findViewById(R.id.nextBtn);
        Bundle b = getArguments();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID = rg.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) rg.findViewById(radioButtonID);
                String selectedText = (String) radioButton.getText();
                b.putString("gender",selectedText);

                int age = Period.between(LocalDate.of(cal.getYear(), cal.getMonth(), cal.getDayOfMonth()),LocalDate.now()).getYears();
                b.putInt("age",age);
                Navigation.findNavController(view).navigate(R.id.action_additional_information_to_additional_information2, b);
            }
        });
        return view;
    }
}