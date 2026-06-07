package com.ptithcm.managernhaxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.model.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VH> {

    public interface Listener {
        void onEdit(Vehicle vehicle);
        void onDelete(Vehicle vehicle);
    }

    private List<Vehicle> list = new ArrayList<>();
    private final Listener listener;

    public VehicleAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setData(List<Vehicle> data) {
        list = data == null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vehicle, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Vehicle vehicle = list.get(position);

        holder.txtVehicle.setText(
                holder.itemView.getContext().getString(
                        R.string.vehicle_item,
                        vehicle.plateNumber,
                        vehicle.type,
                        vehicle.ownerName
                )
        );

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(vehicle);
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDelete(vehicle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView txtVehicle;
        Button btnEdit, btnDelete;

        public VH(@NonNull View itemView) {
            super(itemView);
            txtVehicle = itemView.findViewById(R.id.txtVehicle);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}