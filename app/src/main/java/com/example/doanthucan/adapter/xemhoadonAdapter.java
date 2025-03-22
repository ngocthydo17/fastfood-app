package com.example.doanthucan.adapter;

import static android.view.LayoutInflater.from;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.R;
import com.example.doanthucan.model.XemHoaDonModel;

import java.text.DecimalFormat;
import java.util.List;

public class xemhoadonAdapter extends RecyclerView.Adapter<xemhoadonAdapter.ViewHolder> {
    List<XemHoaDonModel> list;
    Context context;

    public xemhoadonAdapter(List<XemHoaDonModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= from(context);
        View viewSanpham=layoutInflater.inflate(R.layout.item_xemhoadon,parent,false);
        ViewHolder models = new  ViewHolder(viewSanpham);
        return models;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        XemHoaDonModel item =list.get(position);
        String formattedPrice = formatPrice(item.getPrice1());
        holder.price.setText(formattedPrice);
        holder.date.setText(item.getNgay());
        holder.time.setText(item.getGio());

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView price,date,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.txtPriceHD);
            date=itemView.findViewById(R.id.txtDateHD);
            time=itemView.findViewById(R.id.txtGioHD);
        }
    }
    private String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        return decimalFormat.format(price);
    }
}