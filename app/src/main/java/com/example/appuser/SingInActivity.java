package com.example.appuser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appuser.databinding.ActivitySingInBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class SingInActivity extends AppCompatActivity {

    ActivitySingInBinding binding;
    PrefencesManager prefencesManager;
    private static final String PREF_KEY_TOKEN = "token";
    private SharedPreferences sharedPreferences;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySingInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        prefencesManager =new PrefencesManager(getApplicationContext());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("AppUser/user");

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        if (sharedPreferences.contains(PREF_KEY_TOKEN)) {
            startActivity(new Intent(SingInActivity.this, MainActivity.class));
            finish();
        } else {
            setListener();
        }
    }

    public void setListener(){
        binding.btnCreateNewAccount.setOnClickListener(v ->
                startActivity(new Intent(SingInActivity.this, SingUpActivity.class)));

        binding.btnSingIn.setOnClickListener(v->{
            if(isValidDetails()){
                singIn();
            }
        });

    }

   public void singIn(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again whenever data at this location is updated.
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                    User user = snapshot.getValue(User.class);



                    String username= user.getEmail();
                    String password= user.getPassword();


                    if(username.equals(binding.inputEmailS.getText().toString())){

                        if (password.equals(binding.inputPasssword.getText().toString())){
                            String token = UUID.randomUUID().toString();

                            // Save the token locally
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(PREF_KEY_TOKEN, token);
                            shareEmailID(binding.inputEmailS.getText().toString());
                            editor.apply();

                            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                            Toast.makeText(SingInActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }

                    // Set email and password to TextViews
//                    binding.emailTextView .setText(user.getEmail());
//                    binding.passwordTextView.setText(user.getPassword());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Failed to read value
                Log.w("MainActivity", "Failed to read value.", databaseError.toException());
            }
        });
    }

    private void shareEmailID(String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", value);
        editor.apply();
    }

    public void loading(Boolean isLoading){
        if(isLoading){
            binding.btnSingIn.setVisibility(View.INVISIBLE);
            binding.progessBar.setVisibility(View.VISIBLE);
        }else {
            binding.btnSingIn.setVisibility(View.VISIBLE);
            binding.progessBar.setVisibility(View.INVISIBLE);

        }
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public Boolean isValidDetails(){

        if(binding.inputEmailS.getText().toString().isEmpty()){
            showToast("Fill this filed");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmailS.getText().toString()).matches()) {
            showToast("Invalid Emial");
            return  false;
        } else if (binding.inputPasssword.getText().toString().isEmpty()) {
            showToast("Fill this Field");
            return false;
        }   else {
            return true;
        }


    }
}