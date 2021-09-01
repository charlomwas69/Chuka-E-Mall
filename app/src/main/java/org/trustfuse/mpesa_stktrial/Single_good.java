package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.trustfuse.mpesa_stktrial.Categories.CategoriesViewHolder;
import org.trustfuse.mpesa_stktrial.Goods.MyViewHolder;

public class Single_good extends AppCompatActivity {
    ImageButton btn;
    ImageView imageView;
    TextView Category,Name,Description,Price,user;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_good);

        btn = findViewById(R.id.addBtn);
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

//        StorageReference set_Dp = storageReference.child("Images/" + th);
////        proggg.setVisibility(View.VISIBLE);
//        set_Dp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(imageView);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getApplicationContext(), "Pic Update failed", Toast.LENGTH_SHORT).show();
//            }
//        });
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
                                String the_uri = document.get("image_uri").toString();

                                Name.setText(name);
                                Category.setText(category);
                                Description.setText(description);
                                Price.setText(price);

                                StorageReference set_Dp = storageReference.child("Images/" + name);
                                set_Dp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.get().load(uri).into(imageView);
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