package org.trustfuse.mpesa_stktrial;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartViewHolder extends RecyclerView.ViewHolder {

    public ImageView cart_image;
    public TextView cart_category,cart_name,cart_price;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        cart_image = itemView.findViewById(R.id.cart_image);
        cart_category = itemView.findViewById(R.id.categoty_in_cart);
        cart_name = itemView.findViewById(R.id.name_in_cart);
        cart_price = itemView.findViewById(R.id.price_in_cart);

    }
}
