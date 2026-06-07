package com.ptithcm.managernhaxe.activities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.viewmodel.AuthViewModel;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        AuthViewModel vm = new ViewModelProvider(this).get(AuthViewModel.class);

        EditText name = findViewById(R.id.edtName);
        EditText email = findViewById(R.id.edtEmail);
        EditText pass = findViewById(R.id.edtPassword);

        findViewById(R.id.btnRegister).setOnClickListener(v ->
                vm.register(
                        name.getText().toString().trim(),
                        email.getText().toString().trim(),
                        pass.getText().toString().trim()
                )
        );

        vm.result.observe(this, r -> {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            finish();
        });

        vm.error.observe(this, e ->
                Toast.makeText(this, e, Toast.LENGTH_SHORT).show()
        );
    }
}