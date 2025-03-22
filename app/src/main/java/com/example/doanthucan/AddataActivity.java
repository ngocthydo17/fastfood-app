package com.example.doanthucan;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class AddataActivity extends AppCompatActivity {
   private  static final String TAG ="MainActivity2";
    private static final String KEY_TITLE = "title";
   private static final String KEY_DESCRIPTION = "description";

    private EditText editTextName,editTextDescription,editTextPrice,editTextCategory,editTextImage;

    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference notebookRef = db.collection("AllProducts");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addata);
        editTextName = findViewById(R.id.edit_text_name);
        editTextPrice = findViewById(R.id.edit_text_price);
        editTextCategory = findViewById(R.id.edit_text_category);
        editTextImage = findViewById(R.id.edit_text_image);
        textViewData = findViewById(R.id.text_view_data);
    }
    @Override
    protected void onStart() {
        super.onStart();
        notebookRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }
                String data = "";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Product note = documentSnapshot.toObject(Product.class);
                    String name = note.getName();
                    int price = Integer.parseInt(String.valueOf(note.getPrice()));
                    String category = note.getCategory();
                    String image = note.getImage();
                    data += "\nTên: " + name + "\nGiá: "
                            + price + "\nLoại sản phẩm: " + category + "\nẢnh: " + image;

                }
                textViewData.setText(data);
            }
        });
    }

    public void addNote(View v) {
        String name = editTextName.getText().toString();
        int price = Integer.parseInt(editTextPrice.getText().toString());
        String category = editTextCategory.getText().toString();
        String image = editTextImage.getText().toString();
        Product note = new Product(name,category,image,price);
        notebookRef.add(note);
        editTextName.setText("");
        editTextPrice.setText("");
        editTextCategory.setText("");
        editTextImage.setText("");
    }
}