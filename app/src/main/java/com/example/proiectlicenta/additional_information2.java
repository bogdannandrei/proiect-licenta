package com.example.proiectlicenta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.time.LocalDate;
import java.time.Period;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link additional_information2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class additional_information2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public additional_information2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment additional_information2.
     */
    // TODO: Rename and change types and number of parameters
    public static additional_information2 newInstance(String param1, String param2) {
        additional_information2 fragment = new additional_information2();
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
        View view = inflater.inflate(R.layout.fragment_additional_information2, container, false);
        EditText height = (EditText) view.findViewById(R.id.height);
        EditText currentWeight = (EditText) view.findViewById(R.id.currentWeight);
        EditText goalWeight = (EditText) view.findViewById(R.id.goalWeight);
        Button nextBtn = (Button) view.findViewById(R.id.nextBtn);
        Bundle b = getArguments();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.putInt("height", Integer.parseInt(height.getText().toString()));
                b.putInt("currentWeight", Integer.parseInt(currentWeight.getText().toString()));
                b.putInt("goalWeight", Integer.parseInt(goalWeight.getText().toString()));
                Navigation.findNavController(view).navigate(R.id.action_additional_information2_to_final_reg, b);
            }
        });
        return view;
    }
}