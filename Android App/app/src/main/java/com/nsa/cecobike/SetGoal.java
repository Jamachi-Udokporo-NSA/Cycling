package com.nsa.cecobike;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.support.constraint.Constraints.TAG;


public class SetGoal extends Fragment {
    SeekBar seekBar;
    TextView textView;

    public SetGoal() {
        // Required empty public constructor
    }

//    Seekbar set goal in Miles
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_goal, container, false);
        seekBar = (SeekBar) v.findViewById(R.id.seek_bar);
        textView = (TextView) v.findViewById(R.id.goal_miles);
        return v;
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



//                Bike Miles
//        String i = (String) textView.getText();
//        final Double bMiles = Double.valueOf(i);

//                Car miles
//        final Double cMiles = bMiles * (1.0 / 1.609);
        //Goal Constructor to hold the values.
//        final Goal goal = new Goal(bMiles, cMiles);

        //        Seekbar Code Below
        seekBar = seekBar.findViewById(R.id.seek_bar);
        textView = textView.findViewById(R.id.goal_miles);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textView.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
//                goal.setGoal_Miles(bMiles);
//                goal.setAverage_Miles(cMiles);
//                Log.d(TAG, "onStopTrackingTouch: " + bMiles + cMiles);
            }
        });
    }
}
