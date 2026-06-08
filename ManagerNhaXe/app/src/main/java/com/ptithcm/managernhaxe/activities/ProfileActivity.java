package com.ptithcm.managernhaxe.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefManager.init(this);
        setContentView(R.layout.activity_profile);

        TextView txtInfo = findViewById(R.id.txtInfo);

        txtInfo.setText(
                getString(
                        R.string.profile_info,
                        SharedPrefManager.getName(),
                        SharedPrefManager.getEmail(),
                        SharedPrefManager.getRole()
                )
        );
    }
}