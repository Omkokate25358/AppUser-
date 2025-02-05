package com.example.appuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appuser.databinding.ActivityLoginBinding;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider;

public class Login_Activity extends AppCompatActivity {

    ActivityLoginBinding binding;
    FirebaseAuth uAuth;
    private DatabaseReference mDatabase;

    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FirebaseApp.initializeApp(this);

        uAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("users");


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again whenever data at this location is updated.
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                            User user = snapshot.getValue(User.class);

                            // Display fetched data
                            Toast.makeText(Login_Activity.this, "Data fetched", Toast.LENGTH_SHORT).show();
                            Log.d("MainActivity", "User: " + user.toString());

                            // Set email and password to TextViews
                            binding.emailTextView .setText(user.getEmail());
                            binding.passwordTextView.setText(user.getPassword());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Failed to read value
                        Log.w("MainActivity", "Failed to read value.", databaseError.toException());
                    }
                });

            }
        });




//        binding.btnSingIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("user");
//
//                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.exists()) {
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                String email = snapshot.child("email").getValue(String.class);
//                                String password = snapshot.child("password").getValue(String.class);
//
//                                // Use email and password as needed
//                                Log.d("User Data", "Email: " + email + ", Password: " + password);
//                            }
//                        } else {
//                            Log.d("Data Fetch", "No data available");
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Log.e("Data Fetch", "Error fetching data", databaseError.toException());
//                    }
//                });
//            }
//        });



    }
}