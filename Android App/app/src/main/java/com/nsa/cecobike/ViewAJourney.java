package com.nsa.cecobike;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ViewAJourney extends Fragment {

    private List<Journey> listOfJourneys;
    private JourneyDatabase db;

    public ViewAJourney() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_ajourney, container, false);
        // Inflate the layout for this fragment
        getJourneyInfo();
        db = Room.databaseBuilder(getContext(), JourneyDatabase.class, "MyJourneyDatabase").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Journey> journeys = db.journeyDao().getAllJourneys();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        listOfJourneys = journeys;
                        Bundle bundle = getArguments();
                        int text = bundle.getInt("Journey id");
                        TextView JourneyText = (TextView) v.findViewById(R.id.text_journey);
                        JourneyText.setText(String.format("%s%s%s%s%s", listOfJourneys.get(text).getDateAndTime(), System.lineSeparator(), listOfJourneys.get(text).getDistance(), System.lineSeparator(), listOfJourneys.get(text).getDuration()));
                        return v;
                    }
                });
            }
        });
        return v;
    }

    private void getJourneyInfo(){
        
    }
}
