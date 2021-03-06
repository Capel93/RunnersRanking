package com.example.joanmarc.runnersranking;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JoanMarc on 23/03/2015.
 */
public class User implements Serializable {
    private String name;
    private String userName;
    private String password;
    private String email;

    private List<User> friends;
    private List<RouteClass> routes;

    public User(String userName) {

        this.userName = userName;
        this.friends = new ArrayList<>();
        this.routes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<RouteClass> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteClass> routes) {
        this.routes = routes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}


