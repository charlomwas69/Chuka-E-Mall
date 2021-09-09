package org.trustfuse.mpesa_stktrial.Goods;

import android.content.Intent;
import android.view.View;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.trustfuse.mpesa_stktrial.Add_comment;
import org.trustfuse.mpesa_stktrial.Categories.Single_Categories;
import org.trustfuse.mpesa_stktrial.R;
import org.trustfuse.mpesa_stktrial.Single_good;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyViewHolder extends RecyclerView.ViewHolder{
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
        progressBar = itemView.findViewById(R.id.prog_pic);

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
