package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.installations.Utils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<Cart> list;

    FirebaseFirestore firestore ;
    int totalAmount = 0;
    int totalQuantity =0;
FirebaseAuth auth;

    public CartAdapter(Context context, List<Cart> list) {
        this.context = context;
        this.list = list;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.cart_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     Cart item = list.get(position);
        Glide.with(context).load(item.getAnh()).into(holder.imgga);
        holder.name.setText(item.getName());
        String formattedPrice = formatPrice(item.getPrice());
        holder.price.setText(formattedPrice);
        holder.totalQuantity.setText(item.getTotalQuantity()+"");
        holder.dele.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        firestore.collection("doan1").document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).
                collection("AddtoCart").document(list.get(position).getDocumentID()).
                delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            list.remove(list.get(position));
                            notifyItemRemoved(position);
                            // Gửi broadcast khi một mục đã được xóa thành công
                            Intent intent = new Intent("CartItemDeleted");
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }
                });
    }
}
        );
        totalAmount = totalAmount + list.get(position).getPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        totalQuantity = totalQuantity + Integer.parseInt(list.get(position).getTotalQuantity()+"");
        Intent i = new Intent("MyTotalQuantity");
        i.putExtra("totalquantity",totalQuantity);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgga;
        ImageButton dele;
        TextView name, price, totalQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgga = itemView.findViewById(R.id.ivCartItem);
            name = itemView.findViewById(R.id.tvCartNameItem);
            price = itemView.findViewById(R.id.tvCartPrice);
            totalQuantity = itemView.findViewById(R.id.tvCartSoLuong);
            dele = itemView.findViewById(R.id.btnXoaSP);

        }
    }
    private String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,### VNĐ");
        return decimalFormat.format(price);
    }
}
