package com.example.appuser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.ViewHolderPlants> {

    static ArrayList<CommanModel> plant_list;
    Context plants_context;
    FirebaseDatabase database;
    Firebase firebase;

    public PlantsAdapter(ArrayList<CommanModel> plant_list, Context plants_context){
        this.plant_list = plant_list;
        this.plants_context = plants_context;
        this.database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public PlantsAdapter.ViewHolderPlants onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(plants_context).inflate(R.layout.plants_look,parent,false);
        return new ViewHolderPlants(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantsAdapter.ViewHolderPlants holder, int position) {
        CommanModel model = plant_list.get(position);
        holder.sh_plant_name.setText(model.getName());
        holder.sh_plant_price.setText(model.getPrice());
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_plant_image);
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_plant_image_bg);

        holder.plant_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new SeedsModel with details from the current model
                CommanModel commanModel = new CommanModel();
                commanModel.setName(model.getName());

                // Push the new item to the "buy/seeds" node in Firebase Database
                database.getReference("AppUser").child("buy").push().setValue(commanModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(plants_context, "Item added to cart successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(plants_context, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }

    @Override
    public int getItemCount() {
        return  plant_list.size();
    }

    public class ViewHolderPlants extends RecyclerView.ViewHolder {
        TextView sh_plant_name,sh_plant_price;
        ImageView sh_plant_image,sh_plant_image_bg;
        Button plant_cart;
        public ViewHolderPlants(@NonNull View itemView) {
            super(itemView);
            sh_plant_name = itemView.findViewById(R.id.sh_plant_name);
            sh_plant_image = itemView.findViewById(R.id.sh_plant_image);
            sh_plant_image_bg = itemView.findViewById(R.id.sh_plant_image_bg);
            sh_plant_price = itemView.findViewById(R.id.sh_plant_price);
            plant_cart = itemView.findViewById(R.id.plant_cart);
        }
    }
}
