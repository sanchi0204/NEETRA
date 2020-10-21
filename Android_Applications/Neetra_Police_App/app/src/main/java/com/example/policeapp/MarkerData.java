package com.example.policeapp;

public class MarkerData {

    private double lat;
    private double lon;
    private String crime;

    public MarkerData() {
    }

    public MarkerData(double lat, double lon, String crime) {
        this.lat = lat;
        this.lon = lon;
        this.crime = crime;
    }

    double getLat() {
        return lat;
    }

    void setLat(double lat) {
        this.lat = lat;
    }

    double getLon() {
        return lon;
    }

    void setLon(double lon) {
        this.lon = lon;
    }

    String getCrime() {
        return crime;
    }

    void setCrime(String crime) {
        this.crime = crime;
    }
}
