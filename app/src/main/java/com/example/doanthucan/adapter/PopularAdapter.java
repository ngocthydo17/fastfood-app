package com.example.doanthucan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.doanthucan.DetailActivity;
import com.example.doanthucan.R;
import com.example.doanthucan.ViewAllActivity;
import com.example.doanthucan.model.PopularProduct;

import java.text.DecimalFormat;
import java.util.List;

public class PopularAdapter  extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    Context context;
    List<PopularProduct> popularProductList;
    private ProductClickListener productClickListener;
    public PopularAdapter(Context context, List<PopularProduct> popularProductList) {
        this.context = context;
        this.popularProductList = popularProductList;
    }
    public void setProductClickListener(ProductClickListener listener) {
        this.productClickListener = listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popularproduct_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(popularProductList.get(position).getImage()).into(holder.popImg);
        holder.name.setText(popularProductList.get(position).getName());
        holder.price.setText(popularProductList.get(position).getPrice()+"");

        String formattedPrice = formatPrice(popularProductList.get(position).getPrice());
        holder.price.setText(formattedPrice);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Gửi thông tin sản phẩm được chọn tới HomeFragment
                if (productClickListener != null) {
                    productClickListener.onProductClick(popularProductList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularProductList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImg;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            popImg=itemView.findViewById(R.id.bg_img_product);
            name=itemView.findViewById(R.id.tv_product_name);
            price=itemView.findViewById(R.id.tv_product_price);

        }
    }

    private String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        return decimalFormat.format(price);
    }
    public interface ProductClickListener {
        void onProductClick(PopularProduct product);
    }
}
