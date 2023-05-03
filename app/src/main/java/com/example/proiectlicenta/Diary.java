package com.example.proiectlicenta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class Diary extends Fragment {

    private ImageView prevDayButton;
    private ImageView nextDayButton;
    private TextView dateTextView;
    private Calendar calendar;

    public Diary() {
        // Required empty public constructor
    }

    public static Diary newInstance(String phoneNumber) {
        Diary fragment = new Diary();
        Bundle args = new Bundle();
        args.putString("phone_number", phoneNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String phoneNumber = getArguments().getString("phone_number");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        // Initialize the views
        prevDayButton = view.findViewById(R.id.prev_day_button);
        nextDayButton = view.findViewById(R.id.next_day_button);
        dateTextView = view.findViewById(R.id.date_text_view);

        // Set up the calendar with the current date
        calendar = Calendar.getInstance();
        updateDateText();

        // Set up the click listeners for the arrow buttons
        prevDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, -1);
                updateDateText();
            }
        });
        nextDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                updateDateText();
            }
        });

        return view;
    }

    private void updateDateText() {
        // Format the current date as a string
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        // Set the date string to the date text view
        dateTextView.setText(dateString);
    }
}
