package com.example.appuser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appuser.databinding.ActivitySingUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SingUpActivity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;
    ActivitySingUpBinding binding;
    PrefencesManager prefencesManager;
    String encodedImage;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;

    Uri user_ImageUri;

    TextView tv_user_name, tv_user_emial,tv_user_pass;

    ImageView userImage;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySingUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefencesManager=new PrefencesManager(getApplicationContext());

        tv_user_emial=findViewById(R.id.inputEmailS);
        tv_user_name=findViewById(R.id.inputName);
        tv_user_pass=findViewById(R.id.inputPasssword);
        userImage=findViewById(R.id.imageProfile);
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("please Wait");
        dialog.setCancelable(false);
        dialog.setTitle("Uploading");
        dialog.setCanceledOnTouchOutside(false);

        binding.imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();

            }
        });

        binding.btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                    final StorageReference reference = firebaseStorage.getReference().child("AppUser/user")
                            .child(System.currentTimeMillis() + "");
                    reference.putFile(user_ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
//                                toolModel model = new toolModel();
                                    UserModel model = new UserModel(uri.toString(),tv_user_name.getText().toString(),tv_user_emial.getText().toString(),tv_user_pass.getText().toString());
                                    model.setImage(uri.toString());
                                    model.setName(tv_user_name.getText().toString());
                                    model.setEmail(tv_user_emial.getText().toString());
                                    model.setPassword(tv_user_pass.getText().toString());


                                    database.getReference().child("AppUser/user").push().setValue(model)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {


                                                    Toast.makeText(getApplicationContext(), "Upload !!!!!", Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                }
                            });
                        }
                    });
                }



        });

        binding.singIntxt.setOnClickListener(v ->
                startActivity(new Intent(SingUpActivity.this, SingInActivity.class)));



    }


    public void showToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    public void singUp(){


    }
//    public void singUp() {
//        loading(true);
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference usersRef = database.getReference("users"); // Assuming "users" is your desired node in the database
//
//        String name = binding.inputName.getText().toString();
//        String email = binding.inputEmail.getText().toString();
//        String password = binding.inputPasssword.getText().toString();
//
//
//        User newUser = new User(name, email, password, encodedImage); // Assuming you have a User class representing your data structure
//
//        usersRef.push().setValue(newUser)
//                .addOnSuccessListener(aVoid -> {
//                    loading(false);
//                    prefencesManager.putBoolean(Constant.KEY_IS_SING_IN, true);
//                    prefencesManager.putString(Constant.KEY_NAME, name);
//                    prefencesManager.putString(Constant.KEY_IMAGE, encodedImage);
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                })
//                .addOnFailureListener(exception -> {
//                    loading(false);
//                    showToast(exception.getMessage());
//                });
//    }






    private Boolean isValidSingUpDetails() {
        boolean a=false;
        if (encodedImage == null) {
            showToast("Select Profile Image");
            return false;
        } else if (binding.inputName.getText().toString().trim().isEmpty()) {
            showToast("Enter Name");
            return false;
        } else if (binding.inputEmailS.getText().toString().trim().isEmpty()) {
            showToast("Enter email");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmailS.getText().toString()).matches()) {
            showToast("Enter valid Email ID");
        } else if (binding.inputPasssword.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else if (binding.inputConfirmPasssword.getText().toString().trim().isEmpty()) {
            showToast("Enter password");
            return false;
        } else if (!(binding.inputPasssword.getText().toString()).equals(binding.inputConfirmPasssword.getText().toString())) {
            showToast("Password not match");
            return false;
        } else {
            a=true;
        }
        return a;
    }



    private void UploadImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            user_ImageUri = data.getData();
            userImage.setImageURI(user_ImageUri);
        }
    }
}
