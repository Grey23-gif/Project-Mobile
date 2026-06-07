package com.ptithcm.managernhaxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class SelectableVehicleAdapter extends RecyclerView.Adapter<SelectableVehicleAdapter.VH> {

    public interface Listener {
        void onSelect(Vehicle vehicle);
    }

    private final Listener listener;
    private List<Vehicle> list = new ArrayList<>();
    private List<Vehicle> fullList = new ArrayList<>();

    public SelectableVehicleAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setData(List<Vehicle> data) {
        fullList = data == null ? new ArrayList<>() : data;
        list = new ArrayList<>(fullList);
        notifyDataSetChanged();
    }

    public void filter(String keyword) {
        list.clear();

        if (keyword == null || keyword.trim().isEmpty()) {
            list.addAll(fullList);
        } else {
            String key = keyword.toLowerCase().trim();

            for (Vehicle v : fullList) {
                String plate = v.plateNumber == null ? "" : v.plateNumber.toLowerCase();
                String owner = v.ownerName == null ? "" : v.ownerName.toLowerCase();
                String type = v.type == null ? "" : v.type.toLowerCase();

                if (plate.contains(key) || owner.contains(key) || type.contains(key)) {
                    list.add(v);
                }
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_select_vehicle, parent, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Vehicle vehicle = list.get(position);

        holder.txtVehicle.setText(
                vehicle.plateNumber + " - " + vehicle.type
                        + "\nChủ xe: " + vehicle.ownerName
        );

        holder.btnSelect.setText("Chọn gửi xe");

        holder.btnSelect.setOnClickListener(v -> {
            if (listener != null) {
                listener.onSelect(vehicle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView txtVehicle;
        Button btnSelect;

        public VH(@NonNull View itemView) {
            super(itemView);
            txtVehicle = itemView.findViewById(R.id.txtVehicle);
            btnSelect = itemView.findViewById(R.id.btnSelect);
        }
    }
}