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
    private Double goal_Miles = 0.0;
    public SetGoal() {
        // Required empty public constructor
    }

    //Seekbar set goal in Miles
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set_goal, container, false);

        //Getting IDs
        seekBar = v.findViewById(R.id.seek_bar);
        textView = v.findViewById(R.id.goal_miles);
        setGoalButton = (Button) v.findViewById(R.id.set_goal);
        removeGoalButton = (Button) v.findViewById(R.id.remove_goal);

        //Set Default Miles
        final String i = "0";
        textView.setText(i);
        goal_Miles = Double.valueOf(textView.getText().toString());


        //Distance Slider
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                goal_Miles = (double) progress;
                textView.setText(progress + "");
                Log.d(TAG, "onProgressChanged: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Logs the set value, Goal should match this
                Log.d(TAG, "onStopTrackingTouch: " + goal_Miles);
            }
        });
//        Insert Button Task
        setGoalButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final GoalDatabase db = Room.databaseBuilder(v.getContext(), GoalDatabase.class, "MyGoalDatabase").build();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Goal> goals = db.goalDao().getAllGoals();

                        //Init DTF formatter for date
                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy");
                        LocalDateTime now = LocalDateTime.now();
                        String dateNow = String.valueOf(dtf.format(now));

                        Log.d(TAG, "You pressed this button but the code did not run as its always false");
                        Log.d("Size of goals",String.valueOf(goals.size()));

                        if (goals.size() == 0) {
                            Goal newGoal = new Goal(goal_Miles, String.valueOf(dateNow));
                            goals.add(newGoal);
                            db.goalDao().insertGoals(newGoal);
                            Log.i("New GOAL", newGoal.getGoal_miles().toString() + " - " + newGoal.getMilestone_date());

                            //else if size is not 0
                        }

                        for (Goal goal : goals) {
                            //while count is less than the size of the list will check to see if
                                for (int j = 0; j < goals.size(); j++) {
                                    String currentMonth = goals.get(j).getMilestone_date();
                                    List<String> milestone_dates = Arrays.asList(currentMonth.split("/"));

                                    //Gets the month
                                    Log.d("date", milestone_dates.get(0));
                                    Log.d("Milestone Dates", String.valueOf(milestone_dates.size()));

                                    //Checks the date of milestone dates list
                                    if (!milestone_dates.get(0).equals(currentMonth)) {
                                        Goal newGoal = new Goal(goal_Miles, String.valueOf(dateNow));
                                        db.goalDao().insertGoals(newGoal);
                                        Log.i("New GOAL 2", newGoal.getGoal_miles().toString() + " - " + newGoal.getMilestone_date());
                                    }
                                    else {

                                        Log.d("Existing Milestone", goals.get(j).getMilestone_date());
                                        Log.d("Milestone Value", String.valueOf(goals.get(j).getGoal_miles()));

                                    }
                                }

                                Log.i("Update GOAL", goal.getGoal_miles().toString() + " - " + goal.getMilestone_date());

                            }
                        }
                });
                db.close();
            }
        });
//      End Insert Task

//        Remove Task Button
        removeGoalButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final GoalDatabase db = Room.databaseBuilder(v.getContext(), GoalDatabase.class, "MyGoalDatabase").build();
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        List<Goal> goals = db.goalDao().getAllGoals();
                        Log.d("Goals to be removed", String.valueOf(goals.size()));
                        db.goalDao().clearGoals();
                        Log.d("Goals left over", String.valueOf(goals.size()));
                    }
                });
                db.close();
//            End Remove Task
            }
        });
        return v;
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
