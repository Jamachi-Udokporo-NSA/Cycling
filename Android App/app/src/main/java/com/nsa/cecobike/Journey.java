package com.nsa.cecobike;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

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


    public Journey(Double distance, Double duration, String dateAndTime) {
        this.distance = distance;
        this.duration = duration;
        this.dateAndTime = dateAndTime;
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

    @Override
    public String toString() {
        return "Journey{" +
                "jid=" + jid +
                ", distance=" + distance +
                ", duration=" + duration +
                ", dateAndTime='" + dateAndTime + '\'' +
                '}';
    }
}
