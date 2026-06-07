package com.ptithcm.managernhaxe.activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.adapter.ActiveTicketAdapter;
import com.ptithcm.managernhaxe.model.ApiResponse;
import com.ptithcm.managernhaxe.model.ParkingTicket;
import com.ptithcm.managernhaxe.repository.ParkingRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    private final ParkingRepository repo = new ParkingRepository();
    private ActiveTicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        EditText edtSearch = findViewById(R.id.edtSearchTicket);
        RecyclerView rv = findViewById(R.id.rvActiveTickets);

        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new ActiveTicketAdapter(ticket -> checkOut(ticket));
        rv.setAdapter(adapter);

        loadActiveTickets();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void loadActiveTickets() {
        repo.activeTickets().enqueue(new Callback<List<ParkingTicket>>() {
            @Override
            public void onResponse(Call<List<ParkingTicket>> call,
                                   Response<List<ParkingTicket>> response) {

                if (response.isSuccessful()) {
                    adapter.setData(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ParkingTicket>> call, Throwable t) {
                Toast.makeText(CheckOutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkOut(ParkingTicket ticket) {
        repo.checkOut(new ParkingTicket(ticket.plateNumber))
                .enqueue(new Callback<ApiResponse<ParkingTicket>>() {
                    @Override
                    public void onResponse(Call<ApiResponse<ParkingTicket>> call,
                                           Response<ApiResponse<ParkingTicket>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(
                                    CheckOutActivity.this,
                                    response.body().message,
                                    Toast.LENGTH_SHORT
                            ).show();

                            loadActiveTickets();
                        } else {
                            Toast.makeText(CheckOutActivity.this, "Lỗi API: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse<ParkingTicket>> call, Throwable t) {
                        Toast.makeText(CheckOutActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}