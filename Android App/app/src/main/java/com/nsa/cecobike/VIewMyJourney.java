package com.nsa.cecobike;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class VIewMyJourney extends Fragment implements AdapterView.OnItemClickListener {
    //    View Creation

    private View v;
    private List<Journey> listOfJourneys;
    private int numberOfJourneys;
    private JourneyDatabase db;

    private static final int COLUMN_COUNT = 1;

    private RecyclerView recyclerView;
    private CustomRecyclerViewAdapter customAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_view_journey_recycler, container, false);
//        ArrayAdapter();

        listOfJourneys = new ArrayList<Journey>(1){{
            add(new Journey(1.0,2.0,"test"));
            add(new Journey(1.0,2.0,"test"));
            add(new Journey(1.0,2.0,"test"));
            add(new Journey(1.0,2.0,"test"));
            add(new Journey(1.0,2.0,"test"));
            add(new Journey(1.0,2.0,"test"));
            add(new Journey(1.0,2.0,"test"));
        }};


        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        CustomRecyclerViewAdapter recyclerViewAdapter = new CustomRecyclerViewAdapter(getContext(), listOfJourneys);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

//        this.customAdapter = new CustomRecyclerViewAdapter(data);
//        this.recyclerView.setAdapter(this.customAdapter);
//        this.recyclerView = getActivity().findViewById(R.id.recycler_view);
//        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLUMN_COUNT);
//        this.recyclerView.setLayoutManager(layoutManager);
//        this.recyclerView.setAdapter(this.customAdapter);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
//        recyclerView.addItemDecoration(dividerItemDecoration);

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
//    private void ArrayAdapter(){
//        Obtain the data
//        db = Room.databaseBuilder(getContext(), JourneyDatabase.class, "MyJourneyDatabase").build();
//
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                final List<Journey> journeys = db.journeyDao().getAllJourneys();
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//                    @Override
//                    public void run() {
//                        numberOfJourneys = journeys.size();
//                        for(int i=0; i!=numberOfJourneys; i++){
//                            listOfJourneys.add(String.format("Journey " + (i+1) + System.lineSeparator() +journeys.get(i).getDateAndTime()));
//                            Log.d(journeys.get(i).toString(), "Journey" + i);
//                        }
                        //Create the adapter and connect to the data
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                                getContext(),
//                                R.layout.list_view_layout,
//                                listOfJourneys
//                        );
                        //Fetch the listview and connect to the adapter
//                        ListView lv = v.findViewById(R.id.lv_journeys);
//                        lv.setAdapter(adapter);
//                        lv.setOnItemClickListener(VIewMyJourney.this);
//                    }
//                });
//            }
//        });
//    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getContext(), String.format("Item clicked on = %d", i), Toast.LENGTH_SHORT).show();
    }


    private class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.CustomViewHolder> {

        Context mContext;
        List<Journey>mData;

        public CustomRecyclerViewAdapter(Context mContext, List<Journey> mData) {
            this.mContext = mContext;
            this.mData = mData;

            Log.d("TEST", String.valueOf(mData.size()));
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(mContext).inflate(R.layout.fragment_view_my_journey, parent, false);
            return new CustomViewHolder(row);
        }

        @Override
        public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int position) {
            customViewHolder.JourneyText.setText("Journey" + (position + 1));
            customViewHolder.DateAndTimeText.setText(mData.get(position).getDateAndTime());

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        protected class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private AppCompatTextView JourneyText;
            private AppCompatTextView DateAndTimeText;
            private AppCompatImageView icon;

            CustomViewHolder(View itemView) {
                super(itemView);

                this.JourneyText = (AppCompatTextView) itemView.findViewById(R.id.journey_text);
                this.DateAndTimeText = (AppCompatTextView) itemView.findViewById(R.id.dateAndTime_text);
                this.icon = (AppCompatImageView) itemView.findViewById(R.id.progress);

                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                int i = this.getAdapterPosition();
                Toast.makeText(getContext(),
                        String.format(getString(R.string.item_on_tapped_toast_test),
                                String.valueOf(i),
                                this.JourneyText.getText()),
                        Toast.LENGTH_SHORT).show();
            }
        }

    }
}
