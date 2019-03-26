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

import java.util.ArrayList;
import java.util.List;

public class VIewMyJourney extends Fragment implements AdapterView.OnItemClickListener {
    //    View Creation

    private View v;

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

//    Database Implementation
    Activity context;


    //List Adapter
    private void ArrayAdapter(){
        //Obtain the data


        final JourneyDatabase db = Room.databaseBuilder(
                getActivity().getApplicationContext(),
                JourneyDatabase.class,
                "Journey Database"
        ).build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List journeys = db.journeyDao().getAllJourneys();
            }
        });

        int numberOfItems = 4;
        ArrayList<String> listOfJourneys = new ArrayList<>(numberOfItems);
//        listOfJourneys.add(getString(R.string.contents_of_list_row));

        for(int i=0; i<numberOfItems; i++){
            //Make sure you have a string with the name "contents_of_list_row" in your strings.xml resource file
            listOfJourneys.add(getString(R.string.contents_of_list_row));
        }

        //Create the adapter and connect to the data
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                listOfJourneys
        );

        //Fetch the listview and connect to the adapter
        ListView lv_journeys = getActivity().findViewById(R.id.lv_journeys); //Make sure that your listview in your layout file has this id
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
