package com.nsa.cecobike;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewAJourney extends Fragment {

    public ViewAJourney() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_ajourney, container, false);
        // Inflate the layout for this fragment
        Bundle bundle = getArguments();

        int text = bundle.getInt("Journey id");
        TextView JourneyText = (TextView) v.findViewById(R.id.text_journey);
        JourneyText.setText(String.valueOf(text));
        return v;
    }
}
