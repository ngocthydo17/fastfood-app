package com.example.doanthucan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.adapter.ThanhtoanAdapter;
import com.example.doanthucan.model.Thanhtoan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ThanhToanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Thanhtoan> thanhtoans;
    ThanhtoanAdapter thanhtoanAdapter;
    TextView tvtotalPrice;
    Button btnMuahang;
    int totalPrice;
    int totalQuantity;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String diachi1;

TextView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        btnBack=findViewById(R.id.PaymentBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnMuahang = findViewById(R.id.btnPay);
        tvtotalPrice = findViewById(R.id.tvPriceOfPayFooter);
        totalPrice = getIntent().getIntExtra("totalPriceFromCart",0);
        String totalGia = NumberFormat.getNumberInstance(Locale.US).format(totalPrice);
        tvtotalPrice.setText(totalGia+ "VNĐ");
        totalQuantity = getIntent().getIntExtra("totalQuantityFromCart",1);
        recyclerView = findViewById(R.id.rvThanhToan);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        thanhtoans = new ArrayList<>();
        thanhtoanAdapter = new ThanhtoanAdapter(this,thanhtoans);
        recyclerView.setAdapter(thanhtoanAdapter);
        firestore.collection("doan1").document(auth.getCurrentUser().getUid()).collection("AddtoCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot doc:task.getResult().getDocuments()){
                        Thanhtoan thanhtoan = doc.toObject(Thanhtoan.class);
                        thanhtoans.add(thanhtoan);
                        thanhtoanAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
        btnMuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placedOrder();
            }


        });
    }
    private  void clearCart(){
        CollectionReference cart = firestore.collection("doan1").document(Objects.requireNonNull(auth.getCurrentUser()).getUid()).
                collection("AddtoCart");
        cart.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots){
                    doc.getReference().delete();
                }
            }
        });
    }
    private void placedOrder() {
        String saveCurrentDate, saveCurrentTime;
        Calendar callForDate = Calendar.getInstance();
        Calendar callForTime = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForTime.getTime());

//        if(thanhToanModelList != null && thanhToanModelList.size() > 0){
//            for(ThanhToanModel model : thanhToanModelList){


        if(thanhtoans!= null && thanhtoans.size() > 0){
            for(Thanhtoan model : thanhtoans){
                Map<String, Object> infoorder = new HashMap<>();
                infoorder.put("Tên", model.getName());
                infoorder.put("Số lượng", model.getTotalQuantity());
                infoorder.put("Giá", model.getPrice());

                CollectionReference OrderInfoCollectionReference = firestore.collection("doan1").document(auth.getCurrentUser().getUid())
                        .collection("Orders").document("Order Info").collection("Order Info");
                OrderInfoCollectionReference.add(infoorder);
            }
        }

        Map<String, Object> dateInfo = new HashMap<>();
        dateInfo.put("Số lượng", totalQuantity);
        dateInfo.put("price1",totalPrice);
        dateInfo.put("ngay", saveCurrentDate);
        dateInfo.put("gio", saveCurrentTime);
        dateInfo.put("timestamp", FieldValue.serverTimestamp());
        CollectionReference OrderscollectionReference = firestore.collection("doan1").document(auth.getCurrentUser().getUid())
                .collection("Orders");
        OrderscollectionReference.add(dateInfo).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Intent i = new Intent(ThanhToanActivity.this, AddressActivity.class);
                    startActivity(i);
                    clearCart();
                }
            }
        });

    }
}