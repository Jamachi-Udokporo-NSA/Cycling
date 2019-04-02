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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;

@Entity
public class Journey{

    @PrimaryKey(autoGenerate = true)
    private int jid;

    @ColumnInfo(name = "distance_miles")
    private Double distance;

    @ColumnInfo(name = "duration")
    private Double duration;

    @ColumnInfo(name = "date")
    private Date date;

//    @ColumnInfo(name = "Coordinates")
//    private ArrayList<Point>coordinates = new ArrayList<>();


//    @ColumnInfo(name = "coordinates")
//    private ArrayList<Point>coordinates;

    public Journey(Double distance, Double duration, Date date){//, ArrayList<Point> coordinates) {
        this.distance = distance;
        this.duration = duration;
        this.date = date;
//        this.coordinates.addAll(coordinates);
    }


//        this.coordinates = coordinates;

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

    public Date getDate() {
        return date;
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
//                ", coordinates=" + coordinates +
                '}';
    }
}
