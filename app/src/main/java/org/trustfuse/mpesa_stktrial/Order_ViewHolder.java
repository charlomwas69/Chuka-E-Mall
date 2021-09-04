package org.trustfuse.mpesa_stktrial;

import android.content.Intent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.trustfuse.mpesa_stktrial.Authentication.Login;

import de.hdodenhof.circleimageview.CircleImageView;

public class Order_ViewHolder extends RecyclerView.ViewHolder {
    public TextView category,name,price,comment;
    public CircleImageView circleImageView;
    public static String value;
    public static String getValue() {
        return value;
    }
    public Order_ViewHolder(@NonNull View itemView) {
        super(itemView);
        category = itemView.findViewById(R.id.order_category);
        name = itemView.findViewById(R.id.order_name);
        price = itemView.findViewById(R.id.order_price);
        circleImageView = itemView.findViewById(R.id.order_image);
        comment = itemView.findViewById(R.id.comment);

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemView.getContext().startActivity(new Intent(itemView.getContext(), Comments.class));
            }
        });
    }
}
