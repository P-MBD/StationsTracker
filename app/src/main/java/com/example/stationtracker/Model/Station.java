package com.example.stationtracker.Model;

public class Station {
    private int id;
    private String title;
    private String line;
    private String address;

    public Station(int id, String title, String line, String address) {
        this.id = id;
        this.title = title;
        this.line = line;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
