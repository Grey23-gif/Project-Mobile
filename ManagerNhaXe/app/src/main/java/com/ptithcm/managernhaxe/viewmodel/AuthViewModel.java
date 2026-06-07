package com.ptithcm.managernhaxe.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ptithcm.managernhaxe.model.ApiResponse;
import com.ptithcm.managernhaxe.model.User;
import com.ptithcm.managernhaxe.repository.ParkingRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {

    private final ParkingRepository repo = new ParkingRepository();

    public MutableLiveData<ApiResponse<User>> result = new MutableLiveData<>();
    public MutableLiveData<String> error = new MutableLiveData<>();

    public void login(String email, String pass) {
        User user = new User();
        user.email = email;
        user.password = pass;

        repo.login(user).enqueue(callback());
    }

    public void register(String name, String email, String pass) {
        User user = new User(name, email, pass);
        repo.register(user).enqueue(callback());
    }

    private Callback<ApiResponse<User>> callback() {
        return new Callback<ApiResponse<User>>() {
            @Override
            public void onResponse(Call<ApiResponse<User>> call,
                                   Response<ApiResponse<User>> response) {

                if (response.isSuccessful()) {
                    result.setValue(response.body());
                } else {
                    error.setValue("Lỗi API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<User>> call, Throwable t) {
                error.setValue(t.getMessage());
            }
        };
    }
}