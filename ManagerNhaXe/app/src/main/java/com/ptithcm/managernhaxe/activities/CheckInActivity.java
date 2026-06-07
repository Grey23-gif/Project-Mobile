package com.ptithcm.managernhaxe.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.adapter.SelectableVehicleAdapter;
import com.ptithcm.managernhaxe.model.ApiResponse;
import com.ptithcm.managernhaxe.model.ParkingTicket;
import com.ptithcm.managernhaxe.model.Vehicle;
import com.ptithcm.managernhaxe.repository.ParkingRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckInActivity extends AppCompatActivity {

    private final ParkingRepository repo = new ParkingRepository();
    private SelectableVehicleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        EditText edtSearch = findViewById(R.id.edtSearchVehicle);
        RecyclerView rv = findViewById(R.id.rvVehicles);

        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new SelectableVehicleAdapter(vehicle -> checkIn(vehicle));
        rv.setAdapter(adapter);

        loadVehicles();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadVehicles() {
        repo.vehicles().enqueue(new Callback<List<Vehicle>>() {
            @Override
            public void onResponse(Call<List<Vehicle>> call, Response<List<Vehicle>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Vehicle>> call, Throwable t) {
                Toast.makeText(CheckInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkIn(Vehicle vehicle) {
        ParkingTicket ticket = new ParkingTicket(vehicle.plateNumber);

        repo.checkIn(ticket).enqueue(new Callback<ApiResponse<ParkingTicket>>() {
            @Override
            public void onResponse(Call<ApiResponse<ParkingTicket>> call,
                                   Response<ApiResponse<ParkingTicket>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(
                            CheckInActivity.this,
                            response.body().message,
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(CheckInActivity.this, "Lỗi API: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ParkingTicket>> call, Throwable t) {
                Toast.makeText(CheckInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}