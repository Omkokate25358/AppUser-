package com.example.appuser;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ItemDetailsActivity extends AppCompatActivity {

    TextView single_itm_headline, single_itm_price, single_itm_brand, single_itm_category, single_itm_description;
    ImageView single_itm_image;
    FirebaseDatabase database;
    Button addtocart;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        single_itm_brand = findViewById(R.id.single_itm_brand);
        single_itm_headline = findViewById(R.id.single_itm_headline);
        single_itm_price = findViewById(R.id.single_itm_price);
        single_itm_description = findViewById(R.id.single_itm_description);
        single_itm_image = findViewById(R.id.single_itm_image);
        addtocart = findViewById(R.id.addToCart);

        // Initialize Firebase Database
        database = FirebaseDatabase.getInstance();

        // Initialize ProgressDialog
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.setTitle("Processing");
        dialog.setCanceledOnTouchOutside(false);

        // Load item details
        Picasso.get().load(getIntent().getStringExtra("single_itm_image"))
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(single_itm_image);
        single_itm_headline.setText(getIntent().getStringExtra("single_itm_headline"));
        single_itm_price.setText(getIntent().getStringExtra("single_itm_price"));
        single_itm_brand.setText(getIntent().getStringExtra("single_itm_brand"));
        single_itm_description.setText(getIntent().getStringExtra("single_itm_description"));

        // Add to cart button onClickListener
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                // Create a new ItemModel with details from the intent
                ItemModel newModel = new ItemModel();
                newModel.setItm_headline(getIntent().getStringExtra("single_itm_headline"));
                newModel.setItm_price(getIntent().getStringExtra("single_itm_price"));
                newModel.setItm_description(getIntent().getStringExtra("single_itm_description"));

                // Push the new item to the "buy" node in Firebase Database
                database.getReference().child("buy").push().setValue(newModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ItemDetailsActivity.this, "Item added to cart successfully!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ItemDetailsActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
        });
    }
}
