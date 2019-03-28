package com.nsa.cecobike;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.recyclerview.extensions.ListAdapter;
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
        v = inflater.inflate(R.layout.fragment_view_my_journey, container, false);
        ArrayAdapter();
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
       //Obtain the data

        db = Room.databaseBuilder(getContext(), JourneyDatabase.class, "MyJourneyDatabase").build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Journey> journeys = db.journeyDao().getAllJourneys();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numberOfJourneys = journeys.size();
                        listOfJourneys.add(String.format("Journey " + 0));
                        for(int i=0; i!=numberOfJourneys; i++){
                            listOfJourneys.add(String.format("Journey " + (i+1)));
                            Log.d(journeys.get(i).toString(), "Journey" + i);
                        }
                        //Create the adapter and connect to the data
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                getContext(),
                                android.R.layout.simple_list_item_1,
                                listOfJourneys
                        );
                        //Fetch the listview and connect to the adapter
                        ListView lv = v.findViewById(R.id.lv_journeys);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(VIewMyJourney.this);
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(), String.format("Item clicked on = %d", i), Toast.LENGTH_SHORT).show();
    }
}
