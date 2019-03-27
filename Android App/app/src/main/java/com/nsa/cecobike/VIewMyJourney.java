package com.nsa.cecobike;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VIewMyJourney extends Fragment implements AdapterView.OnItemClickListener {
    //    View Creation

    private View v;
    private ArrayList<String> listOfJourneys = new ArrayList<>();
    private int numberOfJourneys;
    private JourneyDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Init

        v = inflater.inflate(R.layout.fragment_view_my_journey, container, false);
        ArrayAdapter();
//        v.findViewById(R.id.lv_journeys);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ArrayAdapter();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public VIewMyJourney() {
        // Required empty public constructor

    }
    //List Adapter
    private void ArrayAdapter(){
//        //Obtain the data

        db = Room.databaseBuilder(getContext(), JourneyDatabase.class, "MyJourneyDatabase").build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Journey> journeys = db.journeyDao().getAllJourneys();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String l = String.valueOf(journeys.size());
                        Log.d(l, "test 2000");
                    }
                });
            }


        });

        numberOfJourneys = 8;
        for(int i=0; i<numberOfJourneys; i++){
            listOfJourneys.add(String.format("Journey %d", i));
        }

        //Create the adapter and connect to the data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                listOfJourneys
        );

        //Fetch the listview and connect to the adapter
         Log.d(adapter.toString(), "Adapter2");

        ListView lv = v.findViewById(R.id.lv_journeys);
        lv.setAdapter(adapter);

//        Set this activity to be the event listener for when list items are pressed
//        lv_journeys.setOnItemClickListener(getActivity());
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
