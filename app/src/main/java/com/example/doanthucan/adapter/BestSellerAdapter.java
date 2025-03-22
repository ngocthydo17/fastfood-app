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
import com.example.doanthucan.model.BestSeller;

import java.util.ArrayList;

public class BestSellerAdapter extends RecyclerView.Adapter<BestSellerAdapter.ViewHolder> {
    Activity context;
    ArrayList<BestSeller> arr_bestseller;
    public BestSellerAdapter(Activity context, ArrayList<BestSeller> arr_bestseller) {
        this.context=context;
        this.arr_bestseller=arr_bestseller;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= from(context);
        View viewBestSeller=layoutInflater.inflate(R.layout.itembestseller,parent,false);
        BestSellerAdapter.ViewHolder viewHolderBS=new BestSellerAdapter.ViewHolder(viewBestSeller);
        return viewHolderBS;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BestSeller bs=arr_bestseller.get(position);
        holder.ivHinh1.setImageResource(bs.getHinhsp());
        holder.txtTensp.setText(bs.getTensp()+"");
        holder.txtDongia.setText(bs.getGiasp()+"");
    }


    public int getItemCount() {
        return arr_bestseller.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivHinh1;
        TextView txtTensp, txtDongia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHinh1=itemView.findViewById(R.id.ivHinh1);
            txtTensp=itemView.findViewById(R.id.txtTensp);
            txtDongia=itemView.findViewById(R.id.txtDongia);
        }
    }
}
