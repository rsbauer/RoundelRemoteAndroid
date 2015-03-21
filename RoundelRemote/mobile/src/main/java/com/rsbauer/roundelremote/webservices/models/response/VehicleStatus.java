package com.rsbauer.roundelremote.webservices.models.response;

/**
 * Created by astro on 2/22/15.
 */
public class VehicleStatus {

    private String vin;
    private String updateTime;
    private Position position;

    /**
     *
     * @return
     * The vin
     */
    public String getVin() {
        return vin;
    }

    /**
     *
     * @param vin
     * The vin
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     *
     * @return
     * The updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     *
     * @param updateTime
     * The updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     * @return
     * The position
     */
    public Position getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

}
