package com.example.proiectlicenta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link reg_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class reg_screen extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public reg_screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment reg_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static reg_screen newInstance(String param1, String param2) {
        reg_screen fragment = new reg_screen();
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
        View view = inflater.inflate(R.layout.fragment_reg_screen, container, false);
        TextView dailyCalorieText = view.findViewById(R.id.calorieTextView);
        TextView dailyCarbsText = view.findViewById(R.id.carbsTextView);
        TextView dailyProteinText = view.findViewById(R.id.proteinTextView);
        TextView dailyFatsText = view.findViewById(R.id.fatsTextView);
        Button nextBtn = view.findViewById(R.id.nextBtn);
        Bundle b = getArguments();

        double totalCalories = b.getDouble("cal");
        double dailyCarb = b.getDouble("carb");
        double dailyProtein = b.getDouble("protein");
        double dailyFats = b.getDouble("fats");

        dailyCalorieText.setText(totalCalories + " calories");
        dailyCarbsText.setText(dailyCarb + " grams");
        dailyProteinText.setText(dailyProtein + " grams");
        dailyFatsText.setText(dailyFats + " grams");
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }
}