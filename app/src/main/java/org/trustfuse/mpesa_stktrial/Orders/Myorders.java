package org.trustfuse.mpesa_stktrial.Orders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.trustfuse.mpesa_stktrial.Add_comment;
import org.trustfuse.mpesa_stktrial.Good_Owner.Good_owner_post;
import org.trustfuse.mpesa_stktrial.Orders.Order_Adapter;
import org.trustfuse.mpesa_stktrial.Orders.Order_ViewHolder;
import org.trustfuse.mpesa_stktrial.R;

public class Myorders extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirestoreRecyclerAdapter<Order_Adapter, Order_ViewHolder> adapter;
    public static String value;
    FragmentActivity listener;
    public static String getValue() {
        return value;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = findViewById(R.id.order_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firebaseFirestore = FirebaseFirestore.getInstance();
//        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);

        Query query = firebaseFirestore.collection("Cart")
//                .whereEqualTo("Purchaser", firebaseAuth.getCurrentUser().getUid());
                .whereEqualTo("Purchaser",firebaseAuth.getCurrentUser().getUid());

        FirestoreRecyclerOptions<Order_Adapter> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Order_Adapter>()
                .setQuery(query,Order_Adapter.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Order_Adapter, Order_ViewHolder>(firestoreRecyclerOptions) {
            @SuppressLint("DefaultLocale")
            @Override
            protected void onBindViewHolder(Order_ViewHolder order_viewHolder, int i, Order_Adapter order_adapter) {

                order_viewHolder.name.setText(order_adapter.getName());
                order_viewHolder.category.setText(order_adapter.getCategory());
                order_viewHolder.price.setText(order_adapter.getPrice());
                order_viewHolder.price.setText(order_adapter.getPrice());
                Glide.with(getApplicationContext()).load(order_adapter.getImage()).into(order_viewHolder.circleImageView);

                value = order_viewHolder.name.getText().toString();

                order_viewHolder.comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent().setClass(getApplicationContext(), Add_comment.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        getApplicationContext().startActivity(i);
                    }
                });
                
            }
            @NonNull
            @Override
            public Order_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_order_template,parent,false);
                return new Order_ViewHolder(view1);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1, GridLayoutManager.VERTICAL,false);
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