package com.example.appuser;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Cart_Fragment extends Fragment {

    private ArrayList<CommanModel> buy_list;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;


    private static final String TAG = "omkokate";
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    private CartAdapter mAdapter;

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        recyclerView = view.findViewById(R.id.recyclerview_cart);

//        buy_list = new ArrayList<>();
//        CartAdapter plantsAdapter = new CartAdapter(buy_list,getContext());
////        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL));
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setAdapter(plantsAdapter);


        //

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize RecyclerView
        mRecyclerView = view.findViewById(R.id.recyclerview_cart);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new CartAdapter(new ArrayList<ModelItem>(), getContext());
        mRecyclerView.setAdapter(mAdapter);

//        firebaseDatabase.getReference().child("AppUser/buy")
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            CommanModel plantsModel = dataSnapshot.getValue(CommanModel.class);
//                            buy_list.add(plantsModel);
//                        }
//                        plantsAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
        fetchDataAndPopulateRecyclerView();

        return view;
    }

    private void fetchDataAndPopulateRecyclerView() {
        mDatabase.child("AppUser/email").child(TAG).child("data").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                        String itemName = itemSnapshot.child("name").getValue(String.class);
                        String itemPrice = itemSnapshot.child("price").getValue(String.class);
                        mAdapter.addItem(new ModelItem(itemName, itemPrice));
                    }
                } else {
                    Log.d(TAG, "No items found.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error fetching data: " + databaseError.getMessage());
            }
        });
    }


}