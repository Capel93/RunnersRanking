package com.example.joanmarc.runnersranking;

import com.google.android.gms.maps.model.Polyline;

import java.sql.Time;
import java.util.Date;
import java.util.List;

/**
 * Created by JoanMarc on 23/03/2015.
 */
public class Route {
    private String startPoint;
    private String finishPoint;
    private Time time;
    private Date date;
    private double distance;
    private double calories;
    private double[] rates;
    private Polyline route;

    public Route(Date date) {
        this.date = date;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getFinishPoint() {
        return finishPoint;
    }

    public void setFinishPoint(String finishPoint) {
        this.finishPoint = finishPoint;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double[] getRates() {
        return rates;
    }

    public void setRates(double[] rates) {
        this.rates = rates;
    }
}