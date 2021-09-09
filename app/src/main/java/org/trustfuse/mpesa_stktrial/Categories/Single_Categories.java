package org.trustfuse.mpesa_stktrial.Categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.trustfuse.mpesa_stktrial.Categories.CategoriesViewHolder;
import org.trustfuse.mpesa_stktrial.Goods.Goods_Adapter;
import org.trustfuse.mpesa_stktrial.Goods.MyViewHolder;
import org.trustfuse.mpesa_stktrial.R;

public class Single_Categories extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;

    Toolbar toolbar;
//    TextView fetch_category;
    FirestoreRecyclerAdapter<Goods_Adapter, MyViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single__categories);
        recyclerView = findViewById(R.id.single_category_recycler);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        firebaseFirestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbar_single_categories);
        toolbar.setTitle("Categories");
        toolbar.setEnabled(true);


        Query query = firebaseFirestore.collection("Goods")
                .whereEqualTo("Category", CategoriesViewHolder.getValue());

        FirestoreRecyclerOptions<Goods_Adapter> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Goods_Adapter>()
                .setQuery(query,Goods_Adapter.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Goods_Adapter, MyViewHolder>(firestoreRecyclerOptions) {
            @SuppressLint("DefaultLocale")
            @Override
            protected void onBindViewHolder(MyViewHolder myViewHolder, int i, Goods_Adapter goods_adapter) {

                myViewHolder.name.setText(goods_adapter.getName());
                myViewHolder.category.setText(goods_adapter.getCategory());
                myViewHolder.price.setText(goods_adapter.getPrice());
                Glide.with(getApplicationContext()).load(goods_adapter.getImage_uri()).into(myViewHolder.circleImageView);
            }
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_item_template,parent,false);
//                progressBarr = view1.findViewById(R.id.prog_pic);
//                progressBarr.setVisibility(view1.GONE);
                return new MyViewHolder(view1);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

    }
    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }
}