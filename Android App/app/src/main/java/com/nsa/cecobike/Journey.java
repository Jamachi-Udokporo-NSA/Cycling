package com.nsa.cecobike;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

//    @ColumnInfo(name = "coordinates")
//    private ArrayList<Point>coordinates;


    public Journey(Double distance, Double duration, String date, String time) {
        this.distance = distance;
        this.duration = duration;
        this.date = date;
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    //    public ArrayList<Point> getCoordinates() {
//        return coordinates;
//    }


    @Override
    public String toString() {
        return "Journey{" +
                "jid=" + jid +
                ", distance=" + distance +
                ", duration=" + duration +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
