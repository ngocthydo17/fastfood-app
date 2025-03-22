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
import com.example.doanthucan.Product;
import com.example.doanthucan.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductFragmentAdapter extends RecyclerView.Adapter<ProductFragmentAdapter.ProductViewHolder> {
    ArrayList<Product> lstProduct;
    Context context;
    ProductFragmentAdapter.ProductCallback productCallBack;
    public ProductFragmentAdapter(ArrayList<Product> lstProduct, ProductFragmentAdapter.ProductCallback productCallBack) {
        this.lstProduct = lstProduct;
        this.productCallBack = productCallBack;
    }
    @NonNull
    @Override
    public ProductFragmentAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Nạp layout cho View biểu diễn phần tử user
        View userView = inflater.inflate(R.layout.productfragmentitem, parent, false);
        //
        return new ProductFragmentAdapter.ProductViewHolder(userView);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductFragmentAdapter.ProductViewHolder holder, int position) {
        // lấy item của dữ liệu
        Product item = lstProduct.get(position);
        // gán item  của view
        holder.textViewNameProduct.setText(item.getName());
//        holder.textViewPriceProduct.setText(item.getPrice()+"");
        String formattedPrice = formatPrice(item.getPrice());
        holder.textViewPriceProduct.setText(formattedPrice);

        Glide.with(context).load(item.getImage()).into(holder.imageViewProduct);
        // lấy sự kiện
        holder.itemView.setOnClickListener(view -> productCallBack.onItemClick(item.getName(),item.getPrice(),item.getCategory(),item.getImage()));
    }

    @Override
    public int getItemCount() {
        return lstProduct.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNameProduct, textViewPriceProduct;
        private ImageView imageViewProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameProduct = itemView.findViewById(R.id.edtTenSP1);
            textViewPriceProduct = itemView.findViewById(R.id.edtGiaSP1);
            imageViewProduct = itemView.findViewById(R.id.ivAvatar1);
        }
    }
    public interface ProductCallback {

        void onItemClick(String name, int price, String mota, String image);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void add(Product product) {
        lstProduct.add(product);
        notifyDataSetChanged();
    }
    private String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        return decimalFormat.format(price);
    }

}
