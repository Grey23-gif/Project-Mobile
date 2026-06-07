package com.ptithcm.managernhaxe.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptithcm.managernhaxe.model.ApiResponse;
import com.ptithcm.managernhaxe.model.Vehicle;
import com.ptithcm.managernhaxe.repository.ParkingRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleViewModel extends ViewModel {

    private final ParkingRepository repo = new ParkingRepository();

    public MutableLiveData<List<Vehicle>> vehicles = new MutableLiveData<>();
    public MutableLiveData<String> msg = new MutableLiveData<>();

    public void load() {
        repo.vehicles().enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call,
                                   Response<List<Vehicle>> response) {

                if (response.isSuccessful()) {
                    vehicles.setValue(response.body());
                } else {
                    msg.setValue("Lỗi tải danh sách xe: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                msg.setValue(t.getMessage());
            }
        });
    }

    public void add(String plate, String type, String owner) {
        if (plate == null || plate.trim().isEmpty()) {
            msg.setValue("Vui lòng nhập biển số xe");
            return;
        }

        Vehicle vehicle = new Vehicle(
                plate.trim(),
                type.trim(),
                owner.trim()
        );

        repo.addVehicle(vehicle).enqueue(simpleCallback("Thêm xe thành công"));
    }

    public void update(int id, String plate, String type, String owner) {
        Vehicle vehicle = new Vehicle(
                plate.trim(),
                type.trim(),
                owner.trim()
        );

        repo.updateVehicle(id, vehicle).enqueue(simpleCallback("Cập nhật xe thành công"));
    }

    public void delete(int id) {
        repo.deleteVehicle(id).enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call,
                                   Response<ApiResponse<Object>> response) {

                if (response.isSuccessful()) {
                    msg.setValue("Đã xóa xe");
                    load();
                } else {
                    msg.setValue("Lỗi xóa xe: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                msg.setValue(t.getMessage());
            }
        });
    }

    private Callback<Vehicle> simpleCallback(String successMessage) {
        return new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call,
                                   Response<Vehicle> response) {

                if (response.isSuccessful()) {
                    msg.setValue(successMessage);
                    load();
                } else {
                    msg.setValue("Lỗi API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                msg.setValue(t.getMessage());
            }
        };
    }
}