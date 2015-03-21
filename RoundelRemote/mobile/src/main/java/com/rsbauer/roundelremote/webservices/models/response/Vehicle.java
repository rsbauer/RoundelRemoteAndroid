package com.rsbauer.roundelremote.webservices.models.response;

/**
 * Created by astro on 2/8/15.
 */
public class Vehicle {
    /*
    		"vin": "WBA1J7C56EVW84688",
		"model": "M235I",
		"bodytype": "F22",
		"driveTrain": "CONV",
		"fuelType": "PETROL",
		"color": "ESTORILBLAU METALLIC   ",
		"colorCode": "B45",
		"brand": "BMW",
		"yearOfConstruction": 2014,
		"statisticsCommunityEnabled": false,
		"statisticsAvailable": false,
		"hub": "HUB_US",
		"hasAlarmSystem": false,
		"countryCode": "V2-US",
		"vehicleFinder": "ACTIVATED",
		"hornBlow": "ACTIVATED",
		"lightFlash": "ACTIVATED",
		"doorLock": "ACTIVATED",
		"doorUnlock": "NOT_SUPPORTED",
		"climateControl": "START_TIMER",
		"climateNow": "ACTIVATED",
		"chargingControl": "NOT_SUPPORTED",
		"chargeNow": "NOT_SUPPORTED",
		"sendPoi": "ACTIVATED",
		"rangeMap": "NOT_SUPPORTED",
		"lastDestinations": "NOT_SUPPORTED",
		"intermodalRouting": "NOT_AVAILABLE",
		"climateFunction": "VENTILATION",
		"onlineSearchMode": "MAP",
		"lscType": "NOT_SUPPORTED"
     */

    String vin;
    String model;
    String bodytype;
    String driveTrain;
    String fuelType;
    String color;
    String colorCode;
    String brand;
    String yearOfConstruction;
    String statisticsCommunityEnabled;
    String statisticsAvailable;
    String hub;
    String hasAlarmSystem;
    String countryCode;
    String vehicleFinder;
    String hornBlow;
    String lightFlash;
    String doorLock;
    String doorUnlock;
    String climateControl;
    String climateNow;
    String chargingControl;
    String chargeNow;

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBodytype() {
        return bodytype;
    }

    public void setBodytype(String bodytype) {
        this.bodytype = bodytype;
    }

    public String getDriveTrain() {
        return driveTrain;
    }

    public void setDriveTrain(String driveTrain) {
        this.driveTrain = driveTrain;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(String yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public String getStatisticsCommunityEnabled() {
        return statisticsCommunityEnabled;
    }

    public void setStatisticsCommunityEnabled(String statisticsCommunityEnabled) {
        this.statisticsCommunityEnabled = statisticsCommunityEnabled;
    }

    public String getStatisticsAvailable() {
        return statisticsAvailable;
    }

    public void setStatisticsAvailable(String statisticsAvailable) {
        this.statisticsAvailable = statisticsAvailable;
    }

    public String getHub() {
        return hub;
    }

    public void setHub(String hub) {
        this.hub = hub;
    }

    public String getHasAlarmSystem() {
        return hasAlarmSystem;
    }

    public void setHasAlarmSystem(String hasAlarmSystem) {
        this.hasAlarmSystem = hasAlarmSystem;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getVehicleFinder() {
        return vehicleFinder;
    }

    public void setVehicleFinder(String vehicleFinder) {
        this.vehicleFinder = vehicleFinder;
    }

    public String getHornBlow() {
        return hornBlow;
    }

    public void setHornBlow(String hornBlow) {
        this.hornBlow = hornBlow;
    }

    public String getLightFlash() {
        return lightFlash;
    }

    public void setLightFlash(String lightFlash) {
        this.lightFlash = lightFlash;
    }

    public String getDoorLock() {
        return doorLock;
    }

    public void setDoorLock(String doorLock) {
        this.doorLock = doorLock;
    }

    public String getDoorUnlock() {
        return doorUnlock;
    }

    public void setDoorUnlock(String doorUnlock) {
        this.doorUnlock = doorUnlock;
    }

    public String getClimateControl() {
        return climateControl;
    }

    public void setClimateControl(String climateControl) {
        this.climateControl = climateControl;
    }

    public String getClimateNow() {
        return climateNow;
    }

    public void setClimateNow(String climateNow) {
        this.climateNow = climateNow;
    }

    public String getChargingControl() {
        return chargingControl;
    }

    public void setChargingControl(String chargingControl) {
        this.chargingControl = chargingControl;
    }

    public String getChargeNow() {
        return chargeNow;
    }

    public void setChargeNow(String chargeNow) {
        this.chargeNow = chargeNow;
    }

    public String getSendPoi() {
        return sendPoi;
    }

    public void setSendPoi(String sendPoi) {
        this.sendPoi = sendPoi;
    }

    public String getRangeMap() {
        return rangeMap;
    }

    public void setRangeMap(String rangeMap) {
        this.rangeMap = rangeMap;
    }

    public String getLastDestinations() {
        return lastDestinations;
    }

    public void setLastDestinations(String lastDestinations) {
        this.lastDestinations = lastDestinations;
    }

    public String getIntermodalRouting() {
        return intermodalRouting;
    }

    public void setIntermodalRouting(String intermodalRouting) {
        this.intermodalRouting = intermodalRouting;
    }

    public String getClimateFunction() {
        return climateFunction;
    }

    public void setClimateFunction(String climateFunction) {
        this.climateFunction = climateFunction;
    }

    public String getOnlineSearchMode() {
        return onlineSearchMode;
    }

    public void setOnlineSearchMode(String onlineSearchMode) {
        this.onlineSearchMode = onlineSearchMode;
    }

    public String getLscType() {
        return lscType;
    }

    public void setLscType(String lscType) {
        this.lscType = lscType;
    }

    String sendPoi;
    String rangeMap;
    String lastDestinations;
    String intermodalRouting;
    String climateFunction;
    String onlineSearchMode;
    String lscType;

}
