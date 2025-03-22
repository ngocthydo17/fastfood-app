package com.example.doanthucan.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doanthucan.Cart;
import com.example.doanthucan.DiaChiActivity;
import com.example.doanthucan.DonHangLonActivity;
import com.example.doanthucan.FavoriteActivity;
import com.example.doanthucan.GiohangActivity;
import com.example.doanthucan.R;
import com.example.doanthucan.TiecSinhNhatActivity;
import com.example.doanthucan.User2Activity;
import com.example.doanthucan.ViewAllActivity;
import com.example.doanthucan.adapter.HomeAdapter;
import com.example.doanthucan.adapter.PopularAdapter;
import com.example.doanthucan.databinding.FragmentHomeBinding;
import com.example.doanthucan.model.HomeCategory;
import com.example.doanthucan.model.PopularProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment implements PopularAdapter.ProductClickListener{
    RecyclerView popularRec,homeCatRec, recyclerView;

    private FragmentHomeBinding binding;



    //popularItem
    PopularAdapter popularAdapter;
    List<PopularProduct> popularProductList;
    FirebaseFirestore db;
    //Home Category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;
    TextView txtViewname;
    ImageView imgtc,ifood,icart;
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    String doan12 = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();

    HorizontalScrollView hrzonScrollView;
    LinearLayout lnSinhNhat, lnDonHangLon;

    private ActionMenuView toolbar;

    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_home,container,false);
        db=FirebaseFirestore.getInstance();
//        toolbar = root.findViewById(R.id.toolbarhome);
//        toolbar.hideOverflowMenu();
        popularRec=root.findViewById(R.id.rv_favourite_meal);
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularProductList=new ArrayList<>();
        popularAdapter= new PopularAdapter(getActivity(),popularProductList);
        popularAdapter.setProductClickListener(this);
        popularRec.setAdapter(popularAdapter);
        db.collection("PopularProduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProduct popularProduct = document.toObject(PopularProduct.class);
                                popularProductList.add(popularProduct);
                                popularAdapter.notifyDataSetChanged();

                            }

                        }else {
                            Toast.makeText(getActivity(), "Error"+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        imgtc = root.findViewById(R.id.immenu1);
        imgtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ii = new Intent(HomeFragment.this.getActivity(), User2Activity.class);
                startActivity(ii);
            }
        });

        icart = root.findViewById(R.id.imCart1);
        icart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeFragment.this.getActivity(), GiohangActivity.class);
                startActivity(i);
            }
        });
        txtViewname = root.findViewById(R.id.txtNameHome1);
        DocumentReference InfoProfiledocumentReference = firestore.collection("doan1").document(doan12);
        InfoProfiledocumentReference.addSnapshotListener(this.getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@com.google.firebase.database.annotations.Nullable DocumentSnapshot value, @com.google.firebase.database.annotations.Nullable FirebaseFirestoreException error) {
                txtViewname.setText(value.getString("Fullname"));
            }
        });


       LinearLayout lineardiachi=root.findViewById(R.id.lndiachi);
        lineardiachi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chingoo = new Intent(HomeFragment.this.getActivity(), DiaChiActivity.class);
                startActivity(chingoo);
            }
        });
        LinearLayout linearfav=root.findViewById(R.id.lnfav);
        linearfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zina = new Intent(HomeFragment.this.getActivity(), FavoriteActivity.class);
                startActivity(zina);
            }
        });

        LinearLayout   linearSP=root.findViewById(R.id.lnSP);
        linearSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent akhai = new Intent(HomeFragment.this.getActivity(), ViewAllActivity.class);
                startActivity(akhai);
            }
        });
        hrzonScrollView = root.findViewById(R.id.hrzonScrollView);

        lnSinhNhat=root.findViewById(R.id.linearSinhNhat);
        lnSinhNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent thydoabc = new Intent(HomeFragment.this.getActivity(), TiecSinhNhatActivity.class);
                startActivity(thydoabc);
            }
        });

        lnDonHangLon = root.findViewById(R.id.linearDonHangLon);
        lnDonHangLon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tn123 = new Intent(HomeFragment.this.getActivity(), DonHangLonActivity.class);
                startActivity(tn123);
            }
        });

        return root;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void onProductClick(PopularProduct product) {
        // Thêm sản phẩm vào giỏ hàng tại đây
        // Ví dụ:
        addToCart(product);
    }
    private void addToCart(PopularProduct product) {
        if (product != null&& firebaseAuth != null && firebaseAuth.getCurrentUser() != null) { // Kiểm tra xem product có null không
            // Tạo một đối tượng Cart từ đối tượng PopularProduct
            Cart cartItem = new Cart();
            cartItem.setName(product.getName());
            cartItem.setPrice(Integer.parseInt(product.getPrice()+""));
            cartItem.setTotalQuantity(String.valueOf(1));
            cartItem.setCategory(product.getCategory());
            cartItem.setAnh(product.getImage());
            // Tùy chỉnh các thông tin khác nếu cần thiết

            // Thêm sản phẩm vào giỏ hàng
            firestore.collection("doan1")
                    .document(firebaseAuth.getCurrentUser().getUid())
                    .collection("AddtoCart")
                    .add(cartItem)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Sản phẩm đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Không thể thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            // Xử lý trường hợp product là null
            Toast.makeText(getActivity(), "Sản phẩm không tồn tại", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}