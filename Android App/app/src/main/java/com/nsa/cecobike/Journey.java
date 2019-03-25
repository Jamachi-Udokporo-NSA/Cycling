package com.nsa.cecobike;


import java.util.ArrayList;

public class Journey{
    private Double distance;
    private Double duration;
    private ArrayList<Point> points;

    public Journey(Double distance, Double duration, ArrayList<Point> points) {
        this.distance = distance;
        this.duration = duration;
        this.points = points;
    }

    public Double getDistance() {
        return distance;
    }

    public Double getDuration() {
        return duration;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}
