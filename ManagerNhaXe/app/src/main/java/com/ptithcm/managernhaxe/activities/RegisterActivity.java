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

        findViewById(R.id.btnRegister).setOnClickListener(v -> {
            String nameText = name.getText().toString().trim();
            String emailText = email.getText().toString().trim();
            String passText = pass.getText().toString().trim();

            if (nameText.isEmpty()) {
                name.setError("Vui lòng nhập họ tên");
                name.requestFocus();
                return;
            }

            if (nameText.length() < 3) {
                name.setError("Họ tên phải từ 3 ký tự");
                name.requestFocus();
                return;
            }

            if (emailText.isEmpty()) {
                email.setError("Vui lòng nhập email");
                email.requestFocus();
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                email.setError("Email không đúng định dạng");
                email.requestFocus();
                return;
            }

            if (passText.isEmpty()) {
                pass.setError("Vui lòng nhập mật khẩu");
                pass.requestFocus();
                return;
            }

            if (passText.length() < 6) {
                pass.setError("Mật khẩu phải từ 6 ký tự");
                pass.requestFocus();
                return;
            }

            vm.register(nameText, emailText, passText);
        });

        vm.result.observe(this, r -> {
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            finish();
        });

        vm.error.observe(this, e ->
                Toast.makeText(this, e, Toast.LENGTH_SHORT).show()
        );
    }
}