package com.rsbauer.roundelremote.webservices.models.response;

/**
 * Created by astro on 2/22/15.
 */
public class VehicleLocation {
    private VehicleStatus vehicleStatus;

    /**
     *
     * @return
     * The vehicleStatus
     */
    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    /**
     *
     * @param vehicleStatus
     * The vehicleStatus
     */
    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }
}
