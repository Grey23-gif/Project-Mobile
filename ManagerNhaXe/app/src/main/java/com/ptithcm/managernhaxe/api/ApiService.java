package com.ptithcm.managernhaxe.api;

import com.ptithcm.managernhaxe.model.ApiResponse;
import com.ptithcm.managernhaxe.model.History;
import com.ptithcm.managernhaxe.model.ParkingTicket;
import com.ptithcm.managernhaxe.model.User;
import com.ptithcm.managernhaxe.model.Vehicle;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @POST("api/auth/login")
    Call<ApiResponse<User>> login(@Body User user);

    @POST("api/auth/register")
    Call<ApiResponse<User>> register(@Body User user);

    @GET("api/auth/users")
    Call<List<User>> getUsers();

    @GET("api/vehicles")
    Call<List<Vehicle>> getVehicles();

    @POST("api/vehicles")
    Call<Vehicle> addVehicle(@Body Vehicle vehicle);

    @PUT("api/vehicles/{id}")
    Call<Vehicle> updateVehicle(@Path("id") int id, @Body Vehicle vehicle);

    @DELETE("api/vehicles/{id}")
    Call<ApiResponse<Object>> deleteVehicle(@Path("id") int id);

    @POST("api/parking/checkin")
    Call<ApiResponse<ParkingTicket>> checkIn(@Body ParkingTicket ticket);

    @POST("api/parking/checkout")
    Call<ApiResponse<ParkingTicket>> checkOut(@Body ParkingTicket ticket);

    @GET("api/parking/history")
    Call<List<History>> history(
            @Query("role") String role,
            @Query("email") String email,
            @Query("keyword") String keyword
    );
    @GET("api/parking/active")
    Call<List<ParkingTicket>> activeTickets();
}