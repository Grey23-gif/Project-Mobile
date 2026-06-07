package com.ptithcm.managernhaxe.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.adapter.HistoryAdapter;
import com.ptithcm.managernhaxe.model.History;
import com.ptithcm.managernhaxe.repository.ParkingRepository;
import com.ptithcm.managernhaxe.utils.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private final ParkingRepository repo = new ParkingRepository();

    private HistoryAdapter adapter;
    private EditText edtSearchHistory;

    private String role;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        SharedPrefManager.init(this);

        role = SharedPrefManager.getRole();
        email = SharedPrefManager.getEmail();

        edtSearchHistory = findViewById(R.id.edtSearchHistory);

        RecyclerView rv = findViewById(R.id.rvHistory);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new HistoryAdapter();
        rv.setAdapter(adapter);

        if ("ADMIN".equalsIgnoreCase(role)) {
            edtSearchHistory.setVisibility(View.VISIBLE);
        } else {
            edtSearchHistory.setVisibility(View.GONE);
        }

        loadHistory("");

        edtSearchHistory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadHistory(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadHistory(String keyword) {
        repo.history(role, email, keyword).enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                } else {
                    Toast.makeText(HistoryActivity.this, "Lỗi API: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}