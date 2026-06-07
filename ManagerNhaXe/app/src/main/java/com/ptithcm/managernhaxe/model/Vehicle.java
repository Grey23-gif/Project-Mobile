package com.ptithcm.managernhaxe.model;

public class Vehicle {

    public int id;

    public String plateNumber;
    public String type;
    public String ownerName;

    public Vehicle() {
    }

    public Vehicle(String plateNumber,
                   String type,
                   String ownerName) {

        this.plateNumber = plateNumber;
        this.type = type;
        this.ownerName = ownerName;
    }
}