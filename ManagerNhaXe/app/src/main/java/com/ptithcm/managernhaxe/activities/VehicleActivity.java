package com.ptithcm.managernhaxe.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.managernhaxe.R;

public class VehicleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle);

        findViewById(R.id.btnAddVehiclePage).setOnClickListener(v ->
                startActivity(new Intent(this, AddVehicleActivity.class))
        );

        findViewById(R.id.btnUpdateVehiclePage).setOnClickListener(v ->
                startActivity(new Intent(this, UpdateVehicleActivity.class))
        );
    }
}