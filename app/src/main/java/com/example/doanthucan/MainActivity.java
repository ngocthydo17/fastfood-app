package com.example.doanthucan;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
// nay la ham dang nhap
public class MainActivity extends AppCompatActivity {
    private EditText dnmail, dnpass;
    private TextView cstk;
    private FirebaseAuth auth;
    private EditText edtPassword;
    private ImageButton btnShowPassword;
    private boolean isResend = false;
    private TextInputLayout tilPassword;
    private CountDownTimer countDownTimer;
    FirebaseUser mUser;
    Button btndn, btndk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btndk = findViewById(R.id.btnDangky);
        btndn = findViewById(R.id.btnDangnhap);
        edtPassword = findViewById(R.id.edtPass);
        tilPassword = findViewById(R.id.txtInputPassDK);
        btnShowPassword=findViewById(R.id.btnShowPassDK);
        auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();
        tilPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);

        //Xử lý sự kiện nút show con mắt

        btnShowPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtPassword = findViewById(R.id.edtPass);
                if(edtPassword.getInputType()== InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnShowPassword.setImageResource(R.drawable.ic_block_123);
                }
                else {
                    edtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnShowPassword.setImageResource(R.drawable.ic_block_123);
                }
                edtPassword.setSelection(edtPassword.getText().length());
            }
        });

        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        dangkiListener();
        dangnhap();
        quenmatkhau();
    }

    // quen mat khau
    private void quenmatkhau() {
        cstk = findViewById(R.id.csmk);
        cstk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                // hiện view dialog forgot
                View dialogV = getLayoutInflater().inflate(R.layout.dialog_forgot, null);
                //
                EditText nhap = dialogV.findViewById(R.id.nhap);
                builder.setView(dialogV);
                AlertDialog dialog = builder.create();
                dialogV.findViewById(R.id.btnsave).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String useremail = nhap.getText().toString();

                        if (TextUtils.isEmpty(useremail) && !Patterns.EMAIL_ADDRESS.matcher(useremail).matches()) {
                            Toast.makeText(MainActivity.this, "Nhấn vào mật khau dang nhap cua ban", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        auth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Kiểm tra mail của bạn ", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(MainActivity.this, "Gui that bai", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogV.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                if (dialog.getWindow() != null) {
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
                dialog.show();
            }
        });
    }

    private void dangnhap() {
        dnmail = findViewById(R.id.edtLogin);
        dnpass = findViewById(R.id.edtPass);
        btndn = findViewById(R.id.btnDangnhap);
        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                String stremail = dnmail.getText().toString();
                String strpass = dnpass.getText().toString();
                if (TextUtils.isEmpty(stremail)) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập gmail", Toast.LENGTH_SHORT).show();
                    dnmail.requestFocus();
                } else if (TextUtils.isEmpty(strpass)) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                    dnmail.requestFocus();
                } else {
                    auth.signInWithEmailAndPassword(stremail, strpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Boolean verification = auth.getCurrentUser().isEmailVerified();
                                if (verification == true) {
                                    Intent i = new Intent(MainActivity.this, MainFragmentActivity.class);
                                    startActivity(i);
                                    dnpass.setText("");
                                } else {
                                    AlertDialog.Builder d = new AlertDialog.Builder(MainActivity.this);
                                    d.setTitle("Xác nhận gmail");
                                    d.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (!isResend) {   // Nếu button resend chưa được nhấn trong lần nào trước đó
                                                mUser.sendEmailVerification()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(MainActivity.this, "Vui lòng xác nhận gmail " + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                                                                    // isResendClicked = true;    // Đánh dấu là button resend đã được nhấn 1 lần
                                                                    //setTimer();                // Thiết lập đếm ngược thời gian chờ giữa các lần gửi email xác minh
                                                                } else {
                                                                    Toast.makeText(getApplicationContext(), "Nhận OTP thất bại", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });

                                            } else {
                                                Toast.makeText(getApplicationContext(), "Vui lòng đợi trong giây lát", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    d.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                                    d.create().show();
                                }
                            } else {


                                Toast.makeText(MainActivity.this, "Email hoặc mật khẩu của bạn không đúng.", Toast.LENGTH_SHORT).show();

                            }
                        }


                    });

                }
            }
        });
    }

    private void dangkiListener() {
        btndk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }

    //ẩn/hiện mật khẩu trong EditText
    //thiet lap thuoc tinh endIConMode cho TextInputLayout



}