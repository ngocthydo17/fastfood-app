package com.example.doanthucan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanthucan.R;
import com.example.doanthucan.model.Thanhtoan;

import java.text.DecimalFormat;
import java.util.List;

public  class ThanhtoanAdapter extends RecyclerView.Adapter<ThanhtoanAdapter.ViewHolder> {
    Context context;

    List<Thanhtoan> list;

    public ThanhtoanAdapter(Context context, List<Thanhtoan> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View userView = inflater.inflate(R.layout.payment_item, parent, false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }


    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Thanhtoan item = list.get(position);
        Glide.with(context).load(item.getAnh()).into(holder.ivThanhtoan);
        holder.name.setText(item.getName());
        String formattedPrice = formatPrice(item.getPrice());
        holder.price.setText(formattedPrice);
        holder.total.setText(item.getTotalQuantity());
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivThanhtoan;
        TextView name, price, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivThanhtoan = itemView.findViewById(R.id.ivThanhtoan);
            name = itemView.findViewById(R.id.tvNameThanhToan);
            price = itemView.findViewById(R.id.tvPriceThanhthoan);
            total = itemView.findViewById(R.id.tvQuantityTToan);
        }

    }
    private String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        return decimalFormat.format(price);
    }
}
