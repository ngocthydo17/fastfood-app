package com.example.doanthucan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MaanhinhchaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maanhinhchao);
        Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivities();
            }

        },3000);
    }
    public void nextActivities(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            startActivity(new Intent(MaanhinhchaoActivity.this,MainActivity.class));

    }
}