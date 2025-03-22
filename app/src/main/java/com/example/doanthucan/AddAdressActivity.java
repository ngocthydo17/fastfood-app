package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddAdressActivity extends AppCompatActivity {
    EditText name, address,city, postalCode,phoneNumber;
    Toolbar toolbar;
    Button addAdress12;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    ImageButton ve;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_adress);
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
   ve = findViewById(R.id.icbackk);
   ve.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           finish();
       }
   });

        address = findViewById(R.id.ad_address);
        city = findViewById(R.id.ad_city);

        addAdress12 = findViewById(R.id.ad_add_address);
        addAdress12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userCity = city.getText().toString();
                String userAddress = address.getText().toString();


                String final_address = " ";
                if(!userAddress.isEmpty()){
                    final_address+=userAddress;
                    final_address+=" ";
                }
                if(!userCity.isEmpty()){
                    final_address+=userCity;

                }


                if ( !userCity.isEmpty() && !userAddress.isEmpty() ){
                    Map<String,String> map = new HashMap<>();
                    map.put("userAdress",final_address);
                    firestore.collection("doan1").document(auth.getCurrentUser().getUid()).collection("Address").add(map).
                            addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(AddAdressActivity.this,"Thêm vào thành công ",Toast.LENGTH_SHORT).show();
                                        city.setText("");
                                        address.setText("");
                                        Intent intent = new Intent(AddAdressActivity.this, AddressActivity.class);
                                        intent.putExtra("update", true);
                                        startActivity(intent);
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }
                                }
                            });

                }else {
                    Toast.makeText(AddAdressActivity.this,"Thông tin còn để trống ",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}