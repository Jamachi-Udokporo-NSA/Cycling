package com.nsa.cecobike;

import android.Manifest;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Map extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private final int STORAGE_PERMISSION_CODE = 1;
    LatLng previousLocation;
    Button start_journey, finish_journey;
    private Chronometer Timer;
    private boolean running;
    LocationManager locationManager;
    boolean permissionIsGranted = false;
    Location location;

    //List of Points for Database:
    ArrayList<Point> coordinates = new ArrayList<>();
    Double TotalDistance = 0.0;

    public Map() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestStoragePermission();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_maps, container, false);
        AppCompatButton dialog = v.findViewById(R.id.finish_journey_button);

        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.finish_journey_button, new Dialogbox());
                fr.commit();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = ((SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map));
        FragmentManager fm = getChildFragmentManager();
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        //button to start the journey

        start_journey = (Button) view.findViewById(R.id.start_journey_button);
        finish_journey = (Button) view.findViewById(R.id.finish_journey_button);
        final JourneyDatabase db = Room.databaseBuilder(getContext(), JourneyDatabase.class, "MyJourneyDatabase").build();

        start_journey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start journey actions start here
                //remove the Toast below when finished testing
//                Toast.makeText(getContext(), "Start the journey button was clicked ", Toast.LENGTH_SHORT).show();
                boolean getCurrentLocationFailed = false;
                locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                requestStoragePermission();
                if (permissionIsGranted && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    try {
                        getCurrentLocation();
                        getCurrentLocationFailed = false;
                    }catch (Exception e){
                        Log.d("Location error" , "Couldn't get location");
                        getCurrentLocationFailed = true;
                       }
                    if (!getCurrentLocationFailed) {
                        start_journey.setVisibility(View.GONE);
                        finish_journey.setVisibility(View.VISIBLE);
                        Timer = view.findViewById(R.id.timer);
                        startTimer(Timer);
                    }
                } else if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    Toast.makeText(getContext(), "Couldn't get location, Please ensure you enable GPS and aeroplane mode is off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        finish_journey.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //finish journey actions start here

                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(),
                                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestStoragePermission();
                    return;
                }
                //Calculates Distance
                getlatlon();

                TotalDistance = TotalDistance * 100;
//                 Toast.makeText(getContext(), "Finish journey button was clicked ", Toast.LENGTH_SHORT).show();
                locationManager.removeUpdates(locationListenerGPS);
                mMap.setMyLocationEnabled(false);
                stopTimer(Timer);
                final Double seconds = ((double) calculateElapsedTime(Timer) /1000);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                final String currentDateAndTime = String.valueOf(dtf.format(now));
                Log.d(currentDateAndTime, "Date");
                Log.d("Timer", String.valueOf(seconds));
                        AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
//                        db.journeyDao().clearJourneys();
                        db.journeyDao().insertJourneys(
                                new Journey(TotalDistance, seconds, currentDateAndTime)
                        );
                        final List<Journey> journeys = db.journeyDao().getAllJourneys();
                        Log.d("Journey_TEST", String.format("Number of Journeys: %d", journeys.size()));
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(String.format("Number of Journeys: %d", journeys.size()),"Total Journeys");
                            }
                        });
                        db.close();
                    }
                });

                Dialogboxaction dialog = new Dialogboxaction();
                dialog.show(getActivity().getSupportFragmentManager(), "anything");
            }
        });
    }
    private long calculateElapsedTime(Chronometer mChronometer) {

        long stoppedMilliseconds = 0;

        String caronText = mChronometer.getText().toString();
        String array[] = caronText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000
                    + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 60 * 1000
                    + Integer.parseInt(array[1]) * 60 * 1000
                    + Integer.parseInt(array[2]) * 1000;
        }

        return stoppedMilliseconds;

    }

    public void startTimer (View v){
        if (!running) {
            Timer.setBase(SystemClock.elapsedRealtime());
            Timer.start();
            running = true;
        }
    }

    public void stopTimer (View v){
        if(running){
        Timer.stop();
        running=false;
        }
        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this.getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestStoragePermission();
            return;
        }
        mMap.setMyLocationEnabled(true);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Log.d("Location status", "Im here now");
        location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        Log.d("Location status", "Im here now 2");
        try {
            getCameraUpdates(location);
            previousLocation = new LatLng(location.getLatitude(), location.getLongitude());
        }catch (Exception e){
            Log.d("Last Location" , "Couldn't get last location,  ...applying another method");
//            locationManager.requestSingleUpdate(criteria, locationListenerGPS, Looper.myLooper());
//            LatLng l = new LatLng(location.getLatitude(), location.getLongitude());
//            Log.d("Lat", String.valueOf(l.latitude));

        }
        Log.d("Location status", "Im here now 3");
//        previousLocation = new LatLng(location.getLatitude(), location.getLongitude());
        Log.d("Location status", "Im here now 4");
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000,
                5, locationListenerGPS);
        Log.d("Location status", "Im here now 5");

    }


    private void getCameraUpdates(Location location)
    {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                .zoom(17)// Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }
    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this.getActivity(), "Access is now granted", Toast.LENGTH_SHORT).show();
                    permissionIsGranted = true;
                    // permission was granted, yay!
                } else {
//                    Toast.makeText(this.getActivity(), "Access has been declined by user", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this.getActivity(), "Permission must be accepted to start", Toast.LENGTH_SHORT).show();
                    permissionIsGranted = false;
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }
        }
    }

    public void addPolyLinesToMap(final Location location) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                if (previousLocation != null) {
                    PolylineOptions polyline = new PolylineOptions().add(previousLocation)
                            .add(new LatLng(location.getLatitude(), location.getLongitude())).width(20).color(Color.BLUE).geodesic(true);
//                mMap.addMarker(new MarkerOptions().position((previousLocation)).title("Old location"));
//                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("new location"));
                    coordinates.add(new Point(location.getLatitude(), location.getLongitude()));
                    mMap.addPolyline(polyline);
                }
                previousLocation = new LatLng(location.getLatitude(), location.getLongitude());
            }
        });
    }

    public void getlatlon(){
        //Calculating the distance in meters
        double latitude = 0;
        double longitude = 0;

        for (int i = 0; i+1 < coordinates.size(); i++){
            if (coordinates.get(i).getpLat() < coordinates.get(i+1).getpLat()){
                latitude = coordinates.get(i+1).getpLat() - coordinates.get(i).getpLat();
            }
            else if(coordinates.get(i).getpLat() > coordinates.get(i+1).getpLat()) {
                latitude = coordinates.get(i).getpLat() - coordinates.get(i+1).getpLat();
            }
            if (coordinates.get(i).getpLon() < coordinates.get(i+1).getpLon()){
                longitude = coordinates.get(i+1).getpLat() - coordinates.get(i).getpLat();
            }
            else if(coordinates.get(i).getpLon() > coordinates.get(i+1).getpLon()) {
                longitude = coordinates.get(i).getpLon() - coordinates.get(i+1).getpLon();
            }
            getcaldistance(latitude, longitude);
        }
    }

    public void getcaldistance(Double latitude, Double longitude){
        latitude = latitude * latitude;
        longitude = longitude * longitude;

        Double Distance = Math.sqrt(latitude + longitude);
        TotalDistance = TotalDistance + Distance;
        String t = String.valueOf(TotalDistance*100);
        Log.d(t, "size");
    }

    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
//            Toast.makeText(getActivity(), "Location update", Toast.LENGTH_SHORT).show();
            getCameraUpdates(location);
            addPolyLinesToMap(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    }
    ;

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
