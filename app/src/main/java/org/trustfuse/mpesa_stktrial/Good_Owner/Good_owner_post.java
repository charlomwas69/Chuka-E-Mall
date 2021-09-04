package org.trustfuse.mpesa_stktrial.Good_Owner;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

import org.trustfuse.mpesa_stktrial.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("deprecation")
public class Good_owner_post extends AppCompatActivity {

    EditText item_name,item_category,item_price;
    EditText item_description;
    StorageReference storageReference;
    FirebaseFirestore firestore;
    Button post;
    CircleImageView circleImageView;
    Uri contentUri;
    CardView cardView;

    private static final int CAM_PERM = 100 ;
    private static final int CAM_REQ_CODE = 101;
    public static final int GALLERY_RE_CODE = 103;
    String currentPhotoPath;
    LottieAlertDialog alertDialog1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_owner_post);
        circleImageView = findViewById(R.id.good_image);
        item_name = findViewById(R.id.i_name);
        item_category = findViewById(R.id.item_category);
        item_price = findViewById(R.id.item_price);
        item_description = findViewById(R.id.item_description);
        post = findViewById(R.id.post_item);
        storageReference = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        cardView = findViewById(R.id.card_view2);



        cardView.setVisibility(View.GONE);
//        post.setVisibility(View.GONE);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_RE_CODE);
//                Toast.makeText(getApplicationContext(),"Fuck",Toast.LENGTH_LONG).show();
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postproduct();
            }
        });
    }



    //end of oncreate
    private void postproduct() {

        String name = item_name.getText().toString();
        String category = item_category.getText().toString();
        String price = (item_price.getText().toString());
        String description = item_description.getText().toString();

        if (TextUtils.isEmpty(name)){
            item_name.setError("Item name is required");
            if (TextUtils.isEmpty(category)){
                item_category.setError("Category cant be empty");
                if (TextUtils.isEmpty(price.toString())){
                    item_price.setError("Price is required");
                }
            }

            if (TextUtils.isEmpty(description)){
                item_description.setError("Description is required(min 5 lines)");
            }
        }
        else {
            alertDialog1= new LottieAlertDialog.Builder(Good_owner_post.this, DialogTypes.TYPE_LOADING)
                    .setTitle("UPLOADING...")
                    .setDescription("Posting your Product")
                    .build();
            alertDialog1.setCancelable(false);
            alertDialog1.show();

            DocumentReference documentReference = firestore.collection("Goods").document(name);
            Map<String,Object> goods = new HashMap<>();

            goods.put("Category",category);
            goods.put("Name",name);
            goods.put("Price",price);
            goods.put("Description",description);
            documentReference.set(goods).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    loadImageToFirebase(contentUri);

//                    alertDialog1.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    LottieAlertDialog alertDialog= new LottieAlertDialog.Builder(Good_owner_post.this, DialogTypes.TYPE_ERROR)
                            .setTitle("FAILURE")
                            .setDescription("Post not created")
                            .build();
                    alertDialog.setCancelable(true);
                    alertDialog.show();
                }
            });
        }
    }
    ////end of posting
    /////////////// DP TINGS
    private void askCameraperm() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA} , CAM_PERM);
        }else {
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAM_PERM) {
            // IF THESE CONDITIONS ARE TRUE THEN CAMERA WILL OPEN
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Cam permission is required ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQ_CODE){
            if (resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                circleImageView.setImageURI(Uri.fromFile(f));
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);
            }

        }
        if (requestCode == GALLERY_RE_CODE){
            if (resultCode == Activity.RESULT_OK){
                contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp +"."+getFileExt(contentUri);
//                Log.d("tag", "onActivityResult: Gallery Image Uri:  " +  imageFileName);
//                Toast.makeText(getApplicationContext(),"onActivityResult: Gallery Image Uri:\" " + imageFileName,Toast.LENGTH_LONG).show();
                circleImageView.setImageURI(contentUri);
                cardView.setVisibility(View.VISIBLE);
//                loadImageToFirebase(contentUri);
            }

        }
    }

    private void loadImageToFirebase(final Uri contentUri) {
//        final StorageReference fileRef = storageReference.child("drivers/"+firebaseAuth.getCurrentUser().getUid()+"profile_pic").child(contentUri.getLastPathSegment());
        final  StorageReference fileRef = storageReference.child("Images/"+ item_name.getText().toString());
        fileRef.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String pichauri = uri.toString();
                        alertDialog1.dismiss();
                        ////ADDING IMAGE URI FIELD
                        Map<String , Object> oyaa = new HashMap<>();
                        oyaa.put("image_uri",pichauri);
                        firestore.collection("Goods").document(item_name.getText().toString())
                                .set(oyaa, SetOptions.merge());
                        item_name.setText("");
                        item_description.setText("");
                        item_price.setText("");
                        item_category.setText("");
                        circleImageView.setImageURI(Uri.parse(""));

                        LottieAlertDialog alertDialog= new LottieAlertDialog.Builder(Good_owner_post.this, DialogTypes.TYPE_SUCCESS)
                                .setTitle("SUCCESS")
                                .setDescription("Your Good was succesfully posted")
                                .build();
                        alertDialog.setCancelable(true);
                        alertDialog.show();
                        cardView.setVisibility(View.GONE);
//                        alertDialog1.dismiss();
//                        Snackbar.make(getCurrentFocus(),"TASK COMPLETED SUCCESSFULLY",3500).show();

                        ////END OF ADDING IMAGE URI FIELD
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Good_owner_post.this, "DP not uploaded" +e.getMessage(), Toast.LENGTH_LONG).show();
                LottieAlertDialog alertDialog= new LottieAlertDialog.Builder(Good_owner_post.this, DialogTypes.TYPE_ERROR)
                        .setTitle("FAILURE")
                        .setDescription("Good not posted")
                        .build();
                alertDialog.setCancelable(true);
                alertDialog.show();
            }
        });

    }

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(c.getType(contentUri));
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAM_REQ_CODE);
            }
        }
    }
    ///////////DP TINGS

}