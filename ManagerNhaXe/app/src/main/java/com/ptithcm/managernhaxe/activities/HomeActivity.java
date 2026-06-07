package com.ptithcm.managernhaxe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.utils.SharedPrefManager;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefManager.init(this);
        setContentView(R.layout.activity_home);

        String role = SharedPrefManager.getRole();

        go(R.id.btnVehicle, VehicleActivity.class);
        go(R.id.btnCheckIn, CheckInActivity.class);
        go(R.id.btnCheckOut, CheckOutActivity.class);
        go(R.id.btnHistory, HistoryActivity.class);
        go(R.id.btnProfile, ProfileActivity.class);

        if (!"ADMIN".equalsIgnoreCase(role)) {
            findViewById(R.id.btnVehicle).setVisibility(View.GONE);
            findViewById(R.id.btnCheckIn).setVisibility(View.GONE);
            findViewById(R.id.btnCheckOut).setVisibility(View.GONE);
        }

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            SharedPrefManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void go(int id, Class<?> activityClass) {
        findViewById(id).setOnClickListener(v ->
                startActivity(new Intent(this, activityClass))
        );
    }
}