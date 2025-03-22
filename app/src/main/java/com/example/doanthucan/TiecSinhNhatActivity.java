package com.example.doanthucan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class TiecSinhNhatActivity extends AppCompatActivity {
    ImageButton btnSN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiec_sinh_nhat);

        btnSN = findViewById(R.id.btnbacksn);
        btnSN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}