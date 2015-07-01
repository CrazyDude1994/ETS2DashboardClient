package com.crazydude.truckdashboard;

/**
 * Created by Crazy on 30.06.2015.
 */
public class TruckInfo {

    private boolean engineEnabled;
    private boolean trailerAttached;

    private float speed;
    private float rpm;
    private float rpmMax;
    private float fuel;
    private float fuelCapacity;
    private float fuelRate;
    private float fuelAvgConsumption;

    private int gear;
    private int gears;

    public TruckInfo() {
    }

    public boolean isEngineEnabled() {
        return engineEnabled;
    }

    public void setEngineEnabled(boolean engineEnabled) {
        this.engineEnabled = engineEnabled;
    }

    public boolean isTrailerAttached() {
        return trailerAttached;
    }

    public void setTrailerAttached(boolean trailerAttached) {
        this.trailerAttached = trailerAttached;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getRpm() {
        return rpm;
    }

    public void setRpm(float rpm) {
        this.rpm = rpm;
    }

    public float getRpmMax() {
        return rpmMax;
    }

    public void setRpmMax(float rpmMax) {
        this.rpmMax = rpmMax;
    }

    public float getFuel() {
        return fuel;
    }

    public void setFuel(float fuel) {
        this.fuel = fuel;
    }

    public float getFuelCapacity() {
        return fuelCapacity;
    }

    public void setFuelCapacity(float fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public float getFuelRate() {
        return fuelRate;
    }

    public void setFuelRate(float fuelRate) {
        this.fuelRate = fuelRate;
    }

    public float getFuelAvgConsumption() {
        return fuelAvgConsumption;
    }

    public void setFuelAvgConsumption(float fuelAvgConsumption) {
        this.fuelAvgConsumption = fuelAvgConsumption;
    }

    public int getGear() {
        return gear;
    }

    public void setGear(int gear) {
        this.gear = gear;
    }

    public int getGears() {
        return gears;
    }

    public void setGears(int gears) {
        this.gears = gears;
    }
}
