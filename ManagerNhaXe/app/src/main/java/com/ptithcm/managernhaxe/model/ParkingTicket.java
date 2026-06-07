package com.ptithcm.managernhaxe.model;

public class ParkingTicket {

    public int id;
    public int vehicleId;

    public String plateNumber;
    public String checkInTime;
    public String checkOutTime;
    public String status;

    public ParkingTicket() {
    }

    public ParkingTicket(String plateNumber) {
        this.plateNumber = plateNumber;
    }
}