package org.trustfuse.mpesa_stktrial;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Comments_ViewHolder extends RecyclerView.ViewHolder {
    public TextView date,comments,namee;
    public Comments_ViewHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.datey);
        comments = itemView.findViewById(R.id.commentsy);
        namee = itemView.findViewById(R.id.nameeey);
    }
}
