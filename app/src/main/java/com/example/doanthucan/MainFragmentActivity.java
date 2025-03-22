package com.example.doanthucan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.adapter.SanPhamAdapter;
import com.example.doanthucan.databinding.ActivityMainFragmentBinding;
import com.example.doanthucan.model.SanPham;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Objects;

public class MainFragmentActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ActivityMainFragmentBinding binding;
    TextView txtViewname;
    ImageView imgtc,ifood,icart;
    ViewFlipper viewFlipper;
    LinearLayout lnSinhNhat, lnDonHangLon;
    RecyclerView recyclerView;
    SanPhamAdapter sanPhamAdapter;
    ArrayList<SanPham> arr_Sanpham;
    HorizontalScrollView hrzonScrollView;
    LinearLayout lineardiachi,linearfav,linearSP;

    Toolbar toolbar;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String doan12 = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainFragmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewFlipper=findViewById(R.id.vfHome);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main_fragment);
        NavigationUI.setupWithNavController(binding.navView, navController);
        ActionViewFlipper();
        toolbar = findViewById(R.id.toolbarhome);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
          toolbar.hideOverflowMenu();

        txtViewname = findViewById(R.id.txtNameHome1);
        DocumentReference InfoProfiledocumentReference = firestore.collection("doan1").document(doan12);
        InfoProfiledocumentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txtViewname.setText(value.getString("Fullname"));

            }
        });

        imgtc = findViewById(R.id.immenu1);
        imgtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img = new Intent(MainFragmentActivity.this, User2Activity.class);
                startActivity(img);
            }
        });

        icart = findViewById(R.id.imCart1);
        icart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gg = new Intent(MainFragmentActivity.this,GiohangActivity.class);
                startActivity(gg);
            }
        });

        hrzonScrollView = findViewById(R.id.hrzonScrollView);

        lnSinhNhat=findViewById(R.id.linearSinhNhat);
        lnSinhNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thydoabc = new Intent(MainFragmentActivity.this, TiecSinhNhatActivity.class);
                startActivity(thydoabc);
            }
        });

        lnDonHangLon = findViewById(R.id.linearDonHangLon);
        lnDonHangLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tn123 = new Intent(MainFragmentActivity.this, DonHangLonActivity.class);
                startActivity(tn123);
            }
        });

        lineardiachi=findViewById(R.id.lndiachi);
        lineardiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chingoo = new Intent(MainFragmentActivity.this, DiaChiActivity.class);
                startActivity(chingoo);
            }
        });
        linearfav=findViewById(R.id.lnfav);
        linearfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zina = new Intent(MainFragmentActivity.this, FavoriteActivity.class);
                startActivity(zina);
            }
        });

        linearSP=findViewById(R.id.lnSP);
        linearSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent akhai = new Intent(MainFragmentActivity.this, ViewAllActivity.class);
                startActivity(akhai);
            }
        });

    }

    private void ActionViewFlipper() {
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
    }
}