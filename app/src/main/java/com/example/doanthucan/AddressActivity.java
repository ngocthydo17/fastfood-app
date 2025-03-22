package com.example.doanthucan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.adapter.AddressAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress  {
    Button address;
    RecyclerView recyclerView;
    List<AdressModel> adressModels;
    private AddressAdapter addressAdapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Button addAdress,paymentbtn;
    String  mAddress = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.address_recycler);
        paymentbtn = findViewById(R.id.payment_btn);

        addAdress = findViewById(R.id.add_address_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

//        LocalBroadcastManager.getInstance(this).registerReceiver(MessReceiver, new IntentFilter("diachi"));

        adressModels = new ArrayList<>();
        addressAdapter = new AddressAdapter(adressModels, this, getApplicationContext());
        recyclerView.setAdapter(addressAdapter);
        if (getIntent().getBooleanExtra("update", false)) {
            firestore.collection("doan1").document(auth.getCurrentUser().getUid()).collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc :task.getResult().getDocuments()){
                            AdressModel adressModel = doc.toObject(AdressModel.class);
                            adressModels.add(adressModel);
                        }
                        addressAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        else {
            firestore.collection("doan1").document(auth.getCurrentUser().getUid()).collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        for(DocumentSnapshot doc :task.getResult().getDocuments()){
                            AdressModel adressModel = doc.toObject(AdressModel.class);
                            adressModels.add(adressModel);
                        }
                        addressAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        paymentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddressActivity.this,HoadonActivity.class);
                i.putExtra("diachi",mAddress);

                startActivity(i);
            }
        });
        address = findViewById(R.id.add_address_btn);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( AddressActivity.this,AddAdressActivity.class));
            }
        });
    }
    private void updateData() {

    }


    //    public  BroadcastReceiver MessReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//             = intent.getIntExtra("totalQuantity",1);
//        }
//    };
    @Override
    public void setAdress(String address) {
        mAddress = address;
    }
}