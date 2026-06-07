package com.ptithcm.managernhaxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.model.ParkingTicket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActiveTicketAdapter extends RecyclerView.Adapter<ActiveTicketAdapter.VH> {

    public interface Listener {
        void onCheckout(ParkingTicket ticket);
    }

    private final Listener listener;
    private List<ParkingTicket> list = new ArrayList<>();
    private List<ParkingTicket> fullList = new ArrayList<>();

    public ActiveTicketAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setData(List<ParkingTicket> data) {
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

            for (ParkingTicket t : fullList) {
                String plate = t.plateNumber == null ? "" : t.plateNumber.toLowerCase();

                if (plate.contains(key)) {
                    list.add(t);
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
                .inflate(R.layout.item_active_ticket, parent, false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        ParkingTicket ticket = list.get(position);

        holder.txtTicket.setText(
                "Biển số: " + ticket.plateNumber
                        + "\nGiờ vào: " + formatDate(ticket.checkInTime)
                        + "\nTrạng thái: " + ticket.status
        );

        holder.btnCheckout.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCheckout(ticket);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView txtTicket;
        Button btnCheckout;

        public VH(@NonNull View itemView) {
            super(itemView);
            txtTicket = itemView.findViewById(R.id.txtTicket);
            btnCheckout = itemView.findViewById(R.id.btnCheckout);
        }
    }

    private String formatDate(String date) {
        try {
            LocalDateTime time = LocalDateTime.parse(date);

            return time.format(
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
            );
        } catch (Exception e) {
            return date;
        }
    }
}