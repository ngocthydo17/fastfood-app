package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DiaChiActivity extends AppCompatActivity {
    ImageView imgback,imgdiachi;
    Toolbar toolbar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);

        toolbar=findViewById(R.id.toolbaDiaChi);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgback=findViewById(R.id.imgback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgdiachi=findViewById(R.id.imgdiachi);
        imgdiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });





    }

    public void DiaChiThy(View view) {
        Intent thy = new Intent(DiaChiActivity.this,MapActivity.class);
        startActivity(thy);
    }

    public void DiaChiQin(View view) {
        Intent qin = new Intent(DiaChiActivity.this,Map1Activity.class);
        startActivity(qin);
    }

    public void DiaChiTNgan(View view) {
        Intent tngan = new Intent(DiaChiActivity.this,Map2Activity.class);
        startActivity(tngan);
    }
}