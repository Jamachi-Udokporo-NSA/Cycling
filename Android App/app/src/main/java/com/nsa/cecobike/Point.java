package com.nsa.cecobike;

//Holds Lat and Long as Decimal Point to be converted to string
public class Point {
    private Double pLat;
    private Double pLon;

    public Point(Double pLat, Double pLon) {
        this.pLat = pLat;
        this.pLon = pLon;
    }

    public Double getpLat() {
        return pLat;
    }

    public Double getpLon() {
        return pLon;
    }

}
