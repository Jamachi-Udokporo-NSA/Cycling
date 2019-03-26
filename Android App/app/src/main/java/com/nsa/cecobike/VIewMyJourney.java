package com.nsa.cecobike;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class VIewMyJourney extends Fragment implements AdapterView.OnItemClickListener {
    //    View Creation
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Init
        ArrayAdapter();
        View v = inflater.inflate(R.layout.fragment_view_my_journey, container, false);
        return v;
    }

    public VIewMyJourney() {
        // Required empty public constructor

    }

//    Database Implementation
    Activity context;


    //List Adapter
    private void ArrayAdapter(){
        //Obtain the data
        ArrayList<String> listOfJourneys = new ArrayList<>();
        listOfJourneys.add(getString(R.string.contents_of_list_row));

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

        //Create the adapter and connect to the data
//        ArrayAdapter<Journey> adapter = new ArrayAdapter<Journey>(
//                getActivity().getApplicationContext(),
//                android.R.layout.simple_list_item_1,
//                Adapter.
//        );

        //Fetch the listview and connect to the adapter
//        ListView lv_journeys = getView().findViewById(R.id.lv_journeys); //Make sure that your listview in your layout file has this id
//        lv_journeys.setAdapter(adapter);

        //Set this activity to be the event listener for when list items are pressed
//        lv_journeys.setOnItemClickListener(this);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
