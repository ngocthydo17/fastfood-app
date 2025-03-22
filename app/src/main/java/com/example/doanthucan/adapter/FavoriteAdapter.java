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
import com.example.doanthucan.model.ViewAll;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    ArrayList<ViewAll> listFav;
    Context context;
    ProductCallback productCallBack;

    public FavoriteAdapter(ArrayList<ViewAll> listFav,Context context,  ProductCallback productCallBack) {
        this.context=context;
        this.listFav = listFav;
        this.productCallBack = productCallBack;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fav,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        // lấy item của dữ liệu
        ViewAll item = listFav.get(position);
        holder.txtname.setText(item.getName());

        String formattedPrice = formatPrice(item.getPrice());
        holder.price.setText(formattedPrice);
        Glide.with(context).load(item.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(view -> productCallBack.onItemClick(item.getName(),item.getPrice(),item.getCategory(),item.getImage()));
    }

    @Override
    public int getItemCount() {
        return listFav.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtname,price;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtname=itemView.findViewById(R.id.txtNameFav);
            price=itemView.findViewById(R.id.txtPriceFav);
            imageView=itemView.findViewById(R.id.imvFav);

        }
    }

    public interface ProductCallback {
        void onItemClick(String ten,int gia,String category,String anh);
    }
    public void add(ViewAll product) {
        listFav.add(product);
        notifyDataSetChanged();
    }
    private String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        return decimalFormat.format(price);
    }
}
