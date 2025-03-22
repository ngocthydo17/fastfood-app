package com.example.doanthucan.adapter;

import static android.view.LayoutInflater.from;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.R;
import com.example.doanthucan.model.SanPham;

import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    Activity context;
    ArrayList<SanPham> arr_sanpham;

    public SanPhamAdapter(Activity context, ArrayList<SanPham> arr_sanpham) {
        this.context=context;
        this.arr_sanpham=arr_sanpham;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= from(context);
        View viewSanpham=layoutInflater.inflate(R.layout.items,parent,false);
        ViewHolder viewHolderSP=new ViewHolder(viewSanpham);
        return viewHolderSP;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sp=arr_sanpham.get(position);
        holder.ivHinh.setImageResource(sp.getHinhsp());
        holder.txtkhuyenmai.setText(sp.getTensp()+"");

    }

    public int getItemCount() {
        return arr_sanpham.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHinh;
        TextView txtkhuyenmai;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinh=itemView.findViewById(R.id.ivHinh1);
            txtkhuyenmai=itemView.findViewById(R.id.txtKhuyenmai);
        }
    }
}
