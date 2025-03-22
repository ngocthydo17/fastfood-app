package com.example.doanthucan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.adapter.FavoriteAdapter;
import com.example.doanthucan.model.ViewAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.ProductCallback {
    RecyclerView rvFav;
    ArrayList<ViewAll> lstProduct;
    FavoriteAdapter favoriteAdapter;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    ImageView imgbackFav, imgstoreFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        rvFav = findViewById(R.id.rvFav);
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        lstProduct = new ArrayList<>();
        favoriteAdapter = new FavoriteAdapter(lstProduct, this, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FavoriteActivity.this, 2);
        rvFav.setLayoutManager(gridLayoutManager);
        rvFav.setAdapter(favoriteAdapter);
        firestore.collection("FAVOURITES").whereEqualTo("user",user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ViewAll viewAll = document.toObject(ViewAll.class);
                                lstProduct.add(viewAll);
                            }
                            favoriteAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(FavoriteActivity.this, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        toolbar = findViewById(R.id.toolbarFav);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        imgbackFav=findViewById(R.id.backFav);
        imgbackFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgstoreFav=findViewById(R.id.storeFav);
        imgstoreFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(FavoriteActivity.this,GiohangActivity.class);
                startActivity(i);
            }
        });



    }





    @Override
    public void onItemClick(String ten, int gia, String category, String anh) {
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("ten", ten);
        i.putExtra("gia", gia);
        i.putExtra("loai", category);
        i.putExtra("anh", anh);
        startActivity(i);
    }
}