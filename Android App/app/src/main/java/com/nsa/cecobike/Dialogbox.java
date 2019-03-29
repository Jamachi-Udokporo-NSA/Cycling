package com.nsa.cecobike;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Dialogbox extends Fragment {
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dialogbox, container, false);
        button = (Button) v.findViewById(R.id.finish_journey_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        return v;
    }

    public void openDialog(){
        Dialogboxaction exmapleDilaog = new Dialogboxaction();
        Dialogboxaction.show(getSupportFragmentManager(), "anything I want");
    }
}
