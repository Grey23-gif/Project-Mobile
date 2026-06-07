package com.ptithcm.managernhaxe.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.adapter.VehicleAdapter;
import com.ptithcm.managernhaxe.model.ApiResponse;
import com.ptithcm.managernhaxe.model.Vehicle;
import com.ptithcm.managernhaxe.repository.ParkingRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVehicleActivity extends AppCompatActivity {

    private RecyclerView rvVehicles;
    private VehicleAdapter adapter;

    private final ParkingRepository repo = new ParkingRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vehicle);

        rvVehicles = findViewById(R.id.rvVehicles);
        rvVehicles.setLayoutManager(new LinearLayoutManager(this));

        adapter = new VehicleAdapter(new VehicleAdapter.Listener() {
            @Override
            public void onEdit(Vehicle vehicle) {
                showEditDialog(vehicle);
            }

            @Override
            public void onDelete(Vehicle vehicle) {
                deleteVehicle(vehicle);
            }
        });

        rvVehicles.setAdapter(adapter);

        loadVehicles();
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
                Toast.makeText(UpdateVehicleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showEditDialog(Vehicle vehicle) {
        android.view.View view = getLayoutInflater().inflate(R.layout.dialog_edit_vehicle, null);

        EditText edtPlate = view.findViewById(R.id.edtPlate);
        EditText edtType = view.findViewById(R.id.edtType);
        EditText edtOwner = view.findViewById(R.id.edtOwner);

        edtPlate.setText(vehicle.plateNumber);
        edtType.setText(vehicle.type);
        edtOwner.setText(vehicle.ownerName);

        new AlertDialog.Builder(this)
                .setTitle("Cập nhật xe")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    Vehicle updated = new Vehicle(
                            edtPlate.getText().toString().trim(),
                            edtType.getText().toString().trim(),
                            edtOwner.getText().toString().trim()
                    );

                    repo.updateVehicle(vehicle.id, updated).enqueue(new Callback<Vehicle>() {
                        @Override
                        public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(UpdateVehicleActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                loadVehicles();
                            }
                        }

                        @Override
                        public void onFailure(Call<Vehicle> call, Throwable t) {
                            Toast.makeText(UpdateVehicleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteVehicle(Vehicle vehicle) {
        repo.deleteVehicle(vehicle.id).enqueue(new Callback<ApiResponse<Object>>() {
            @Override
            public void onResponse(Call<ApiResponse<Object>> call, Response<ApiResponse<Object>> response) {
                Toast.makeText(UpdateVehicleActivity.this, "Đã xóa xe", Toast.LENGTH_SHORT).show();
                loadVehicles();
            }

            @Override
            public void onFailure(Call<ApiResponse<Object>> call, Throwable t) {
                Toast.makeText(UpdateVehicleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}