package org.trustfuse.mpesa_stktrial.Goods;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.trustfuse.mpesa_stktrial.Categories.Single_Categories;
import org.trustfuse.mpesa_stktrial.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView category,name,price;
    public CircleImageView circleImageView;
    public ProgressBar progressBar;
    public CardView cardView;
    public static String value;
    public static String getValue() {
        return value;
    }
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        category = itemView.findViewById(R.id.category);
        name = itemView.findViewById(R.id.item_name);
        price = itemView.findViewById(R.id.price);
        circleImageView = itemView.findViewById(R.id.item_image);
        cardView = itemView.findViewById(R.id.single_item);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = name.getText().toString();
                itemView.getContext().startActivity(new Intent(itemView.getContext(), Single_Categories.class));
            }
        });
//        progressBar = itemView.findViewById(R.id.prog_pic);
    }
}
