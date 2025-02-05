package com.example.appuser;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadItemActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;


    TextView itm_upl_headline, itm_upl_description, itm_upl_price, itm_upl_productBrand, itm_upl_category;
    ImageView itm_uploadbtn, itm_productImage;
    Button itm_upl_submit;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private ProgressDialog dialog;

    RelativeLayout relativeLayout;

    Uri itm_ImageUri;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_item);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        dialog= new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("please Wait");
        dialog.setCancelable(false);
        dialog.setTitle("Uploading");
        dialog.setCanceledOnTouchOutside(false);

        itm_upl_headline = findViewById(R.id.itm_upl_headline);
        itm_upl_description = findViewById(R.id.itm_upl_description);
        itm_upl_price = findViewById(R.id.itm_upl_price);

        itm_uploadbtn = findViewById(R.id.itm_uploadbtn);
        itm_productImage = findViewById(R.id.itm_productImage);

        itm_upl_submit = findViewById(R.id.itm_upl_submit);

        relativeLayout = findViewById(R.id.itm_relative);


        itm_uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
                relativeLayout.setVisibility(View.VISIBLE);
                itm_uploadbtn.setVisibility(View.GONE);

            }
        });

        itm_upl_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                final StorageReference reference = firebaseStorage.getReference().child("plant_section")
                        .child(System.currentTimeMillis() + "");
                reference.putFile(itm_ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                ItemModel model = new ItemModel();
                                model.setItm_productImage(uri.toString());
                                model.setItm_headline(itm_upl_headline.getText().toString());
                                model.setItm_price(itm_upl_price.getText().toString());
                                model.setItm_description(itm_upl_description.getText().toString());


                                database.getReference().child("plant_section").push().setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                                Toast.makeText(UploadItemActivity.this, "Upload !!!!!", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();

                                            }

                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialog.dismiss();
                                                Toast.makeText(UploadItemActivity.this, "Error", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        });
                    }
                });

            }
        });
    }

    private void UploadImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            itm_ImageUri = data.getData();
            itm_productImage.setImageURI(itm_ImageUri);
        }
    }
}