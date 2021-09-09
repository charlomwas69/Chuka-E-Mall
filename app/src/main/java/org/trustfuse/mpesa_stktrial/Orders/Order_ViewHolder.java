package org.trustfuse.mpesa_stktrial.Orders;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.trustfuse.mpesa_stktrial.Comments.Comments;
import org.trustfuse.mpesa_stktrial.R;
import org.trustfuse.mpesa_stktrial.Single_good;

import de.hdodenhof.circleimageview.CircleImageView;

public class Order_ViewHolder extends RecyclerView.ViewHolder {
    public TextView category,name,price,comment,status;
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
        status = itemView.findViewById(R.id.status);

//        comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemView.getContext().startActivity(new Intent(itemView.getContext(), Comments.class));
//            }
//        });
    }
}
