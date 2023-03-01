package com.example.proiectlicenta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link activity_level#newInstance} factory method to
 * create an instance of this fragment.
 */
public class activity_level extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public activity_level() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment activity.
     */
    // TODO: Rename and change types and number of parameters
    public static activity_level newInstance(String param1, String param2) {
        activity_level fragment = new activity_level();
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
        View view = inflater.inflate(R.layout.fragment_activity_level, container, false);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.activityLevelRadioGroup);
        Button nextBtn = (Button) view.findViewById(R.id.nextBtn);
        Bundle b = new Bundle();

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioButtonID = rg.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) rg.findViewById(radioButtonID);
                String selectedText = (String) radioButton.getText();
                b.putString("goal",selectedText);
                Navigation.findNavController(view).navigate(R.id.action_activity_level_to_additional_information, b);
            }
        });
        return view;
    }
}