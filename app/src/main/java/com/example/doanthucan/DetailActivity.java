package com.example.doanthucan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.doanthucan.model.ViewAll;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;


public class DetailActivity extends AppCompatActivity {
    TextView tvNameDetailC,tvPriceDetailC,tvQuantityC;
    ImageView ivImageDetailC;

    int count = 1;
    int totalPrice ;
    Button btnOrder;


    // firebase
    FirebaseAuth auth;
    //    StorageReference storageReference ;
    FirebaseStorage firestore;
    FirebaseFirestore firestores;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    CheckBox checkfav;

    private CollectionReference favouriteList = FirebaseFirestore.getInstance().collection("FAVOURITES");
    ImageButton imageButtonBack,  imageButtonShare;
    ViewAll product = new ViewAll();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        firestore = FirebaseStorage.getInstance().getReference().getStorage();
        firestores = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        tvNameDetailC = findViewById(R.id.tvNameDetail);
        tvPriceDetailC = findViewById(R.id.tvPriceDetail);
        ivImageDetailC = findViewById(R.id.ivImageDetail);
        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtoCart();
            }
        });
        tvQuantityC = (TextView) findViewById(R.id.tvQuantity);

        imageButtonBack = findViewById(R.id.ibBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Ánh xạ nút fav
        checkfav=findViewById(R.id.checkfav);


        //load user
        String user = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();


        //load tên
        String ten = getIntent().getStringExtra("ten");
        tvNameDetailC.setText(ten + "");
        product.setName(ten);

        //load giá ban đầu (giá trị mặc định là 1)
        int price = getIntent().getIntExtra("gia",0 );
        changePrice(1);

        //load mô tả

        //load phân loại
        String loai = getIntent().getStringExtra("loai");

        //load ảnh
        String anh_nay_moi_dung = getIntent().getStringExtra("loai");
        Glide.with(this).load(anh_nay_moi_dung).into(ivImageDetailC);
        product.setImage(anh_nay_moi_dung);
        checkfav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Toast.makeText(DetailActivity.this,"Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                    checkfav.setBackgroundResource(R.drawable.icon_fav);
                    addFavourite(user,ten,price,loai,anh_nay_moi_dung);
                    //
                } else {
                    Toast.makeText(DetailActivity.this,"Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
                    checkfav.setBackgroundResource(R.drawable.icon_fav2);
                    removeFavourite(anh_nay_moi_dung);
                    //
                }
            }
        });


        // Check PRODUCTS có trùng với FAVOURITES bằng cách so sánh Image //
        // Nếu có dữ liệu trong đây thì tiến hành checkbox                //
        // Không có thì tiếp tục xuống dưới                               //
        favouriteList.whereEqualTo("image",anh_nay_moi_dung)
                .whereEqualTo("user",user)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            checkfav.setBackgroundResource(R.drawable.icon_fav);
                            checkfav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if(b) {
                                        Toast.makeText(DetailActivity.this,"Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
                                        checkfav.setBackgroundResource(R.drawable.icon_fav2);
                                        removeFavourite(anh_nay_moi_dung);
                                    } else {
                                        Toast.makeText(DetailActivity.this,"Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                                        checkfav.setBackgroundResource(R.drawable.icon_fav);
                                        addFavourite(user,ten,price,loai,anh_nay_moi_dung);
                                    }
                                }
                            });
                        }

                    }
                });



        // ivImageDetailC.setImageResource(Integer.parseInt(product.getImage()));
    }


    // Thêm vào danh sách yêu thích //
    public void addFavourite(String user,String name,int gia,String loai,String anh) {
         product = new ViewAll(user,name,loai,anh,gia);
        favouriteList.add(product);
    }
    // Xóa khỏi danh sách yêu thích //
    public void removeFavourite(String anh) {
        favouriteList.whereEqualTo("image",anh)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String documentId = "";

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            documentId = documentSnapshot.getId();
                        }
                        favouriteList.document(documentId).delete();
                    }
                });

    }
    public void changePrice(int quantity) {
        int gia = quantity * getIntent().getIntExtra("gia",0);
        String giatien = NumberFormat.getNumberInstance(Locale.US).format(gia);
        tvPriceDetailC.setText(giatien + " VNĐ");
        product.setPrice(getIntent().getIntExtra("gia",0));
    }
    public  void addtoCart(){
        String saveCurrentDate,savetime;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM /dd/yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        savetime = currentTime.format(calendar.getTime());
        final HashMap<String,Object> cartmap = new HashMap<>();
        String anh = getIntent().getStringExtra("loai");
        totalPrice = getTotalPrice();
        cartmap.put("name",tvNameDetailC.getText().toString());
        cartmap.put("price",totalPrice);
        cartmap.put("totalQuantity",tvQuantityC.getText().toString().trim()+"");
        cartmap.put("anh",anh);
        cartmap.put("saveCurrentDate",saveCurrentDate);
        cartmap.put("savetime",savetime);
        firestores.collection("doan1").document(Objects.requireNonNull(auth.getCurrentUser()).getUid())
                .collection("AddtoCart")
                .add(cartmap).addOnCompleteListener(task -> {
                    Toast.makeText(DetailActivity.this,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();
                    finish();
                });
    }
    public void increment(View v) {
        count++;
        changePrice(count);
        tvQuantityC.setText("" + count);
    }

    // Giảm số lượng //
    public void decrement(View v) {
        // Nếu nhập số lượng nhỏ hơn bằng 1 thì set mặt định số lượng bằng 1
        if (count <= 1) {
            count = 1;
            changePrice(count);
        } else {
            count--;
            changePrice(count);
        }
        tvQuantityC.setText("" + count);
    }
    private int getTotalPrice() {
        totalPrice = count * getIntent().getIntExtra("gia",0);
        return  totalPrice;
    }
}