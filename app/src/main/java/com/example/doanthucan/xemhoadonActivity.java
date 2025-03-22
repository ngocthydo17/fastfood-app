package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.adapter.xemhoadonAdapter;
import com.example.doanthucan.model.XemHoaDonModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class xemhoadonActivity extends AppCompatActivity {
    RecyclerView rvlichsuhd;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    List<XemHoaDonModel> list11;
ImageView imglichsu;
    xemhoadonAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemhoadon);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        rvlichsuhd = findViewById(R.id.rvlichsuhd);
        rvlichsuhd.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list11 = new ArrayList<>();
        adapter = new xemhoadonAdapter(list11,this);
        rvlichsuhd.setAdapter(adapter);

        imglichsu=findViewById(R.id.imglichsu);
        imglichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firestore.collection("doan1").document(auth.getCurrentUser().getUid()).collection("Orders")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                XemHoaDonModel hoadon = doc.toObject(XemHoaDonModel.class);
                                list11.add(hoadon);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });



    }
}