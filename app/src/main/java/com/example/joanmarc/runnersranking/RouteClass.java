package com.example.joanmarc.runnersranking;

import com.example.joanmarc.myapplication.backend.routeApi.model.Route;

import com.google.android.gms.maps.model.Polyline;

import java.io.Serializable;


import java.util.Date;
import java.util.List;

/**
 * Created by JoanMarc on 23/03/2015.
 */
public class RouteClass implements Serializable{
    private String startPoint;
    private String finishPoint;
    private long time;
    private Date date;
    private double distance;
    private double calories;
    private List<Double> rates;
    //private Polyline route;

    private List<Double> longitudes;
    private List<Double> latitudes;

    public RouteClass(Route route) {
        try {
            this.startPoint = route.getStartPoint();
            this.finishPoint = route.getFinishPoint();
        }catch (NullPointerException n){
            this.startPoint = "";
            this.finishPoint ="";
        }

        try {

            this.time = route.getTime();
        }catch (NullPointerException n){

            this.time = (long) 0.0;
        }

        try {

            Date d= new Date();
            d.setTime(route.getDate().getValue());
            this.date = d;
        }catch (NullPointerException n){
            this.date= new Date();
        }
        try {
            this.distance = route.getDistance();
        }catch (NullPointerException n){
            this.distance = 0.0;
        }

        try {

        }catch (NullPointerException n){

        }

        try {
            this.calories = route.getCalories();
        }catch (NullPointerException n){
            this.calories = 0.0;
        }




        //this.rates = route.getRates();
        this.longitudes = route.getLongitudes();
        this.latitudes = route.getLatitudes();
    }


    public RouteClass() {
    }

    public List<Double> getLongitudes() {
        return longitudes;
    }

    public void setLongitudes(List<Double> longitudes) {
        this.longitudes = longitudes;
    }

    public List<Double> getLatitudes() {
        return latitudes;
    }

    public void setLatitudes(List<Double> latitudes) {
        this.latitudes = latitudes;
    }

    public RouteClass(String startPoint, String finishPoint, long time, Date date, double distance, double calories, List<Double> rates, List<Double> longitudes,List<Double> latitudes) {
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
        this.time = time;
        this.date = date;
        this.distance = distance;
        this.calories = calories;
        this.rates = rates;
        this.longitudes = longitudes;
        this.latitudes = latitudes;
    }

    public RouteClass(Date date) {
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
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

    public List<Double> getRates() {
        return rates;
    }

    public void setRates(List<Double> rates) {
        this.rates = rates;
    }


}
