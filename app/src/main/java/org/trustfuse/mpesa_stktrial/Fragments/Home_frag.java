package org.trustfuse.mpesa_stktrial.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.trustfuse.mpesa_stktrial.Goods.Goods_Adapter;
import org.trustfuse.mpesa_stktrial.Goods.MyViewHolder;
import org.trustfuse.mpesa_stktrial.R;
import org.trustfuse.mpesa_stktrial.Single_good;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.lang.String.format;
import static org.checkerframework.checker.units.UnitsTools.s;

public class Home_frag extends Fragment {

    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirestoreRecyclerAdapter<Goods_Adapter, MyViewHolder> adapter;
    D_adapter d_adapter;
    ArrayList<Goods_Adapter> drivers;
    ProgressBar progressBarr;
    public static String goodname;
    public static String getGoodname() {
        return goodname;
    }
    public static String value;
    public static String getValue() {
        return value;
    }
    Toolbar toolbar;
    SearchView searchView;
    String imageuri;

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
        searchView = view.findViewById(R.id.search_item);
        drivers = new ArrayList<>();
//        view.findViewById(R.id.progress_bar).setVisibility(View.GONE);

//        Query query = firebaseFirestore.collection("Goods");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String t) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String t) {
//                adapter.getFilter().filter(t);
                search(t);
                return true;
            }
        });

//        ///trying getting data in another way
        firebaseFirestore.collection("Goods")
                /////LOOK FOR A WAY TO SORT
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String Name = document.get("Name").toString();
                                String Category = document.get("Category").toString();
                                String Price = document.get("Price").toString();
                                String image_uri = document.get("image_uri").toString();
                                String Description = document.get("Description").toString();

                                ///////IMAGE TRIALS
                                final StorageReference set_Dp = storageReference.child("Images/"+ Name);
                                set_Dp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
//                Picasso.get().load(uri).into(profile_pic);
                                        set_Dp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                imageuri = uri.toString();
                                            }
                                        });
                                    }
                                });
                                //////END OF IMAGE TRIALS


                                Log.d("hey","this are"+ document.getData());
//                                drivers.add(new Driver_User(dname,dphone,dtime));
//                                drivers.add(new Goods_Adapter(Name,Category,Price,image_uri,Description));
                                drivers.add(new Goods_Adapter(Category, Description, Name, image_uri, Price));

                            }

                            d_adapter = new D_adapter(getContext(), drivers);
                            recyclerView.setAdapter(d_adapter);
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL,false);
                            recyclerView.setLayoutManager(gridLayoutManager);
                            progressBarr.setVisibility(View.GONE);
//                            recyclerView.setAdapter(new D_adapter(getApplicationContext(),drivers));
                        }
                        else {
                            Toast.makeText(getContext(), "error" + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        // end of trying to get data

        //firestore adapter
//        FirestoreRecyclerOptions<Goods_Adapter> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Goods_Adapter>()
//                .setQuery(query,Goods_Adapter.class)
//                .build();
//        adapter = new FirestoreRecyclerAdapter<Goods_Adapter, MyViewHolder>(firestoreRecyclerOptions) {
//            @SuppressLint("DefaultLocale")
//            @Override
//            protected void onBindViewHolder(@NotNull MyViewHolder myViewHolder, int i, @NotNull Goods_Adapter goods_adapter) {
//
//                myViewHolder.name.setText(goods_adapter.getName());
//                myViewHolder.category.setText(goods_adapter.getCategory());
//                myViewHolder.price.setText(goods_adapter.getPrice());
//                Glide.with(getContext()).load(goods_adapter.getImage_uri()).into(myViewHolder.circleImageView);
//
//                progressBarr.setVisibility(View.GONE);
//            }
//            @NonNull
//            @Override
//            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.single_item_template,parent,false);
//                return new MyViewHolder(view1);
//            }
//        };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2, GridLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(gridLayoutManager);

        return view;
    }

    private void search(String str) {
        ArrayList<Goods_Adapter> mylist = new ArrayList<>();
        for (Goods_Adapter object : drivers){
            if ((object.getName().toLowerCase().contains(str.toLowerCase()) || object.getCategory().contains(str.toLowerCase()))){
                mylist.add(object);
            }
        }
        D_adapter d_adapter = new D_adapter(getContext(), mylist);
        recyclerView.setAdapter(d_adapter);
    }
    
    ///////ADAPTER
    public static class D_adapter extends RecyclerView.Adapter<D_adapter.ViewHolder>{

        private final ArrayList<Goods_Adapter> data;
        private final LayoutInflater layoutInflater;
//    private mech_view_driver mech_view_drivers;

        D_adapter(Context context , ArrayList<Goods_Adapter> data ){
            this.layoutInflater = LayoutInflater.from(context);
            this.data = data;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(mech_view_drivers.getBaseContext());
            View vieww = layoutInflater.inflate(R.layout.single_item_template,parent,false);
            return new ViewHolder(vieww);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //binding textview with data received
            holder.category.setText(data.get(position).getCategory());
            holder.name.setText(data.get(position).getName());
            holder.price.setText(data.get(position).getPrice());
//            holder.description.setText(data.get(position).getDescription());
            Picasso.get().load(data.get(position).getImage_uri()).into(holder.circleImageView);
            //u can set image for each card as well


        }


        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            TextView category,name,price,description;
            CircleImageView circleImageView;
            CardView cardView;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                category = itemView.findViewById(R.id.category);
                name = itemView.findViewById(R.id.item_name);
                price = itemView.findViewById(R.id.price);
//                description = itemView.findViewById(R.id.description);
                circleImageView = itemView.findViewById(R.id.item_image);
                cardView = itemView.findViewById(R.id.single_item);

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        value = name.getText().toString();
                        Intent i = new Intent().setClass(itemView.getContext(), Single_good.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        itemView.getContext().startActivity(i);
                    }
                });
            }
        }

    }

///////END OF ADAPTER
    

//    @Override
//    public void onStart() {
//        adapter.startListening();
////        if (dialog != null) { dialog.dismiss(); dialog = null; }
//        super.onStart();
//    }
//
//
//    @Override
//    public void onStop() {
//        adapter.stopListening();
//        super.onStop();
//    }

}