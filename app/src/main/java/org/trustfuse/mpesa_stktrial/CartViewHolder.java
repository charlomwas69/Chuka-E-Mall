package org.trustfuse.mpesa_stktrial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartViewHolder extends RecyclerView.ViewHolder {
    public FirebaseFirestore firestore = FirebaseFirestore.getInstance();
   public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public CircleImageView cart_image;
    public TextView cart_category,cart_name,cart_price,quantity;
    public RelativeLayout add,remove;
    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

        cart_image = itemView.findViewById(R.id.cart_image);
        cart_category = itemView.findViewById(R.id.categoty_in_cart);
        cart_name = itemView.findViewById(R.id.name_in_cart);
        cart_price = itemView.findViewById(R.id.price_in_cart);
        quantity = itemView.findViewById(R.id.quantity);
        add = itemView.findViewById(R.id.group_29);
        remove = itemView.findViewById(R.id.group_30);
    }
}
