package com.example.doanthucan;

import static androidx.fragment.app.FragmentManager.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.Locale;

public class HoadonActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    TextView tenkh;
    TextView ngay;
    TextView tongtien;
    TextView diachi;
    String diachi2;
    TextView gio;
    Button gui;
    ImageButton imgbtnBackSN;
    FirebaseUser currentUser;
    String usermail;
    ImageButton btn_Back_Bill;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);
        auth = FirebaseAuth.getInstance();
        currentUser=auth.getCurrentUser();
        usermail=currentUser.getEmail();
        tenkh = findViewById(R.id.ten);
        ngay = findViewById(R.id.ngay);
        gio = findViewById(R.id.gio);
        tongtien= findViewById(R.id.tien);
        gui = findViewById(R.id.buttongui);
        diachi = findViewById(R.id.diachi);
        diachi2 = getIntent().getStringExtra("diachi");
        diachi.setText(diachi2);
        firestore = FirebaseFirestore.getInstance();
        diachi = findViewById(R.id.textViewName);
        DocumentReference InfoProfiledocumentReference = firestore.collection("doan1").document(auth.getCurrentUser().getUid());
        InfoProfiledocumentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                tenkh.setText(value.getString("Fullname"));

            }
        });
        firestore.collection("doan1").document(auth.getCurrentUser().getUid()).collection("Orders").orderBy("timestamp", Query.Direction.DESCENDING).limit(1)   // Sắp xếp theo thời gian tạo giảm dần
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            for (QueryDocumentSnapshot doc :task.getResult()){
                                String ngayy = doc.getString("ngay");
                                String gio1 = doc.getString("gio");
                                int tien = doc.getLong("price1").intValue();
                                String totalGia = NumberFormat.getNumberInstance(Locale.US).format(tien);
                                ngay.setText(ngayy);
                                gio.setText(gio1);
                                tongtien.setText(totalGia + "VNĐ");

                               gui.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       String emailcontent ="Cảm ơn quý khách đã mua hàng của F'Star và xin lỗi vì sự bất tiện bạn phải tự gửi mail về" + "\n" + "Ngày" + ngayy + "\n" + "Thời gian" + "\n" + "Giá"+ totalGia;
                                       Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                       emailIntent.setType("text/plain");
                                       emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{usermail});
                                       emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Hóa đơn");
                                       emailIntent.putExtra(Intent.EXTRA_TEXT,emailcontent);
                                       startActivity(emailIntent);
                                   }
                               });
                            }


                        } else {
                            Log.d(TAG,"LỖI",task.getException());
                        }
                    }
                });
        btn_Back_Bill=findViewById(R.id.btn_Back_Bill);
        btn_Back_Bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HoadonActivity.this,MainFragmentActivity.class);
                startActivity(i);
            }
        });

    }
}