package com.example.policeapp;

public class ReportItem
{
private String date;
    private String name;
    private String aadhar;
    private String crime;
    private String proof;
    private String time;

    public ReportItem(String date, String name, String aadhar, String crime, String proof, String time) {
        this.date = date;
        this.name = name;
        this.aadhar = aadhar;
        this.crime = crime;
        this.proof = proof;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}