package com.example.devoxx;

public class Fan {
    private String username;

    private String password;

    private String nearestVenue;

    private String show;

    private boolean swiftie;

    public Fan() {
    }

    public Fan(Fan user) {
        this.username = user.username;
        this.password = user.password;
        this.nearestVenue = user.nearestVenue;
        this.show = user.show;
        this.swiftie = user.swiftie;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNearestVenue() {
        return nearestVenue;
    }

    public void setNearestVenue(String nearestVenue) {
        this.nearestVenue = nearestVenue;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public boolean isSwiftie() {
        return swiftie;
    }

    public void setSwiftie(boolean swiftie) {
        this.swiftie = swiftie;
    }

}
