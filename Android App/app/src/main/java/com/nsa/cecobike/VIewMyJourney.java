package com.nsa.cecobike;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

public class VIewMyJourney extends Fragment implements AdapterView.OnItemClickListener {
    public VIewMyJourney() {
        // Required empty public constructor

    }

//    Database Implementation
    Activity context;
    //List Adapter
    private void ArrayAdapter(){
        //Obtain the data
        final List<Journey> listOfJourneys = new ArrayList<>();

        final JourneyDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(),
                JourneyDatabase.class,
                "Journey Database"
        ).build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Journey> journeys = db.journeyDao().getAllJourneys();
            }
        });
    }

//    View Creation
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_view_my_journey, container, false);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
