package com.lambton.moviebuddy.ui.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tickets {
    @PrimaryKey(autoGenerate = true)
    private int ticket_id;

    private String movie_name;
    private String time;
    private int quantity;
    private double latitude;
    private double longitude;

    public int getTicket_id() {
        return ticket_id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Tickets( String movie_name, String time, int quantity, double latitude, double longitude) {
        this.movie_name = movie_name;
        this.time = time;
        this.quantity = quantity;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
