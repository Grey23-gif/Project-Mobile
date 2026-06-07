package com.ptithcm.managernhaxe.repository;

import com.ptithcm.managernhaxe.api.RetrofitClient;
import com.ptithcm.managernhaxe.model.ApiResponse;
import com.ptithcm.managernhaxe.model.History;
import com.ptithcm.managernhaxe.model.ParkingTicket;
import com.ptithcm.managernhaxe.model.User;
import com.ptithcm.managernhaxe.model.Vehicle;

import java.util.List;

import retrofit2.Call;

public class ParkingRepository {

    public Call<ApiResponse<User>> login(User user) {
        return RetrofitClient.getApiService().login(user);
    }

    public Call<ApiResponse<User>> register(User user) {
        return RetrofitClient.getApiService().register(user);
    }

    public Call<List<Vehicle>> vehicles() {
        return RetrofitClient.getApiService().getVehicles();
    }

    public Call<Vehicle> addVehicle(Vehicle vehicle) {
        return RetrofitClient.getApiService().addVehicle(vehicle);
    }

    public Call<Vehicle> updateVehicle(int id, Vehicle vehicle) {
        return RetrofitClient.getApiService().updateVehicle(id, vehicle);
    }

    public Call<ApiResponse<Object>> deleteVehicle(int id) {
        return RetrofitClient.getApiService().deleteVehicle(id);
    }

    public Call<ApiResponse<ParkingTicket>> checkIn(ParkingTicket ticket) {
        return RetrofitClient.getApiService().checkIn(ticket);
    }

    public Call<ApiResponse<ParkingTicket>> checkOut(ParkingTicket ticket) {
        return RetrofitClient.getApiService().checkOut(ticket);
    }

    public Call<List<History>> history(String role, String email, String keyword) {
        return RetrofitClient.getApiService().history(role, email, keyword);
    }

    public Call<List<User>> users() {
        return RetrofitClient.getApiService().getUsers();
    }

    public Call<List<ParkingTicket>> activeTickets() {
        return RetrofitClient.getApiService().activeTickets();
    }
}