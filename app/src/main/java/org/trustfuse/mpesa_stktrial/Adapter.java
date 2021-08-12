package org.trustfuse.mpesa_stktrial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Integer> image;
    List<String> category;
    List<String> name;
    List<String> price;
    LayoutInflater layoutInflater;


    public Adapter(Context context, List<Integer> image,List<String> category,List<String> name, List<String> price){
        this.image = image;
        this.category =  category;
        this.name = name;
        this.price = price;
        this.layoutInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.single_item_template,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.category.setText(category.get(position));
        holder.name.setText(name.get(position));
        holder.price.setText(price.get(position));
        holder.imageView.setImageResource(Integer.parseInt(String.valueOf(image.get(position))));
    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    ///constructor
    public static class ViewHolder extends RecyclerView.ViewHolder{
        EditText category;
        EditText name;
        EditText price;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            name = itemView.findViewById(R.id.item_name);
            price = itemView.findViewById(R.id.price);
            imageView = itemView.findViewById(R.id.item_image);

        }
    }
}
