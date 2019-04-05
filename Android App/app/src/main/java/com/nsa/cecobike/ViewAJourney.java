package com.nsa.cecobike;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.okhttp.internal.InternalCache;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ViewAJourney extends Fragment implements OnMapReadyCallback {

    private List<Journey> listOfJourneys;
    private JourneyDatabase db;
    private GoogleMap mMap;
    int text;
    ArrayList<Point> coordinates = new ArrayList<>();
//    FirebaseDatabase database;
    DatabaseReference reff;
    Journey ajourney;

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
                        Log.d("test journey id", String.valueOf(listOfJourneys.get(text).toString()));
                        Bundle bundle = getArguments();
                        text = bundle.getInt("Journey id");
                        Log.d("actual journey id", String.valueOf(text));
                        final TextView timeAndDateText = v.findViewById(R.id.time_and_date_text);
                        final TextView distAndDurationText = v.findViewById(R.id.distance_and_duration_text);
                        final TextView emissionsText = v.findViewById(R.id.emissions_text);
//                        JourneyText.setText(String.format("Date: %s%s%sDistance: %s Miles%s%sDuration: %ss", listOfJourneys.get(text).getDate(), System.lineSeparator(), System.lineSeparator(), listOfJourneys.get(text).getDistance(), System.lineSeparator(), System.lineSeparator(), listOfJourneys.get(text).getDuration()));
                        timeAndDateText.setText("Date: " + android.text.format.DateFormat.format("dd-MM-yyyy", (listOfJourneys.get(text).getDate())) + "  Time: " + android.text.format.DateFormat.format("HH:mm:ss a" ,listOfJourneys.get(text).getDate()));
                        distAndDurationText.setText("Distance: " + listOfJourneys.get(text).getDistance() + " Km" + "   Duration: " + listOfJourneys.get(text).getDuration()+"s");
                        emissionsText.setText("Emissions saved: " + (listOfJourneys.get(text).getDistance() * 271) + "g");

                        ajourney = new Journey();
                        reff = FirebaseDatabase.getInstance().getReference("Journey");
                        ajourney.setDistance(listOfJourneys.get(0).getDistance());
//                        Double dist = Double.parseDouble(distAndDurationText.getText().toString().trim());
                        reff.push().setValue(ajourney);


                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(new LatLng(listOfJourneys.get(text).getCoordinates().get(0).getpLat() , listOfJourneys.get(text).getCoordinates().get(0).getpLon())).title("Start location"));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target((new LatLng(listOfJourneys.get(text).getCoordinates().get(0).getpLat() , listOfJourneys.get(text).getCoordinates().get(0).getpLon())))      // Sets the center of the map to location user
                .zoom(10)// Sets the zoom
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        coordinates = listOfJourneys.get(text).getCoordinates();
        for (int i = 0; i+1 < coordinates.size(); i++){
            addPolyLinesToMap(coordinates.get(i).getpLat(), coordinates.get(i).getpLon(), coordinates.get((i+1)).getpLat(),coordinates.get((i+1)).getpLon());
        }
        mMap.addMarker(new MarkerOptions().position(new LatLng(listOfJourneys.get(text).getCoordinates().get(coordinates.size() - 1).getpLat() , listOfJourneys.get(text).getCoordinates().get(coordinates.size() - 1).getpLon())).title("End location"));
    }

    public void addPolyLinesToMap(final Double pLat1, final Double pLon1, final Double pLat2, final Double pLon2) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                    PolylineOptions polyline = new PolylineOptions().add(new LatLng(pLat1, pLon1))
                            .add(new LatLng(pLat2, pLon2)).width(20).color(Color.BLUE).geodesic(true);
                    mMap.addPolyline(polyline);
            }
        });
    }
}