package com.example.appuser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ToolsFragment extends Fragment {

    private ArrayList<CommanModel> tools_list;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = view.findViewById(R.id.recyclerview_tools);

        tools_list = new ArrayList<>();
        ToolsAdapter toolsAdapter = new ToolsAdapter(tools_list,getContext());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(toolsAdapter);

        firebaseDatabase.getReference().child("AppUser/data/tools")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            CommanModel toolsModel = dataSnapshot.getValue(CommanModel.class);
                            tools_list.add(toolsModel);
                        }
                        toolsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        return  view;
    }
}