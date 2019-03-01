package com.example.worldskills.Model;

public class ATM {
    private int id;
    private String address, type, hours_start, hours_end;
    private double lat, lon;

    public int getId() { return id; }

    public String getAddress() { return address; }

    public String getType() { return type; }

    public String getTimeStart() { return hours_start; }

    public String getTimeEnd() { return hours_end; }

    public double getLatitude() { return lat; }

    public double getLongitude() { return lon; }
}
