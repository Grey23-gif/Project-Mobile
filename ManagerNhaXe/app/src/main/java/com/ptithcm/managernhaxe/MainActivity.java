package com.ptithcm.managernhaxe;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ptithcm.managernhaxe.activities.HomeActivity;
import com.ptithcm.managernhaxe.activities.LoginActivity;
import com.ptithcm.managernhaxe.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefManager.init(this);

        Intent intent;

        if (SharedPrefManager.getToken().isEmpty()) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }

        startActivity(intent);
        finish();
    }
}