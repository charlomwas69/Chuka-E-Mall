package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.squareup.picasso.Picasso;

import org.trustfuse.mpesa_stktrial.Comments.Comments;
import org.trustfuse.mpesa_stktrial.Goods.MyViewHolder;

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
    public static String i_name;
    public static String getI_name(){
        return  i_name;
    }

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
                    String cart_name = Name.getText().toString().trim();
                    String cart_category = Category.getText().toString();
                    String cart_price = Price.getText().toString();
                    String qty = "1";
                CollectionReference cities = firebaseFirestore.collection("Cart");

                Map<String, Object> goods = new HashMap<>();
                    goods.put("Category",cart_category);
                    goods.put("Name",cart_name);
                    goods.put("Price",cart_price);
                    goods.put("Purchaser",firebaseAuth.getCurrentUser().getUid());
                    goods.put("Image",the_uri);
                    goods.put("Qty",qty);
                    goods.put("Paid","no");
//                    goods.put("Status","Pending ⏳");
                    goods.put("Status","Successful ✅");

                cities.document(cart_name).set(goods).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(Single_good.this, "ADDDDDDDDED", Toast.LENGTH_SHORT).show();
                        LottieAlertDialog alertDialog= new LottieAlertDialog.Builder(Single_good.this, DialogTypes.TYPE_SUCCESS)
                                .setTitle("SUCCESS")
                                .setDescription("Item added to cart")
                                .build();
                        alertDialog.setCancelable(true);
                        alertDialog.show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

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
//                Toast.makeText(getApplicationContext(),"This are the comments",Toast.LENGTH_LONG).show();
                 i_name = Name.getText().toString();
                Intent intent = new Intent(getApplicationContext(), Comments.class);
                startActivity(intent);

            }
        });
    }
}