package com.ptithcm.managernhaxe.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.model.User;
import com.ptithcm.managernhaxe.model.Vehicle;
import com.ptithcm.managernhaxe.repository.ParkingRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText edtPlate, edtType, edtGuestOwner;
    private Spinner spinnerOwner;

    private final ParkingRepository repo = new ParkingRepository();

    private final List<String> ownerNames = new ArrayList<>();
    private final List<String> ownerEmails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        edtPlate = findViewById(R.id.edtPlate);
        edtType = findViewById(R.id.edtType);
        edtGuestOwner = findViewById(R.id.edtGuestOwner);
        spinnerOwner = findViewById(R.id.spinnerOwner);

        loadUsers();

        findViewById(R.id.btnSaveVehicle).setOnClickListener(v -> addVehicle());
    }

    private void loadUsers() {
        ownerNames.clear();
        ownerEmails.clear();

        ownerNames.add("Khách ngoài");
        ownerEmails.add("");

        repo.users().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (User user : response.body()) {
                        ownerNames.add(user.name + " - " + user.email);
                        ownerEmails.add(user.email);
                    }
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        AddVehicleActivity.this,
                        android.R.layout.simple_spinner_item,
                        ownerNames
                );
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerOwner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(AddVehicleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addVehicle() {
        String plate = edtPlate.getText().toString().trim();
        String type = edtType.getText().toString().trim();

        int pos = spinnerOwner.getSelectedItemPosition();

        String ownerName;

        if (pos == 0) {
            ownerName = edtGuestOwner.getText().toString().trim();

            if (ownerName.isEmpty()) {
                ownerName = "Khách ngoài";
            }
        } else {
            ownerName = ownerNames.get(pos);
        }

        if (plate.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập biển số xe", Toast.LENGTH_SHORT).show();
            return;
        }

        Vehicle vehicle = new Vehicle(plate, type, ownerName);

        repo.addVehicle(vehicle).enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddVehicleActivity.this, "Thêm xe thành công", Toast.LENGTH_SHORT).show();
                    edtPlate.setText("");
                    edtType.setText("");
                    edtGuestOwner.setText("");
                } else {
                    Toast.makeText(AddVehicleActivity.this, "Lỗi API: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {
                Toast.makeText(AddVehicleActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}