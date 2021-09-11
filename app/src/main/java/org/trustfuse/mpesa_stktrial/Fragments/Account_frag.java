package org.trustfuse.mpesa_stktrial.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;
import com.squareup.picasso.Picasso;

import org.trustfuse.mpesa_stktrial.Authentication.First_Page;
import org.trustfuse.mpesa_stktrial.Main_Menu;
import org.trustfuse.mpesa_stktrial.Orders.Myorders;
import org.trustfuse.mpesa_stktrial.R;
import org.trustfuse.mpesa_stktrial.Single_good;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Account_frag extends Fragment {
    TextView name, username, locationn, phone_number, emaill;
    ImageView image;
    Button update;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    FirebaseAuth firebaseAuth;
    String currentPhotoPath;
    Toolbar toolbar;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final int CAM_PERM = 100 ;
    private static final int CAM_REQ_CODE = 101;
    public static final int GALLERY_RE_CODE = 103;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.account_fragment, container, false);

        name = root.findViewById(R.id.c_name);
        username = root.findViewById(R.id.c_username);
        locationn = root.findViewById(R.id.c_location);
        phone_number = root.findViewById(R.id.c_phone_number);
        emaill = root.findViewById(R.id.c_email);
        image = root.findViewById(R.id.consumer_image);
        update = root.findViewById(R.id.edit_profile);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        toolbar = root.findViewById(R.id.toolbar0);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("My Account");
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("My profile");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setTitle("Account");
//        toolbar.setEnabled(true);

        if (user != null) {
            // User is signed in
            LottieAlertDialog alertDialog= new LottieAlertDialog.Builder(getContext(), DialogTypes.TYPE_LOADING)
                    .setTitle("LOADING DATA")
                    .setDescription("Account data loading...")
                    .build();
            alertDialog.setCancelable(false);
            alertDialog.show();

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Myorders.class);
                    startActivity(intent);
                }
            });


            //Setting of image from Firebase storage
            StorageReference set_Dp = storageReference.child("Consumer/" + firebaseAuth.getCurrentUser().getUid() + "profile_pic");
//        proggg.setVisibility(View.VISIBLE);
            set_Dp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(image);
                    alertDialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
//                Toast.makeText(getContext(), "Pic Update failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            });
            //end of Setting of image from Firebase storage

            DocumentReference documentReference = firebaseFirestore.collection("Consumer").document(firebaseAuth.getCurrentUser().getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    String u_name = documentSnapshot.getString("Consumer username");
                    String p_number = documentSnapshot.getString("Phone Number");
                    String namee = documentSnapshot.getString("Consumer name");
                    String email = documentSnapshot.getString("Consumer email");
                    String location = documentSnapshot.getString("Consumer location");

                    name.setText(namee);
                    username.setText(u_name);
                    phone_number.setText(p_number);
                    emaill.setText(email);
                    locationn.setText(location);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Failed to fetch data" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            ///image view
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(gallery, GALLERY_RE_CODE);
                }
            });
        } else {
            // No user is signed in
            Intent i = new Intent().setClass(getContext(), First_Page.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            requireContext().startActivity(i);
        }

        return root;
    }

    private void askCameraperm() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA} , CAM_PERM);

            ///adding a new line to see if app will ask for permission first
            Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(gallery, GALLERY_RE_CODE);

            //end of adding a new line to see if app will ask for permission first
        }else {
            dispatchTakePictureIntent();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAM_PERM){
            // IF THESE CONDITIONS ARE TRUE THEN CAMERA WILL OPEN
            if (grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }else {
                Toast.makeText(getContext(), "Cam permission is required ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQ_CODE){
            if (resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                image.setImageURI(Uri.fromFile(f));
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(f);
                mediaScanIntent.setData(contentUri);
                this.getActivity().sendBroadcast(mediaScanIntent);
            }

        }
        if (requestCode == GALLERY_RE_CODE){
            if (resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + timeStamp +"."+getFileExt(contentUri);
                Log.d("tag", "onActivityResult: Gallery Image Uri:  " +  imageFileName);
//                profile_pic.setImageURI(contentUri);
                loadImageToFirebase(contentUri);
            }

        }
    }

    private void loadImageToFirebase(final Uri contentUri) {
//        final StorageReference fileRef = storageReference.child("drivers/"+firebaseAuth.getCurrentUser().getUid()+"profile_pic").child(contentUri.getLastPathSegment());
        final  StorageReference fileRef = storageReference.child("Consumer/"+firebaseAuth.getCurrentUser().getUid()+"profile_pic");
        fileRef.putFile(contentUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(mech_main_page.this, "DP uploaded successfully", Toast.LENGTH_LONG).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(image);
//                        Log.d("upload","uploaded succ");
//                        Toast.makeText(driver_Profile.this, "Successfully updated", Toast.LENGTH_SHORT).show();
                        String pichauri = uri.toString();
                        ////ADDING IMAGE URI FIELD
                        Map<String , Object> oyaa = new HashMap<>();
                        oyaa.put("image Uri",pichauri);
                        firebaseFirestore.collection("Consumer").document(firebaseAuth.getCurrentUser().getUid())
                                .set(oyaa, SetOptions.merge());
                        ////END OF ADDING IMAGE URI FIELD
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "DP not uploaded", Toast.LENGTH_LONG).show();
            }
        });

    }
    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContext().getContentResolver();
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
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAM_REQ_CODE);
            }
        }
    }
    ///////////DP TINGS
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.logout_consumer,menu);
//        return super.onCreateOptionsMenu(menu);
//    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        MenuInflater menuInflater =
        inflater.inflate(R.menu.logout_consumer,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sign_out){
            firebaseAuth.signOut();
            Intent i = new Intent().setClass(getContext(), Main_Menu.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            getContext().startActivity(i);
        }
        return true;
    }

}

