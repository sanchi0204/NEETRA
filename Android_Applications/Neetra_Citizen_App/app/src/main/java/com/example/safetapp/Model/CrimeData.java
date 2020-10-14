package com.example.safetapp.Model;

public class CrimeData {
    private String uId;
    private String crimeType;
    private String lat;
    private String lon;

    public CrimeData(String uId, String crimeType, String lat, String lon) {
        this.uId = uId;
        this.crimeType = crimeType;
        this.lat = lat;
        this.lon = lon;
    }

    public CrimeData() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
