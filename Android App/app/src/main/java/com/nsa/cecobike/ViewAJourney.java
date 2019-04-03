package com.nsa.cecobike;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.squareup.okhttp.internal.InternalCache;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


public class ViewAJourney extends Fragment implements OnMapReadyCallback {

    private List<Journey> listOfJourneys;
    private JourneyDatabase db;
    private GoogleMap mMap;

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
        getJourneyInfo(v);
        SupportMapFragment mapFragment = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.view_my_journey_on_map));
        FragmentManager fm = getChildFragmentManager();
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.view_my_journey_on_map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return v;
    }

    private View getJourneyInfo(final View v){
        db = Room.databaseBuilder(getContext(), JourneyDatabase.class, "MyJourneyDatabase").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<Journey> journeys = db.journeyDao().getAllJourneys();
                getActivity().runOnUiThread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void run() {
                        listOfJourneys = journeys;
                        Log.d("test journey id", String.valueOf(listOfJourneys.get(0).getJid()));
                        Bundle bundle = getArguments();
                        int text = bundle.getInt("Journey id");
                        Log.d("actual journey id", String.valueOf(text));
                        TextView timeAndDateText = v.findViewById(R.id.time_and_date_text);
//                        JourneyText.setText(String.format("Date: %s%s%sDistance: %s Miles%s%sDuration: %ss", listOfJourneys.get(text).getDate(), System.lineSeparator(), System.lineSeparator(), listOfJourneys.get(text).getDistance(), System.lineSeparator(), System.lineSeparator(), listOfJourneys.get(text).getDuration()));
                        timeAndDateText.setText("Date: " + android.text.format.DateFormat.format("dd-MM-yyyy", (listOfJourneys.get(text).getDate())) + "  Time: " + android.text.format.DateFormat.format("HH:mm:ss a" ,listOfJourneys.get(text).getDate()));
//                                + System.lineSeparator() + System.lineSeparator() + "Distance: " + listOfJourneys.get(text).getDistance() + " Km"+ System.lineSeparator() + System.lineSeparator() + "Duration: " + listOfJourneys.get(text).getDuration());
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
