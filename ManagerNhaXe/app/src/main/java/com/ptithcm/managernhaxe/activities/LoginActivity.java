package com.ptithcm.managernhaxe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.utils.SharedPrefManager;
import com.ptithcm.managernhaxe.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    private AuthViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPrefManager.init(this);

        if (SharedPrefManager.getToken() != null && !SharedPrefManager.getToken().isEmpty()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);

        EditText email = findViewById(R.id.edtEmail);
        EditText pass = findViewById(R.id.edtPassword);

        vm = new ViewModelProvider(this).get(AuthViewModel.class);

        findViewById(R.id.btnLogin).setOnClickListener(v ->
                vm.login(
                        email.getText().toString().trim(),
                        pass.getText().toString().trim()
                )
        );

        findViewById(R.id.btnRegister).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class))
        );

        vm.result.observe(this, r -> {
            String token = r != null && r.token != null ? r.token : "demo-token";

            SharedPrefManager.saveToken(token);

            if (r != null && r.data != null) {
                SharedPrefManager.saveUser(r.data.name, r.data.email, r.data.role);
            }

            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });

        vm.error.observe(this, e ->
                Toast.makeText(this, e, Toast.LENGTH_SHORT).show()
        );
    }
}