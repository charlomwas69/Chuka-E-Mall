package org.trustfuse.mpesa_stktrial.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;
import org.trustfuse.mpesa_stktrial.Goods.Goods_Adapter;
import org.trustfuse.mpesa_stktrial.Goods.MyViewHolder;
import org.trustfuse.mpesa_stktrial.R;

import java.util.ArrayList;
import java.util.Objects;

import static java.lang.String.format;

public class Home_frag extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirestoreRecyclerAdapter<Goods_Adapter, MyViewHolder> adapter;
    ProgressBar progressBarr;
    public static String goodname;
    ArrayList<Integer> list;
    public static String getGoodname() {
        return goodname;
    }
    public static String value;
    public static String getValue() {
        return value;
    }
    Toolbar toolbar;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

        progressBarr = view.findViewById(R.id.main_menu_progress);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = view.findViewById(R.id.recyler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseFirestore = FirebaseFirestore.getInstance();
//        toolbar = view.findViewById(R.id.toolbar_home_frag);
//        toolbar.setTitle("Home");
//        toolbar.setEnabled(true);
        searchView = view.findViewById(R.id.search_item);
//        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);

        Query query = firebaseFirestore.collection("Goods");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String t) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String t) {
//                adapter.getFilter().filter(t);
                tafta(t);
                return true;
            }
        });

        FirestoreRecyclerOptions<Goods_Adapter> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Goods_Adapter>()
                .setQuery(query,Goods_Adapter.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Goods_Adapter, MyViewHolder>(firestoreRecyclerOptions) {
            @SuppressLint("DefaultLocale")
            @Override
            protected void onBindViewHolder(@NotNull MyViewHolder myViewHolder, int i, @NotNull Goods_Adapter goods_adapter) {

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

    private void tafta(String feedback) {
        Query query = firebaseFirestore.collection("Cart")
                .whereEqualTo("Category",feedback )
                .whereEqualTo("Name",feedback);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    list = new ArrayList<Integer>();
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String price = Objects.requireNonNull(document.get("Category")).toString();
                        String qty = Objects.requireNonNull(document.get("Name")).toString();
                        list.add(Integer.parseInt(price) * Integer.parseInt(qty));
                        Toast.makeText(getContext(),document.getId(), Toast.LENGTH_LONG).show();
//                        list.add(Integer.parseInt(Objects.requireNonNull(document.get("Price")).toString()));
                    }

                }
            }
        });
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