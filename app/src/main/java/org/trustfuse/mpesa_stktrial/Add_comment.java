package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.trustfuse.mpesa_stktrial.Good_Owner.Good_owner_post;
import org.trustfuse.mpesa_stktrial.Orders.Myorders;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Add_comment extends AppCompatActivity {
    TextView comment_date,commenter;
    EditText comment;
    View post;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        comment = findViewById(R.id.commenter_comments);
        comment_date = findViewById(R.id.comment_date);
        commenter = findViewById(R.id.commenters_name);
        post = findViewById(R.id.post_comment);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firestore.collection("Consumer")
                .document(firebaseAuth.getCurrentUser().getUid());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String namee = documentSnapshot.getString("Consumer name");
                commenter.setText(namee);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed to fetch name" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //end of getting commenter name


        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        comment_date.setText(currentDate);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comments = comment.getText().toString().trim();
                if (comments.isEmpty()){
                    comment.setError("CANT BE EMPTY");
                }else {
//                    Toast.makeText(Add_comment.this, "SENT", Toast.LENGTH_SHORT).show();
                    DocumentReference documentReference = firestore.collection("Comments").document(Myorders.getValue());
                    Map<String,Object> comment = new HashMap<>();

                    comment.put("Comment",comments);
                    comment.put("Commenter",commenter.getText().toString());
                    comment.put("Date",currentDate);
                    comment.put("Item_Name",Myorders.getValue());

                    documentReference.set(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            LottieAlertDialog alertDialog= new LottieAlertDialog.Builder(Add_comment.this, DialogTypes.TYPE_SUCCESS)
                                    .setTitle("SUCCESS")
                                    .setDescription("Comment Added")
                                    .build();
                            alertDialog.setCancelable(true);
                            alertDialog.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            LottieAlertDialog alertDialog= new LottieAlertDialog.Builder(Add_comment.this, DialogTypes.TYPE_ERROR)
                                    .setTitle("FAILURE")
                                    .setDescription("Post not created")
                                    .build();
                            alertDialog.setCancelable(true);
                            alertDialog.show();
                        }
                    });
                }

                }
        });
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}