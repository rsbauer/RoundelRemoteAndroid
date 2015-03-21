package com.rsbauer.roundelremote.webservices.models.response;

import java.util.Date;

/**
 * Created by astro on 2/22/15.
 */
public class UserLocation {

    public String latitude;
    public String longitude;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Date dateTime;


}
