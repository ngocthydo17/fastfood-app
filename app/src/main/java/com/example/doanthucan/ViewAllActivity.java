package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.adapter.ViewAllAdapter;
import com.example.doanthucan.model.ViewAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewAllActivity extends AppCompatActivity implements ViewAllAdapter.ProductCallback {
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ViewAll> viewAllList;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    ImageView backview,homeview;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        firestore=FirebaseFirestore.getInstance();
        String cate=getIntent().getStringExtra("category");

        recyclerView=findViewById(R.id.view_all_rec);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(ViewAllActivity.this,2);
        viewAllList=new ArrayList<>();
        viewAllAdapter=new ViewAllAdapter(viewAllList,this,this);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(viewAllAdapter);

        //Lấy sản phẩm gà
        if(cate!=null && cate.equalsIgnoreCase("ga")) {
            firestore.collection("AllProducts").whereEqualTo("category", "ga")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                                viewAllList.add(viewAll);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        //Lấy sản phẩm mì
        else   if(cate!=null && cate.equalsIgnoreCase("mi")) {
            firestore.collection("AllProducts").whereEqualTo("category", "mi")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                                viewAllList.add(viewAll);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        //Lấy sản phẩm nước
        else  if(cate!=null && cate.equalsIgnoreCase("nuoc")) {
            firestore.collection("AllProducts").whereEqualTo("category", "nuoc")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                                viewAllList.add(viewAll);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        } //Lấy sản phẩm khác
        else if(cate!=null && cate.equalsIgnoreCase("khac")) {
            firestore.collection("AllProducts").whereEqualTo("category", "khac")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                                viewAllList.add(viewAll);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        else if(cate!=null && cate.equalsIgnoreCase("combo")) {
            firestore.collection("AllProducts").whereEqualTo("category", "combo")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                                viewAllList.add(viewAll);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        else if(cate!=null && cate.equalsIgnoreCase("burger")) {
            firestore.collection("AllProducts").whereEqualTo("category", "burger")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                                viewAllList.add(viewAll);
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }
        else {
            firestore.collection("AllProducts")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    // Xử lý dữ liệu ở đây
                                    // Ví dụ:
                                    ViewAll viewAll = documentSnapshot.toObject(ViewAll.class);
                                    viewAllList.add(viewAll);
                                }
                                // Cập nhật giao diện nếu cần
                                viewAllAdapter.notifyDataSetChanged();
                            }
                        }
                    });
        }

        toolbar = findViewById(R.id.toolbarview);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.hideOverflowMenu();

        backview = findViewById(R.id.backview);
        backview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        homeview = findViewById(R.id.homeview);
        homeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img = new Intent(ViewAllActivity.this, GiohangActivity.class);
                startActivity(img);
            }
        });

    }


    @Override
    public void onItemClick(String ten, int price, String anh,String category) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("gia",price);
        i.putExtra("anh",anh);
        i.putExtra("loai",category);
        startActivity(i);
    }
}