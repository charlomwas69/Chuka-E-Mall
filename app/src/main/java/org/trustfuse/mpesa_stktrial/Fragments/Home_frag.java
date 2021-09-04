package org.trustfuse.mpesa_stktrial.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import org.trustfuse.mpesa_stktrial.Goods.Goods_Adapter;
import org.trustfuse.mpesa_stktrial.Goods.MyViewHolder;
import org.trustfuse.mpesa_stktrial.R;

import static java.lang.String.format;

public class Home_frag extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirestoreRecyclerAdapter<Goods_Adapter, MyViewHolder> adapter;
    ProgressBar progressBarr;
    public static String value;
    FragmentActivity listener;
    public static String getValue() {
        return value;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.home_fragment,container,false);
//        View view = {inflater.inflate(R.layout.home_fragment, container, false)};
        View view = inflater.inflate(R.layout.home_fragment,container,false);

//        progressBarrr = view.findViewById(R.id.wp7progressBar);
        progressBarr = view.findViewById(R.id.main_menu_progress);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = view.findViewById(R.id.recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseFirestore = FirebaseFirestore.getInstance();
//        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);

        Query query = firebaseFirestore.collection("Goods");

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
                Glide.with(getContext()).load(goods_adapter.getImage_uri()).into(myViewHolder.circleImageView);

                progressBarr.setVisibility(View.GONE);
            }
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.single_item_template,parent,false);
                return new MyViewHolder(view1);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        return view;
    }

    @Override
    public void onStart() {
        adapter.startListening();
//        if (dialog != null) { dialog.dismiss(); dialog = null; }
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }
}