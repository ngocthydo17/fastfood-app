package com.example.doanthucan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class User2Activity extends AppCompatActivity {
 TextView btncstk,txtViewname,btnUDCT;
 Button btnnback,btndangxuat,btnchct,btnhtlt;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    FirebaseUser user = firebaseAuth.getCurrentUser();
    String doan12 = firebaseAuth.getCurrentUser().getUid();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user2);
        btncstk = findViewById(R.id.btnCSTK);
        btncstk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(User2Activity.this,UserInfoActivity.class);
                startActivity(i);
            }
        });
        txtViewname = findViewById(R.id.textViewName);
        DocumentReference InfoProfiledocumentReference = firestore.collection("doan1").document(doan12);
        InfoProfiledocumentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
              txtViewname.setText(value.getString("Fullname"));

            }
        });
        btnnback = findViewById(R.id.btnbackCPass);
        btnnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       btndangxuat = findViewById(R.id.btnDangXuat);
       btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                finish();
            }
        });
        btnUDCT=findViewById(R.id.btnUDCT);
        btnUDCT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(User2Activity.this,FavoriteActivity.class);
                startActivity(i);
            }
        });
       TextView btnDSDH=findViewById(R.id.btnDSDH);
       btnDSDH.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i=new Intent(User2Activity.this,xemhoadonActivity.class);
               startActivity(i);
           }
       });
       btnchct = findViewById(R.id.btnCHCT);
       btnchct.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent ic = new Intent(User2Activity.this, LienheActivity2.class);
               startActivity(ic);
           }
       });
       btnhtlt = findViewById(R.id.btnHTLH);
       btnhtlt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(User2Activity.this,DTActivity.class);
               startActivity(i);
           }
       });

    }
}