package org.trustfuse.mpesa_stktrial;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Home_frag extends Fragment {

    RecyclerView recyclerView;
    List<String> category;
    List<String> name;
    List<String> price;
    List<Integer> images;
    Adapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.home_fragment,container,false);
        View view = inflater.inflate(R.layout.home_fragment,container,false);

        //////test data
        category = new ArrayList<>();
        name = new ArrayList<>();
        price = new ArrayList<>();
        images = new ArrayList<>();

        category.add("fist item");
        category.add("second item");
        category.add("third item");
        category.add("fourth item");

        name.add("fist name");
        name.add("second name");
        name.add("third name");
        name.add("fourth name");

        price.add("price 1");
        price.add("price 2");
        price.add("price 3");
        price.add("price 4");

        images.add(R.drawable.mpesa_logo);
        images.add(R.drawable.big);
        images.add(R.drawable.add_image);
        images.add(R.drawable.logo);


        adapter = new Adapter(getContext(),images,category,name,price);

        ////end of test data

        recyclerView = view.findViewById(R.id.recyler);
        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        ///adapter


        return view;
    }
}
