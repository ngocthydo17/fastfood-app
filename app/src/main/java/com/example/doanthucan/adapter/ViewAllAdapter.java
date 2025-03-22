package com.example.doanthucan.adapter;

import android.annotation.SuppressLint;
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
import com.example.doanthucan.model.ViewAll;

import java.text.DecimalFormat;
import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    private Context context;
    private List<ViewAll> list;
    ProductCallback productCallBack;

    public ViewAllAdapter(List<ViewAll> list,Context context, ProductCallback productCallBack) {
        this.context=context;
        this.list = list;
        this.productCallBack = productCallBack;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layoutitemgrid,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        ViewAll item=list.get(position);

        holder.name.setText(list.get(position).getName());
        holder.price.setText(list.get(position).getPrice()+"");

        String formattedPrice = formatPrice(item.getPrice());
        holder.price.setText(formattedPrice);
        Glide.with(context).load(item.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(view -> productCallBack.onItemClick(item.getName(),item.getPrice(),item.getCategory(),item.getImage()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ivAvatar);
            name=itemView.findViewById(R.id.edtTenSP);
            price=itemView.findViewById(R.id.edtGiaSP);
        }
    }

    public interface ProductCallback {

        void onItemClick(String ten, int price, String mota, String anh);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void add(ViewAll viewAll) {
        list.add(viewAll);
        notifyDataSetChanged();
    }
    private String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        return decimalFormat.format(price);
    }
}
