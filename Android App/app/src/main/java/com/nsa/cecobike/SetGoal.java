package com.nsa.cecobike;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static android.support.constraint.Constraints.TAG;


public class SetGoal extends Fragment {
    SeekBar seekBar;
    TextView textView;
    Button  setGoalButton;
    Button  removeGoalButton;
    private Double goal_Miles =0.0;
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
        seekBar = v.findViewById(R.id.seek_bar);
        textView = v.findViewById(R.id.goal_miles);
        setGoalButton = (Button) v.findViewById(R.id.set_goal);
        removeGoalButton = (Button) v.findViewById(R.id.remove_goal);

//                Bike Miles
        final String i = "0";
        textView.setText(i);
        goal_Miles = Double.valueOf(textView.getText().toString());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                goal_Miles = Double.valueOf(progress);
                textView.setText(progress + "");
                Log.d(TAG, "onProgressChanged: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: " + goal_Miles);
            }
        });

        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        final GoalDatabase db = Room.databaseBuilder(getContext(), GoalDatabase.class, "MyGoalDatabase").build();
                        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
                        final LocalDateTime now = LocalDateTime.now();

//                        Gets the set goals from the database
                        final List<Goal> goals = db.goalDao().getAllGoals();
                        Log.d("current number of goals", String.valueOf(goals.size()));

//                        goes through each goal
                        for (int j = 0; j < goals.size(); j++) {
//                            adds date of milestone goal
                            List<String> milestone_dates = Arrays.asList(goals.get(j).getMilestone_date().split("/"));
                            Log.d("Milestone Dates", String.valueOf(milestone_dates.size()));

//                            gets the current month
                            final String currentMonth = goals.get(j).getMilestone_date();
                            Log.d("Current Month", currentMonth);
                            final String currentDateAndTime = String.valueOf(dtf.format(now));

//                          Run check to see if set goal already exists with the same Milestone Date comparison
                            if (!milestone_dates.get(j).equals(currentMonth)) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                            Creates new Goal in database
                                        db.goalDao().insertGoals(
                                                new Goal(goal_Miles, String.valueOf(currentMonth))
                                        );
                                        Log.d("Creating new milestone", String.valueOf(goals.size()));
                                    }
                                });
                                db.close();

                            } else {

                                final int finalJ = j;
                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Log.d("Existing Milestone", goals.get(finalJ).getMilestone_date());
                                        Log.d("Milestone Value", String.valueOf(goals.get(finalJ).getGoal_miles()));
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });

        removeGoalButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final GoalDatabase db = Room.databaseBuilder(getContext(), GoalDatabase.class, "MyGoalDatabase").build();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        final List<Goal> goals = db.goalDao().getAllGoals();
                        db.goalDao().clearGoals();
                        Log.d("Goals have been removed", String.valueOf(goals.size()));
                        db.close();
                    }
                });
            }
        });

        return v;
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
