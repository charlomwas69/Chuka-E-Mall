package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.squareup.picasso.Picasso;

import org.trustfuse.mpesa_stktrial.Authentication.Login;
import org.trustfuse.mpesa_stktrial.Categories.CategoriesViewHolder;
import org.trustfuse.mpesa_stktrial.Fragments.Cart_frag;
import org.trustfuse.mpesa_stktrial.Good_Owner.Good_owner_post;
import org.trustfuse.mpesa_stktrial.Goods.MyViewHolder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Single_good extends AppCompatActivity {
    ImageButton btn;
    ImageView imageView;
    TextView Category,Name,Description,Price,user;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    Button add_to_cart;
    public String the_uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_good);

        btn = findViewById(R.id.addBtn);
        add_to_cart = findViewById(R.id.add_to_cart);
        progressBar = findViewById(R.id.prog_pic);
        Category = findViewById(R.id.single_item_category);
        Name = findViewById(R.id.single_item_name);
        Description = findViewById(R.id.single_item_description);
        Price = findViewById(R.id.single_item_price);
        user = findViewById(R.id.user);
        imageView = findViewById(R.id.single_item_image);
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        user.setText(firebaseAuth.getCurrentUser().getUid());

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LottieAlertDialog alertDialog= new LottieAlertDialog.Builder(getApplicationContext(), DialogTypes.TYPE_LOADING)
                        .setTitle("LOADING DATA")
                        .setDescription("Account data loading...")
                        .build();
                alertDialog.setCancelable(false);
                alertDialog.show();

                String cart_name = Name.getText().toString();
                    String cart_category = Category.getText().toString();
                    String cart_price = Price.getText().toString();
                    String qty = "1";
                CollectionReference cities = firebaseFirestore.collection("Cart");

//                Map<String, Object> goods = new HashMap<>();
//                    goods.put("Category",cart_category);
//                    goods.put("Name",cart_name);
//                    goods.put("Price",cart_price);
//                    goods.put("Purchaser",firebaseAuth.getCurrentUser().getUid());
//                    goods.put("Image",the_uri);
//                    goods.put("Qty",qty);
//                cities.document(cart_name).set(goods);
                Map<String, Object> goods = new HashMap<>();
                goods.put("Category",cart_category);
                goods.put("Name",cart_name);
                goods.put("Price",cart_price);
                goods.put("Purchaser",firebaseAuth.getCurrentUser().getUid());
                goods.put("Image",the_uri);
                goods.put("Qty",qty);
                cities.document(cart_name).set(goods).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(Single_good.this, "ADDED", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        LottieAlertDialog alertDialog0= new LottieAlertDialog.Builder(getApplicationContext(), DialogTypes.TYPE_ERROR)
                                .setTitle("FAILURE")
                                .setDescription("Item not added")
                                .build();
                        alertDialog0.setCancelable(true);
                        alertDialog0.show();
                    }
                });
            }
        });

        firebaseFirestore.collection("Goods")
                .whereEqualTo("Name", MyViewHolder.getValue())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // getting details of one user (document)----------------------------------------
                                String category = document.get("Category").toString();
                                String description = document.get("Description").toString();
                                String name = document.get("Name").toString();
                                String price = document.get("Price").toString();
                                the_uri = document.get("image_uri").toString();

                                Name.setText(name);
                                Category.setText(category);
                                Description.setText(description);
                                Price.setText(price);

                                StorageReference set_Dp = storageReference.child("Images/" + name);
                                set_Dp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.get().load(uri).into(imageView);
//                                        Toast.makeText(getApplicationContext(), (CharSequence) uri,Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.INVISIBLE);
//                                        Glide.with(getApplicationContext()).load(the_uri).into(imageView);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Pic Update failed" +e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        } else {
                            Toast.makeText(Single_good.this, "error" + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"This are the comments",Toast.LENGTH_LONG).show();
            }
        });
    }
}