package com.rsbauer.roundelremote.webservices.models.request;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Created by astro on 2/8/15.
 */
public class BMWAuthenticate {
    String grant_type;

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getVehicle_data() {
        return vehicle_data;
    }

    public void setVehicle_data(String vehicle_data) {
        this.vehicle_data = vehicle_data;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String password;
    String scope;
    String vehicle_data;
    String username;

    public String toUrlEncoded() {
        try {
            return "grant_type=" + URLEncoder.encode(this.getGrant_type(), "UTF-8") +
                    "&password=" + URLEncoder.encode(this.getPassword(), "UTF-8") +
                    "&scope="+ URLEncoder.encode(this.getScope(), "UTF-8") +
                    "&username=" + URLEncoder.encode(this.getUsername(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
