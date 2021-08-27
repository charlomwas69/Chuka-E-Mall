package org.trustfuse.mpesa_stktrial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_frag extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirestoreRecyclerAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.home_fragment,container,false);
        final View[] view = {inflater.inflate(R.layout.home_fragment, container, false)};

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView = view[0].findViewById(R.id.recyler);
        recyclerView.setHasFixedSize(true);

        Query query = firebaseFirestore.collection("Goods");

        FirestoreRecyclerOptions<Goods_Adapter> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Goods_Adapter>()
                .setQuery(query,Goods_Adapter.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Goods_Adapter, Goods_ViewHolder>(firestoreRecyclerOptions) {

            @NonNull
            @Override
            public Goods_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                view[0] = LayoutInflater.from(getContext()).inflate(R.layout.single_item_template,parent,false);
                return new Goods_ViewHolder(view[0]);
            }

            @Override
            protected void onBindViewHolder(Goods_ViewHolder goods_viewHolder, int i, Goods_Adapter goods_adapter) {
                goods_viewHolder.i_name.setText(goods_adapter.getName());
                goods_viewHolder.i_category.setText(goods_adapter.getCategory());
                goods_viewHolder.i_price.setText(goods_adapter.getPrice() + "");
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);


        return view[0];
    }

    private static class Goods_ViewHolder extends RecyclerView.ViewHolder {
        TextView i_name,i_category,i_price;
        CircleImageView image;

        public Goods_ViewHolder(@NonNull View itemView) {
            super(itemView);

            i_name = itemView.findViewById(R.id.item_name);
            i_category = itemView.findViewById(R.id.item_category);
            i_price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.item_image);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
