package com.ptithcm.managernhaxe.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptithcm.managernhaxe.R;
import com.ptithcm.managernhaxe.model.History;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.VH> {

    private List<History> list = new ArrayList<>();

    public void setData(List<History> data) {
        list = data == null ? new ArrayList<>() : data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        History history = list.get(position);

        holder.txtHistory.setText(
                history.plateNumber
                        + "\nVào: " + formatDate(history.checkInTime)
                        + "\nRa: " + formatDate(history.checkOutTime)
                        + "\nPhí: " + history.totalFee + " VNĐ"
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VH extends RecyclerView.ViewHolder {

        TextView txtHistory;

        public VH(@NonNull View itemView) {
            super(itemView);
            txtHistory = itemView.findViewById(R.id.txtHistory);
        }
    }

    private String formatDate(String date) {
        try {
            LocalDateTime time = LocalDateTime.parse(date);

            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            return time.format(formatter);
        } catch (Exception e) {
            return date;
        }
    }
}