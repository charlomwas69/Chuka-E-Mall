package org.trustfuse.mpesa_stktrial.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidstudy.daraja.Daraja;
import com.androidstudy.daraja.DarajaListener;
import com.androidstudy.daraja.model.AccessToken;
import com.androidstudy.daraja.model.LNMExpress;
import com.androidstudy.daraja.model.LNMResult;
import com.androidstudy.daraja.util.TransactionType;
import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.trustfuse.mpesa_stktrial.Cart.CartViewHolder;
import org.trustfuse.mpesa_stktrial.Cart.Cart_Adapter;
import org.trustfuse.mpesa_stktrial.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cart_frag extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirestoreRecyclerAdapter<Cart_Adapter, CartViewHolder> adapter;
    TextView sum_display,freeshippin;
    ArrayList<Integer> list;
    View next;
    Daraja daraja;
    Query query;
    String p_number;
    String use_name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.cart_fragment,container,false);
        View view = inflater.inflate(R.layout.cart_fragment,container,false);
        recyclerView = view.findViewById(R.id.cart_recycler);
        sum_display = view.findViewById(R.id.total);
        next = view.findViewById(R.id.rectangle_4);
        freeshippin = view.findViewById(R.id.free_domestic_shipping);
        //cart
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = view.findViewById(R.id.cart_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReferencee = firebaseFirestore.collection("Consumer").document(firebaseAuth.getCurrentUser().getUid());
        documentReferencee.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                 p_number = Objects.requireNonNull(documentSnapshot.getString("Phone Number")).substring(1);
//                freeshippin.setText(p_number);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Failed to fetch data" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //start
        //Init Daraja
        //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
        daraja = Daraja.with("ZW2P0zyZPsqfQhqyvglc2Rmd7ThBqG16", "J5AAeNAgmQ9ArAA2", new DarajaListener<AccessToken>() {
            @Override
            public void onResult(@NonNull AccessToken accessToken) {
                Log.i(Cart_frag.this.getClass().getSimpleName(), accessToken.getAccess_token());
//                Toast.makeText(getContext(), "TOKEN : " + accessToken.getAccess_token(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Log.e(Cart_frag.this.getClass().getSimpleName(), error);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext(), Order_succesful.class);
//                startActivity(intent);
                //TODO :: REPLACE WITH YOUR OWN CREDENTIALS  :: THIS IS SANDBOX DEMO
                LNMExpress lnmExpress = new LNMExpress(
                        "174379",
                        "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919",  //https://developer.safaricom.co.ke/test_credentials
                        TransactionType.CustomerBuyGoodsOnline, // TransactionType.CustomerPayBillOnline  <- Apply any of these two
                        sum_display.getText().toString(),
                        "254708374149",
                        "174379",
                        p_number,
                        "http://mycallbackurl.com/checkout.php",
                        "001ABC",
                        "Goods Payment"
                );

                //This is the
                daraja.requestMPESAExpress(lnmExpress,
                        new DarajaListener<LNMResult>() {
                            @Override
                            public void onResult(@NonNull LNMResult lnmResult) {
                                Log.i(Cart_frag.this.getClass().getSimpleName(), lnmResult.ResponseDescription);
                                updatepaidfield();
                            }

                            @Override
                            public void onError(String error) {
                                Log.i(Cart_frag.this.getClass().getSimpleName(), error);
                            }
                        }
                );
                sum_display.setText("0");
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

                 use_name = cart_adapter.getName();

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

    private void updatepaidfield() {
//        Map<String , Object> quantity = new HashMap<>();
//        quantity.put("Paid","Yes");
//        firebaseFirestore.collection("Cart")
//                .document("arsenal")
//                .update(quantity);
        Query queryy = firebaseFirestore.collection("Cart")
                .whereEqualTo("Purchaser", firebaseAuth.getCurrentUser().getUid())
                .whereEqualTo("Paid","no");
        queryy.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
//                    listt = new ArrayList<Integer>();
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String paidd = Objects.requireNonNull(document.get("Paid")).toString();
//                        Toast.makeText(getContext(), document.getId(), Toast.LENGTH_LONG).show();
                        Map<String , Object> quantity = new HashMap<>();
                        quantity.put("Paid","Yes");
                        firebaseFirestore.collection("Cart")
                            .document(document.getId())
                            .update(quantity);
                        recalculate();
//                        listt.add(Integer.parseInt(paidd));
//                        list.add(Integer.parseInt(Objects.requireNonNull(document.get("Price")).toString()));
                    }
//                    recalculate();

                }
            }
        });

    }

    private void getTotalPrice() {
        query = firebaseFirestore.collection("Cart")
                .whereEqualTo("Purchaser", firebaseAuth.getCurrentUser().getUid())
                .whereEqualTo("Paid","no");
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