package com.example.appuser;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class PlaceOrderActivity extends AppCompatActivity {
    Button placeOrder;

    DatabaseReference databaseReference;
    EditText customerName,customerAddress, customerPincode, customerMobile, customerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_place_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("orders");

        placeOrder = findViewById(R.id.placeOrder);
        customerName = findViewById(R.id.customerName);
        customerAddress = findViewById(R.id.customerAddress);
        customerPincode = findViewById(R.id.customerPincode);
        customerMobile = findViewById(R.id.customerMobile);
        customerEmail = findViewById(R.id.customerEmail);

        placeOrder.setOnClickListener(view -> {

            if (customerName.getText().toString().isEmpty()) {
                customerName.setError("Please Enter Your Name");
            } else if (customerAddress.getText().toString().isEmpty()) {
                customerAddress.setError("Please Enter Your Address");
            } else if (customerPincode.getText().toString().isEmpty()) {
                customerPincode.setError("Please Enter the Pincode");
            } else if (customerMobile.getText().toString().isEmpty()) {
                customerMobile.setError("Please Enter Your Mobile Number");
            } else if (customerEmail.getText().toString().isEmpty()) {
                customerEmail.setError("Please Enter Your Email ID");
            } else if (!customerEmail.getText().toString().contains("@") || !customerEmail.getText().toString().contains(".com")) {
                customerEmail.setError("Please Enter Valid Email ID");
            } else {
                String name = customerName.getText().toString();
                String address = customerAddress.getText().toString();
                String pincode = customerPincode.getText().toString();
                String mobile = customerMobile.getText().toString();
                String email = customerEmail.getText().toString();

                // Create an Order object
                Order order = new Order(name, address, pincode, mobile, email);

                // Push the Order object to Firebase Database
                databaseReference.push().setValue(order);
                Intent intent = new Intent(PlaceOrderActivity.this, OrderPlacedActivity.class);
                startActivity(intent);

            }
        });



    }
}