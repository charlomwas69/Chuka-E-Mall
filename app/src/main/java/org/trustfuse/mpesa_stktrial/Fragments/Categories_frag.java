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

import org.trustfuse.mpesa_stktrial.Categories.CategoriesViewHolder;
import org.trustfuse.mpesa_stktrial.Categories.Categories_Adapter;
import org.trustfuse.mpesa_stktrial.R;

public class Categories_frag extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirestoreRecyclerAdapter<Categories_Adapter, CategoriesViewHolder> adapter;
    ProgressBar progressBarr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.category_fragment,container,false);
        View view = inflater.inflate(R.layout.category_fragment,container,false);

        progressBarr = view.findViewById(R.id.category_progress);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = view.findViewById(R.id.category_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseFirestore = FirebaseFirestore.getInstance();
//        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);

        Query query = firebaseFirestore.collection("Categories");

        FirestoreRecyclerOptions<Categories_Adapter> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Categories_Adapter>()
                .setQuery(query,Categories_Adapter.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Categories_Adapter, CategoriesViewHolder>(firestoreRecyclerOptions) {
            @SuppressLint("DefaultLocale")
            @Override
            protected void onBindViewHolder(CategoriesViewHolder categoriesViewHolder, int i, Categories_Adapter categories_adapter) {

                categoriesViewHolder.categoryy.setText(categories_adapter.getCategory());
                Glide.with(getContext()).load(categories_adapter.getImage()).into(categoriesViewHolder.circleImageVieww);
                progressBarr.setVisibility(View.GONE);
            }
            @NonNull
            @Override
            public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.single_category_template,parent,false);
//                progressBarr = view1.findViewById(R.id.prog_pic);
//                progressBarr.setVisibility(view1.GONE);
                return new CategoriesViewHolder(view1);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        return view;
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
