package com.example.doanthucan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DTActivity extends AppCompatActivity {
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dtactivity);

//        btncall = findViewById(R.id.btnCall);
//        btncall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:0906483257"));
//                startActivity(intent);
//            }
//        });

//        txt6 = findViewById(R.id.textView6);
//        txt6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent thy = new Intent(DTActivity.this,MapActivity.class);
//                startActivity(thy);
//            }
//        });
    }


    public void chovu(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
              intent.setData(Uri.parse("tel:0906483257"));
               startActivity(intent);
    }
    public void Thydo(View view) {
        Intent thy = new Intent(DTActivity.this,MapActivity.class);
              startActivity(thy);
    }

    public void fstar(View view) {
         finish();
    }
}