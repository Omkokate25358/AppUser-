package com.example.appuser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlantFragment extends Fragment {

    private ArrayList<CommanModel> plant_list;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;

    private Context mContext;
    TextView tv1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_plant, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = view.findViewById(R.id.recyclerview_plant);

        tv1=view.findViewById(R.id.tv1);
        String value=retrieveFromSharedPreferences();
        tv1.setText(value);

        plant_list = new ArrayList<>();
        PlantsAdapter plantsAdapter = new PlantsAdapter(plant_list,getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(plantsAdapter);

        firebaseDatabase.getReference().child("AppUser/data/plants")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            CommanModel plantsModel = dataSnapshot.getValue(CommanModel.class);
                            plant_list.add(plantsModel);
                        }
                        plantsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        return view;
    }

    private String retrieveFromSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        return preferences.getString("email", "");
    }
}