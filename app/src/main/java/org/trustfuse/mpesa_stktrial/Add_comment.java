package org.trustfuse.mpesa_stktrial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    TextView comment_date,commenter,sentiment;
    EditText the_comment;
    View post,analyse;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);
        the_comment = findViewById(R.id.commenter_comments);
        comment_date = findViewById(R.id.comment_date);
        commenter = findViewById(R.id.commenters_name);
        post = findViewById(R.id.post_comment);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.toolbar_add_comment);
        sentiment = findViewById(R.id.result);
        analyse = findViewById(R.id.analyse);
        toolbar.setTitle("Add comment");
        toolbar.setEnabled(true);
        String comments = the_comment.getText().toString().trim();

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

        analyse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (comments.isEmpty()){
                    the_comment.setError("CANT BE EMPTY");
                }else {
//                    Toast.makeText(Add_comment.this, "SENT", Toast.LENGTH_SHORT).show();
                    if (the_comment.getText().toString().contains("good") ||
                            the_comment.getText().toString().contains("awesome")
                            || the_comment.getText().toString().contains("acceptable")
                            || the_comment.getText().toString().contains("exceptional")
                            || the_comment.getText().toString().contains("excellent")
                            || the_comment.getText().toString().contains("favourable")
                            || the_comment.getText().toString().contains("brilliant")
                            || the_comment.getText().toString().contains("great")
                            || the_comment.getText().toString().contains("marvelous")
                            || the_comment.getText().toString().contains("satisfactory")
                            || the_comment.getText().toString().contains("satisfying")
                            || the_comment.getText().toString().contains("superb")
                            || the_comment.getText().toString().contains("value")
                            || the_comment.getText().toString().contains("wonderful")
                            || the_comment.getText().toString().contains("sterling")
                            || the_comment.getText().toString().contains("worthy")
                            || the_comment.getText().toString().contains("deluxe")
                            || the_comment.getText().toString().contains("honorable")
                            || the_comment.getText().toString().contains("neat")
                            || the_comment.getText().toString().contains("precious")
                            || the_comment.getText().toString().contains("splendid")
                            || the_comment.getText().toString().contains("beautiful")
                            || the_comment.getText().toString().contains("impressive")
                            || the_comment.getText().toString().contains("magnificent")
                            || the_comment.getText().toString().contains("exalted")
                            || the_comment.getText().toString().contains("horrific")
                            || the_comment.getText().toString().contains("delightful")
                            || the_comment.getText().toString().contains("delighted")
                            || the_comment.getText().toString().contains("sweet")
                            || the_comment.getText().toString().contains("mind blowing")
                            || the_comment.getText().toString().contains("delicious")
                            || the_comment.getText().toString().contains("nice")
                            || the_comment.getText().toString().contains("satisfied")
                            || the_comment.getText().toString().contains("fair")) {
                        sentiment.setText("YOUR COMMENT = " + " " + "POSITIVE");
                    } else if (the_comment.getText().toString().contains("bad")
                            || the_comment.getText().toString().contains("awful")
                            || the_comment.getText().toString().contains("nasty")
                            || the_comment.getText().toString().contains("sickening")
                            || the_comment.getText().toString().contains("horrid")
                            || the_comment.getText().toString().contains("nauseating")
                            || the_comment.getText().toString().contains("putrid")
                            || the_comment.getText().toString().contains("gross")
                            || the_comment.getText().toString().contains("sick")
                            || the_comment.getText().toString().contains("crap")
                            || the_comment.getText().toString().contains("crappy")
                            || the_comment.getText().toString().contains("disgusting")
                            || the_comment.getText().toString().contains("distressing")
                            || the_comment.getText().toString().contains("depressing")
                            || the_comment.getText().toString().contains("dreadful")
                            || the_comment.getText().toString().contains("frightful")
                            || the_comment.getText().toString().contains("ghastly")
                            || the_comment.getText().toString().contains("horrendous")
                            || the_comment.getText().toString().contains("horrible")
                            || the_comment.getText().toString().contains("horrifying")
                            || the_comment.getText().toString().contains("shocking")
                            || the_comment.getText().toString().contains("alarming")
                            || the_comment.getText().toString().contains("ugly")) {
                        sentiment.setText("COMMENT  =" + " " + "POSITIVE");

                    }
                }
            }
        });
        //end of getting commenter name
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        comment_date.setText(currentDate);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                if (comments.isEmpty()) {
                    the_comment.setError("CANT BE EMPTY");
                } else {
                    DocumentReference documentReference = firestore.collection("Comments").document(Myorders.getValue());
                    Map<String, Object> comment = new HashMap<>();

                    comment.put("Comment", comments);
                    comment.put("Commenter", commenter.getText().toString());
                    comment.put("Date", currentDate);
                    comment.put("Item_Name", Myorders.getValue());
                    comment.put("Analysis", sentiment.getText().toString());

                    documentReference.set(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            the_comment.setText("");
                            LottieAlertDialog alertDialog = new LottieAlertDialog.Builder(Add_comment.this, DialogTypes.TYPE_SUCCESS)
                                    .setTitle("SUCCESS")
                                    .setDescription("Thank you for the comment")
                                    .build();
                            alertDialog.setCancelable(true);
                            alertDialog.show();
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            LottieAlertDialog alertDialog = new LottieAlertDialog.Builder(Add_comment.this, DialogTypes.TYPE_ERROR)
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