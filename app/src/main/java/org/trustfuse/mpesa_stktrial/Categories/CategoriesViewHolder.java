package org.trustfuse.mpesa_stktrial.Categories;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.trustfuse.mpesa_stktrial.R;
import org.trustfuse.mpesa_stktrial.Single_good;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {
    public TextView categoryy;
    public CircleImageView circleImageVieww;
    public static String value;
    public static String getValue() {
        return value;
    }

    public CategoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryy = itemView.findViewById(R.id.categoryy);
        circleImageVieww = itemView.findViewById(R.id.category_image);
        circleImageVieww.setFocusable(false);
        circleImageVieww.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = categoryy.getText().toString();
                Intent i = new Intent().setClass(itemView.getContext(), Single_Categories.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                itemView.getContext().startActivity(i);
            }
        });
    }
}
