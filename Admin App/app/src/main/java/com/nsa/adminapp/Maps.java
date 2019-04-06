package com.nsa.adminapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import static android.support.constraint.Constraints.TAG;

public class Maps extends Fragment implements OnMapReadyCallback {
    private HeatmapTileProvider mProvider;
    private TileOverlay mOverlay;
    private GoogleMap mMap;
    private List<LatLng> list = null;
    private Points p;
    String data = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = new Points("");
        addHeatMap();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, null, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference reff = database.getDatabase().getReference().child("Journey");
        Query positionQuery = reff;
        positionQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = "";
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    data += singleSnapshot.child("points").getValue().toString() + ";";
                    Log.d("dataFire", singleSnapshot.child("points").getValue().toString());
                }
                p.setLocations(data);
                Log.d("lTag", "" +p.getLocations());
//                for (LatLng latLng : p.getLocations()) {
//                    mMap.addMarker(new MarkerOptions().position(latLng));
//                }
                Collection<LatLng> collection = new ArrayList<>(p.getLocations());
                mProvider = new HeatmapTileProvider.Builder().data(collection).build();
                mOverlay = mMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });



        mMap = googleMap;
        LatLng cardiff = new LatLng(51.495624, -3.176227);
//        LatLng cardiff = new LatLng(-37.1886, 145.708);
        mMap.addMarker(new MarkerOptions().position(cardiff).title("Marker in Cardiff"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cardiff));



    }

    protected GoogleMap getMap() {

        return mMap;
    }

    private void addHeatMap() {


        try {
            list = readItems(R.raw.points);
        } catch (JSONException e) {
            Toast.makeText(this.getActivity(), "Problem reading list of locations.", Toast.LENGTH_LONG).show();

        }

        // Create a heat map tile provider, passing it the latlngs of the points stations.
        mProvider = new HeatmapTileProvider.Builder()
                .data(list)
                .build();
    }

    //Reference:https://developers.google.com/maps/documentation/android-sdk/utility/heatmap
    private ArrayList<LatLng> readItems(int resource) throws JSONException {
        ArrayList<LatLng> list = new ArrayList<LatLng>();
        InputStream inputStream = getResources().openRawResource(resource);
        String json = new Scanner(inputStream).useDelimiter("\\A").next();
        JSONArray array = new JSONArray(json);
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            double lat = object.getDouble("lat");
            double lng = object.getDouble("lng");
            list.add(new LatLng(lat, lng));
        }
        return list;
    }
}
