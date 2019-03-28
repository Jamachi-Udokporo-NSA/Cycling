package com.nsa.cecobike;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Arrays;

@Entity
public class Journey{

    @PrimaryKey(autoGenerate = true)
    private int jid;

    @ColumnInfo(name = "distance_miles")
    private Double distance;

    @ColumnInfo(name = "duration")
    private Double duration;

    @ColumnInfo(name = "date and time")
    private String dateAndTime ;

    @ColumnInfo(name = "coordinates")
    private ArrayList<Point>coordinates;

    public Journey(Double distance, Double duration, String dateAndTime, ArrayList<Point> coordinates) {
        this.distance = distance;
        this.duration = duration;
        this.dateAndTime = dateAndTime;
        this.coordinates = coordinates;
    }

    public void setJid(int jid) {
        this.jid = jid;
    }

    public int getJid() {
        return jid;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getDuration() {
        return duration;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public ArrayList<Point> getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "jid=" + jid +
                ", distance=" + distance +
                ", duration=" + duration +
                ", dateAndTime='" + dateAndTime + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
