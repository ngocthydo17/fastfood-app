package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserInfoActivity extends AppCompatActivity {

    TextView mtvProUserName, mtvProUserMail, mtvProUserPhone,txtten;
    EditText edtProUserName, edtProUserMail, edtProUserPhone;
    ImageView mimgProfileImage;
    EditText edtprofileuserDate;

    Button btChangeInfoProfile, btChangePassProfile;
    ImageButton btnUserback, imgImage;
    FirebaseAuth  firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    FirebaseUser user = firebaseAuth.getCurrentUser();
    String doan12 = firebaseAuth.getCurrentUser().getUid();
    boolean verification;
    private boolean isResendClicked = false;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        btnUserback = findViewById(R.id.btnUserBack);

        btnUserback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mimgProfileImage = findViewById(R.id.imgProfileImage);
//       imgImage = findViewById(R.id.imgbtProfileImageChange);
        StorageReference profileRef = storageReference.child("Users/"+ doan12 +"/Profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(mimgProfileImage);
                Glide.with(UserInfoActivity.this).load(uri).override(150, 150).centerCrop().into(mimgProfileImage);
            }
        });
//       imgImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent openGalleryintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryintent, 1000);
//            }
//        });
        edtProUserName = findViewById(R.id.edtProfileUserName);
        edtProUserMail = findViewById(R.id.edtProfileUserMail);
        edtProUserPhone = findViewById(R.id.edtProfileUserPhone);
        DocumentReference InfoProfiledocumentReference = firestore.collection("doan1").document(doan12);
        InfoProfiledocumentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                edtProUserName.setText(value.getString("Fullname"));
                edtProUserMail.setText(value.getString("Mail"));
                edtProUserPhone.setText(value.getString("Phone"));
                edtprofileuserDate.setText(value.getString("nam"));
            }
        });
        btChangePassProfile = findViewById(R.id.btnChangePassUser);
        btChangePassProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RePassDialog dialog = new RePassDialog(UserInfoActivity.this);
                dialog.show();
            }
        });
        btChangeInfoProfile = findViewById(R.id.btnChaneInfoProfile);
        btChangeInfoProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edtProUserMail.getText().toString();
                String currentMail = user.getEmail();
                DocumentReference ChangeInfoDocumentReference = firestore.collection("doan1").document(doan12);
                Map<String,Object> edited = new HashMap<>();

                if (!currentMail.equals(email)) {
                    user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            user.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    user.sendEmailVerification();
                                    VerifyMailDialog dialog = new VerifyMailDialog(UserInfoActivity.this);
                                    dialog.show();

                                    // Cập nhật email trong Firestore sau khi email được xác minh thành công
                                    edited.put("Mail", email);
                                    ChangeInfoDocumentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            // Thành công khi cập nhật email trong Firestore
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Xử lý lỗi khi cập nhật email trong Firestore không thành công
                                        }
                                    });
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserInfoActivity.this,"Thay đổi không thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                String fullName = edtProUserName.getText().toString();
                String phone = edtProUserPhone.getText().toString();
                String nam= edtprofileuserDate.getText().toString();
                if (!fullName.isEmpty() || !phone.isEmpty()) {
                    edited.put("Fullname", fullName);
                    edited.put("Phone", phone);
                    edited.put("nam",nam);
                    ChangeInfoDocumentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(UserInfoActivity.this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserInfoActivity.this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    return;
                }
            }
        });
        edtprofileuserDate = findViewById(R.id.edtProfileUserDate);
        final Calendar calendar = Calendar.getInstance();
        final int ngay = calendar.get(Calendar.DAY_OF_MONTH);
        final int thang = calendar.get(Calendar.MONTH);
        final int nam = calendar.get(Calendar.YEAR);

        edtprofileuserDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sm = new SimpleDateFormat("mm/dd/yyyy");
                String strDate = sm.format(new Date());
                DatePickerDialog dialog = new DatePickerDialog(UserInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int Nam, int Thang, int Ngay) {

                        Thang = Thang + 1;
                        String date = Ngay+"/"+Thang+"/"+Nam;
                        edtprofileuserDate.setText(date);
                    }
                },nam,thang,ngay);
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
                dialog.show();

            }
        });

    }




    public  class  RePassDialog extends Dialog{
        EditText matkhau = findViewById(R.id.edtTenDL);
        EditText   medtdialogPass = findViewById(R.id.edtFogotMK);
        EditText medtdialogRePass = findViewById(R.id.edtFogotMK1);
        Button mbtDialogChange = findViewById(R.id.btnLuuMK);
        @SuppressLint("MissingInflatedId")
        public RePassDialog(Context context){
            super(context);

            setContentView(R.layout.changepass);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setDimAmount(0.6f);
            matkhau = findViewById(R.id.edtTenDL);
            medtdialogPass = findViewById(R.id.edtFogotMK);
            medtdialogRePass = findViewById(R.id.edtFogotMK1);
            mbtDialogChange = findViewById(R.id.btnLuuMK);
            mbtDialogChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(matkhau.getText().toString().isEmpty() || matkhau.getText().toString().isEmpty()){
                        Toast.makeText(UserInfoActivity.this,"Vui lòng nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                    }
                    if(medtdialogPass.getText().toString().isEmpty() || medtdialogRePass.getText().toString().isEmpty()){
                        Toast.makeText(UserInfoActivity.this,"Vui lòng nhập đầy đủ thông tin ", Toast.LENGTH_SHORT).show();
                    }
                    else if(!medtdialogPass.getText().toString().equals(medtdialogRePass.getText().toString())){
                        Toast.makeText(UserInfoActivity.this,"Mat khau khong trung khop  ",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        user.updatePassword(medtdialogPass.getText().toString());
                        Toast.makeText(UserInfoActivity.this,"Thay đổi mật khẩu thành công ",Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            });

        }
    }
    public class VerifyMailDialog extends Dialog{
        TextView tvDialog;
        Button tvDialogContinue;
        @SuppressLint("MissingInflatedId")
        public VerifyMailDialog(Context context){
            super(context);
            setContentView(R.layout.fragment_user);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setDimAmount(0.6f);

            tvDialog = findViewById(R.id.tvDialogReVerifyMail);
            tvDialogContinue = findViewById(R.id.btnDialogContinue);
            tvDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isResendClicked){
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                isResendClicked = true;
                                setTimer();
                                Toast.makeText(UserInfoActivity.this,"Yêu cầu gửi lại thành công", Toast.LENGTH_SHORT).show();

                            }

                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UserInfoActivity.this,"Yêu cầu gửi lại thất bại ",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
            tvDialogContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            verification = user.isEmailVerified();
                            if(verification == false){
                                if (VerifyMailDialog.this != null && VerifyMailDialog.this.isShowing()){
                                    dismiss();
                                }
                                firebaseAuth.signOut();

                                Toast.makeText(UserInfoActivity.this,"Email tài khoản chưa được xác nhận ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UserInfoActivity.this,MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(UserInfoActivity.this,"Email đã được xác nhận",Toast.LENGTH_SHORT).show();
                                if(VerifyMailDialog.this != null && VerifyMailDialog.this.isShowing()){
                                    dismiss();
                                }
                            }
                        }
                    });
                }
            });
        }
        private  void setTimer(){
            countDownTimer = new CountDownTimer(60000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long seconds = millisUntilFinished / 1000;
                    tvDialog.setText("Gửi lại trong" + seconds + "giây");
                }

                @Override
                public void onFinish() {
                    tvDialog.setText("Gửi lại xác nhân mail");
                    isResendClicked = false;
                }
            }.start();
        }

    }
    private void openChangePassDialog(int gravity) {
        final Dialog dialog = new Dialog(UserInfoActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_userpass);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);
        EditText medtdialogPass = findViewById(R.id.edtdialogPass);
        EditText medtdialogRePass = findViewById(R.id.edtdialogRePass);
        Button mbtDialogChange = findViewById(R.id.btnDialogChange);

        mbtDialogChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        if(dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
    }
}