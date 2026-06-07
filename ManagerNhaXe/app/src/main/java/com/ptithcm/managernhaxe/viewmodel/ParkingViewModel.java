package com.ptithcm.managernhaxe.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptithcm.managernhaxe.model.ApiResponse;
import com.ptithcm.managernhaxe.model.History;
import com.ptithcm.managernhaxe.model.ParkingTicket;
import com.ptithcm.managernhaxe.repository.ParkingRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParkingViewModel extends ViewModel {

    private final ParkingRepository repo = new ParkingRepository();

    public MutableLiveData<String> msg = new MutableLiveData<>();
    public MutableLiveData<List<History>> histories = new MutableLiveData<>();

    public void checkIn(String plate) {
        if (plate == null || plate.trim().isEmpty()) {
            msg.setValue("Vui lòng nhập biển số xe");
            return;
        }

        ParkingTicket ticket = new ParkingTicket(plate.trim());
        repo.checkIn(ticket).enqueue(ticketCallback("Check-in thành công"));
    }

    public void checkOut(String plate) {
        if (plate == null || plate.trim().isEmpty()) {
            msg.setValue("Vui lòng nhập biển số xe");
            return;
        }

        ParkingTicket ticket = new ParkingTicket(plate.trim());
        repo.checkOut(ticket).enqueue(ticketCallback("Check-out thành công"));
    }

    public void loadHistory(String role, String email, String keyword) {
        repo.history(role, email, keyword).enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call,
                                   Response<List<History>> response) {

                if (response.isSuccessful()) {
                    histories.setValue(response.body());
                } else {
                    msg.setValue("Lỗi tải lịch sử: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                msg.setValue(t.getMessage());
            }
        });
    }

    private Callback<ApiResponse<ParkingTicket>> ticketCallback(String successMessage) {
        return new Callback<ApiResponse<ParkingTicket>>() {
            @Override
            public void onResponse(Call<ApiResponse<ParkingTicket>> call,
                                   Response<ApiResponse<ParkingTicket>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    msg.setValue(response.body().message);
                } else {
                    msg.setValue("Lỗi API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ParkingTicket>> call, Throwable t) {
                msg.setValue(t.getMessage());
            }
        };
    }
}