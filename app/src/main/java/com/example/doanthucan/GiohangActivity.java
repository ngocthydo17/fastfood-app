package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class GiohangActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Cart> carts;
    CartAdapter cartAdapter;
    int totalbill;
    int totalQuantity;
    TextView Amount;
    Button btnThanhtoan;
    TextView Btnback,btnHome;
    ImageView backgiohang,homegiohang;
    Toolbar toolbar;
    ImageView imvCart;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private BroadcastReceiver cartItemDeletedReceiver;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Amount = findViewById(R.id.tvCartTotalPrice);
//        Btnback = findViewById(R.id.iconCartBack);
//        btnHome = findViewById(R.id.iconCartHome);
        btnThanhtoan = (Button) findViewById(R.id.btnCartThanhtoan);
//        Btnback.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//                Intent i = new Intent(GiohangActivity.this,MainFragmentActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
        imvCart = findViewById(R.id.imvCart);

        cartItemDeletedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Cập nhật giá tổng khi nhận được sự kiện xóa mục
                updateTotalPrice();
                // Cập nhật trạng thái của giỏ hàng
                updateEmptyCartMessage();
            }
        };
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        LocalBroadcastManager.getInstance(this).registerReceiver(MessReceiver, new IntentFilter("MyTotalAmount"));
        LocalBroadcastManager.getInstance(this).registerReceiver(messReceiver,new IntentFilter("MyTotalQuantity"));
        recyclerView = findViewById(R.id.rcCart);recyclerView.setLayoutManager( new LinearLayoutManager(this));
        carts = new ArrayList<>();
        cartAdapter = new  CartAdapter(this, carts);
        recyclerView.setAdapter(cartAdapter);
        updateCart();
        btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GiohangActivity.this,ThanhToanActivity.class);
                i.putExtra("totalPriceFromCart",totalbill);
                i.putExtra("totalQuantityFromCart",totalQuantity);
                startActivity(i);

            }
        });


        toolbar = findViewById(R.id.toolbargiohang);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.hideOverflowMenu();

        backgiohang = findViewById(R.id.backgiohang);
        backgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent img = new Intent(GiohangActivity.this, ViewAllActivity.class);
//                startActivity(img);
                finish();
            }
        });

        homegiohang = findViewById(R.id.homegiohang);
        homegiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img = new Intent(GiohangActivity.this, MainFragmentActivity.class);
                startActivity(img);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Đăng ký BroadcastReceiver
        LocalBroadcastManager.getInstance(this).registerReceiver(cartItemDeletedReceiver, new IntentFilter("CartItemDeleted"));

        // Cập nhật giá tổng lần đầu tiên khi onResume()
        updateTotalPrice();
        // Cập nhật thông báo giỏ hàng trống
        updateEmptyCartMessage();
    }
    @Override
    protected void onPause() {super.onPause();
        // Hủy đăng ký BroadcastReceiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(cartItemDeletedReceiver);
    }
    private void updateTotalPrice() {
        // Tính toán lại giá tổng và cập nhật lên giao diện
        // Ví dụ:
        int totalPrice = calculateTotalPrice(); // Hàm tính toán giá tổng
        String totalGia = NumberFormat.getNumberInstance(Locale.US).format(totalPrice);
        Amount.setText(totalGia + "VNĐ"); // Hiển thị giá tổng lên TextView
    }
    private int calculateTotalPrice() {
        // Tính toán giá tổng dựa trên danh sách giỏ hàng
        int totalPrice = 0;
        for (Cart cartItem : carts) {
            totalPrice += cartItem.getPrice() * Integer.parseInt(cartItem.getTotalQuantity());
        }
        return totalPrice;
    }
    public BroadcastReceiver MessReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalbill = intent.getIntExtra("totalAmount",0);
            String totalGia = NumberFormat.getNumberInstance(Locale.US).format(totalbill);
            Amount.setText(totalGia + "VNĐ");
        }
    };
    public  BroadcastReceiver messReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            totalQuantity = intent.getIntExtra("totalQuantity",1);
        }
    };
    private void updateCart() {
        // Cập nhật danh sách giỏ hàng
        firestore.collection("doan1").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .collection("AddtoCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            carts.clear();
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                String documentID = doc.getId();
                                Cart cart = doc.toObject(Cart.class);
                                assert cart != null;
                                cart.setDocumentID(documentID);
                                carts.add(cart);
                            }
                            cartAdapter.notifyDataSetChanged();
                            // Cập nhật thông báo giỏ hàng trống
                            updateEmptyCartMessage();
                        }
                    }
                });
    }
    private void updateEmptyCartMessage() {
        if (carts.isEmpty()) {
            imvCart.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            imvCart.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}