package com.rsbauer.roundelremote.webservices.models.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by astro on 2/8/15.
 */
public class Vehicles {

    private List<Vehicle> vehicles = new ArrayList<Vehicle>();

    /**
     *
     * @return
     * The vehicles
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     *
     * @param vehicles
     * The vehicles
     */
    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }


}
