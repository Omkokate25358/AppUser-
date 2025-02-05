package com.example.appuser;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolderRepair> {

    static ArrayList<RepairModel> rep_list;
    Context rep_context;
    FirebaseDatabase database;
    Firebase firebase;

    public RepairAdapter(ArrayList<RepairModel> rep_list, Context rep_context) {
        this.rep_list = rep_list;
        this.rep_context = rep_context;
    }
    public static double calculateTotalPrice() {
        double totalPrice = 0.0;
        for (RepairModel item : rep_list) {
            String priceString = item.getItm_price();
            // Check if the priceString is a valid number before conversion
            if (priceString.matches("^-?\\d+(\\.\\d+)?$")) {
                totalPrice += Double.parseDouble(priceString);
            } else {
                // Handle the scenario where the priceString is not a number
                Log.e("ItemAdapter", "Invalid price format for item: " + item.getItm_headline());
            }
        }
        return totalPrice;
    }
    @NonNull
    @Override
    public ViewHolderRepair onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(rep_context).inflate(R.layout.repair_look, parent, false);
        return new ViewHolderRepair(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderRepair holder, int position) {
        RepairModel model = rep_list.get(position);
//        Picasso.get().load(model.getRep_image()).placeholder(R.drawable.ic_launcher_background).into(holder.rep_image);
        holder.rep_hedline.setText(model.getItm_headline());
        holder.rep_discription.setText(model.getItm_description());
        holder.rep_price.setText(model.getItm_price());
//        holder.rep_price_to.setText(model.getRep_price_to());


        holder.itemDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                String headlineToRemove = model.getItm_headline();
                Query query = database.getReference().child("buy").orderByChild("itm_headline").equalTo(headlineToRemove);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(rep_context, "Item removed successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(rep_context, "Error removing item", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }

        });

    }


    @Override
    public int getItemCount() {
        return rep_list.size();
    }

    public class ViewHolderRepair extends RecyclerView.ViewHolder{
        TextView rep_hedline, rep_discription, rep_price;
        ImageView rep_image,itemDelete;
        public ViewHolderRepair(View repairView){
            super(repairView);
            rep_hedline = repairView.findViewById(R.id.rep_headline);
            rep_discription = repairView.findViewById(R.id.rep_discription);
            rep_price = repairView.findViewById(R.id.rep_price);
            itemDelete = repairView.findViewById(R.id.itemDelete);
        }
    }

}
