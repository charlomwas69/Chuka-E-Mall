package org.trustfuse.mpesa_stktrial.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.math.MathUtils;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.trustfuse.mpesa_stktrial.Authentication.Login;
import org.trustfuse.mpesa_stktrial.CartViewHolder;
import org.trustfuse.mpesa_stktrial.Cart_Adapter;
import org.trustfuse.mpesa_stktrial.Order_succesful;
import org.trustfuse.mpesa_stktrial.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Cart_frag extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirestoreRecyclerAdapter<Cart_Adapter, CartViewHolder> adapter;
    TextView sum_display;
    ArrayList<Integer> list;
    View next;
    Query query;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.cart_fragment,container,false);
        View view = inflater.inflate(R.layout.cart_fragment,container,false);
        recyclerView = view.findViewById(R.id.cart_recycler);
        sum_display = view.findViewById(R.id.total);
        next = view.findViewById(R.id.rectangle_4);
        //cart
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = view.findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseFirestore = FirebaseFirestore.getInstance();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Order_succesful.class);
                startActivity(intent);
            }
        });

        getTotalPrice();

        FirestoreRecyclerOptions<Cart_Adapter> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Cart_Adapter>()
                .setQuery(query,Cart_Adapter.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Cart_Adapter, CartViewHolder>(firestoreRecyclerOptions) {
            @SuppressLint({"DefaultLocale", "SetTextI18n"})
            @Override
            protected void onBindViewHolder(CartViewHolder cartViewHolder, int i, Cart_Adapter cart_adapter) {

                cartViewHolder.cart_name.setText(cart_adapter.getName());
                cartViewHolder.cart_category.setText(cart_adapter.getCategory());
                cartViewHolder.cart_price.setText(cart_adapter.getPrice());
                cartViewHolder.quantity.setText(cart_adapter.getQty());
                Glide.with(getContext()).load(cart_adapter.getImage()).into(cartViewHolder.cart_image);

                String use_name = cart_adapter.getName();

                cartViewHolder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String totall = String.valueOf(Integer.parseInt(cartViewHolder.quantity.getText().toString()) + 1);
                        cartViewHolder.quantity.setText(totall);
//                ////ADDING qty FIELD
                        Map<String , Object> quantity = new HashMap<>();
                        quantity.put("Qty",totall);
                        firebaseFirestore.collection("Cart")
                                .document(use_name)
                                .update(quantity);
                        getTotalPrice();
                    }
                });
                cartViewHolder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Integer.parseInt(cartViewHolder.quantity.getText().toString()) <=1){
                            Toast.makeText(getContext(),"Cant be 0", Toast.LENGTH_SHORT).show();
//                            String totall = String.valueOf(Integer.parseInt(cartViewHolder.quantity.getText().toString()) - 1);
//                            cartViewHolder.quantity.setText(totall);
//                            Map<String , Object> quantity = new HashMap<>();
//                            quantity.put("Qty",totall);
//                            firebaseFirestore.collection("Cart")
//                                    .document("SF")
//                                    .update(quantity);
//                            getTotalPrice();
                        }else{
//                            Toast.makeText(getContext(),"Cant be 0", Toast.LENGTH_SHORT).show();
                            String totall = String.valueOf(Integer.parseInt(cartViewHolder.quantity.getText().toString()) - 1);
                            cartViewHolder.quantity.setText(totall);
                            Map<String , Object> quantity = new HashMap<>();
                            quantity.put("Qty",totall);
                            firebaseFirestore.collection("Cart")
                                    .document(use_name)
                                    .update(quantity);
                            getTotalPrice();
                        }
                    }
                });
                cartViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseFirestore.collection("Cart").document(use_name)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
//                                        Toast.makeText(getContext(),"DELETED", Toast.LENGTH_SHORT).show();\
                                        getTotalPrice();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(),"FAILED", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.single_cart_template,parent,false);
//                progressBarr = view1.findViewById(R.id.prog_pic);
//                progressBarr.setVisibility(view1.GONE);
                return new CartViewHolder(view1);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),1, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        //cart
        return view;
    }

    private void getTotalPrice() {
        query = firebaseFirestore.collection("Cart")
                .whereEqualTo("Purchaser", firebaseAuth.getCurrentUser().getUid());
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = new ArrayList<Integer>();
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String price = Objects.requireNonNull(document.get("Price")).toString();
                        String qty = Objects.requireNonNull(document.get("Qty")).toString();
                        list.add(Integer.parseInt(price) * Integer.parseInt(qty));
//                        list.add(Integer.parseInt(Objects.requireNonNull(document.get("Price")).toString()));
                    }
                    recalculate();

                }
            }
        });
    }

    private void recalculate() {
        int cart_sum = mySum(list);
        sum_display.setText(String.valueOf(cart_sum));
    }

    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }

    public static int mySum(List<Integer> list) {
        int sum = 0;
        for (int i: list) {
            sum += i;
        }
        return sum;
    }
}