package com.example.doanthucan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.DetailActivity;
import com.example.doanthucan.GiohangActivity;
import com.example.doanthucan.R;
import com.example.doanthucan.User2Activity;
import com.example.doanthucan.adapter.HomeAdapter;
import com.example.doanthucan.adapter.ProductFragmentAdapter;
import com.example.doanthucan.model.HomeCategory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ProductFragment extends Fragment  implements ProductFragmentAdapter.ProductCallback {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    List<HomeCategory> categoryList;
    DrawerLayout mdrawMenuPro;
    RecyclerView homeCatRec;
    FirebaseFirestore db;
    HomeAdapter homeAdapter;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    String doan12 = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
    private View v;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        ImageView imCartpro=root.findViewById(R.id.imCartpro);
        imCartpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vanvu=new Intent(ProductFragment.this.getActivity(), GiohangActivity.class);
                startActivity(vanvu);
            }
        });
        TextView txtname=root.findViewById(R.id.txtNamePro);
        DocumentReference InfoProfiledocumentReference = firestore.collection("doan1").document(doan12);
        InfoProfiledocumentReference.addSnapshotListener(ProductFragment.this.getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txtname.setText(value.getString("Fullname"));

            }
        });
        ImageView imgtc = root.findViewById(R.id.immenupro);
        imgtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent img = new Intent(ProductFragment.this.getActivity(), User2Activity.class);
                startActivity(img);
            }
        });
        db = FirebaseFirestore.getInstance();
        // Home Category
        homeCatRec = root.findViewById(R.id.rvGrid);
        categoryList=new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(ProductFragment.this.getActivity(),2);
        homeCatRec.setAdapter(homeAdapter);
        homeCatRec.setLayoutManager(gridLayoutManager);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }





    @Override
    public void onItemClick(String ten, int gia, String mota, String anh) {
        Intent i = new Intent(ProductFragment.this.getActivity(), DetailActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("gia",gia);
        i.putExtra("anh",anh);
        startActivity(i);
    }


    void Menu(){
        mdrawMenuPro = v.findViewById(R.id.drawmenuPro);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ProductFragment.this.getActivity(), mdrawMenuPro, R.string.nav_menu_op, R.string.nav_menu_cl);
        mdrawMenuPro.addDrawerListener(toggle);
        toggle.syncState();
    }
}