package org.trustfuse.mpesa_stktrial.Comments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;
import org.trustfuse.mpesa_stktrial.Comments_Adapter;
import org.trustfuse.mpesa_stktrial.Comments_ViewHolder;
import org.trustfuse.mpesa_stktrial.R;
import org.trustfuse.mpesa_stktrial.Single_good;

public class Comments extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    TextView textView;
    FirestoreRecyclerAdapter<Comments_Adapter, Comments_ViewHolder> adapter;
    public static String value;
    public static String getValue() {
        return value;
    }
    public String namey;
    Toolbar toolbar;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        recyclerView = findViewById(R.id.comments_recycler);
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        firebaseFirestore = FirebaseFirestore.getInstance();
        textView = findViewById(R.id.textView2);
        toolbar = findViewById(R.id.toolbar_comments);
        toolbar.setTitle("COMMENTS");
        toolbar.setEnabled(true);

//        Toast.makeText(getApplicationContext(), Single_good.getI_name(), Toast.LENGTH_LONG).show();
        textView.setText(Single_good.getI_name());

        Query query = firebaseFirestore.collection("Comments")
                .whereEqualTo("Item_Name",Single_good.getI_name());

        FirestoreRecyclerOptions<Comments_Adapter> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Comments_Adapter>()
                .setQuery(query,Comments_Adapter.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Comments_Adapter,Comments_ViewHolder>(firestoreRecyclerOptions) {
            @SuppressLint("DefaultLocale")
            @Override
            protected void onBindViewHolder(@NotNull Comments_ViewHolder comments_viewHolder, int i, @NotNull Comments_Adapter comments_adapter) {

               comments_viewHolder.namee.setText(comments_adapter.getCommenter());
               comments_viewHolder.comments.setText(comments_adapter.getComment());
               comments_viewHolder.date.setText(comments_adapter.getDatee());

            }
            @NonNull
            @Override
            public Comments_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.single_comment_template,parent,false);
                return new Comments_ViewHolder(view1);
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),1, GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();
    }


}