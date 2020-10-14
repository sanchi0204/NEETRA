package com.example.safetapp.Model;

public class FirRecord {
    private String name;
    private String emailId;
    private String PhoneNo;
    private String date;
    private String time;
    private String policeStation;
    private String policeStationAddress;
    private String tokenId;
    private String crimeType;

    public FirRecord() {
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public FirRecord(String name, String emailId, String phoneNo, String date, String time, String policeStation, String policeStationAddress, String tokenId, String crimeType) {
        this.name = name;
        this.emailId = emailId;
        PhoneNo = phoneNo;
        this.date = date;
        this.time = time;
        this.policeStation = policeStation;
        this.policeStationAddress = policeStationAddress;
        this.tokenId = tokenId;
        this.crimeType = crimeType;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public String getPoliceStationAddress() {
        return policeStationAddress;
    }

    public void setPoliceStationAddress(String policeStationAddress) {
        this.policeStationAddress = policeStationAddress;
    }
}
