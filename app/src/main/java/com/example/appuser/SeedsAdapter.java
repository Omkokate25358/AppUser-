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
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SeedsAdapter extends RecyclerView.Adapter<SeedsAdapter.ViewHolderSeeds> {

    private ArrayList<CommanModel> seed_list;
    private Context seed_context;
    private FirebaseDatabase database;

    public SeedsAdapter(ArrayList<CommanModel> seed_list, Context seed_context) {
        this.seed_list = seed_list;
        this.seed_context = seed_context;
        this.database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public ViewHolderSeeds onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(seed_context).inflate(R.layout.seeds_look, parent, false);
        return new ViewHolderSeeds(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSeeds holder, int position) {
        CommanModel model = seed_list.get(position);
        holder.sh_seed_name.setText(model.getName());
        holder.sh_seed_price.setText(model.getPrice());
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_seed_image);
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_seed_image_bg);

        holder.seed_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new SeedsModel with details from the current model
                CommanModel seedsModel = new CommanModel();
                seedsModel.setName(model.getName());

                // Push the new item to the "buy/seeds" node in Firebase Database
                database.getReference("AppUser").child("buy").push().setValue(seedsModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(seed_context, "Item added to cart successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(seed_context, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return seed_list.size();
    }

    public static class ViewHolderSeeds extends RecyclerView.ViewHolder {
        TextView sh_seed_name, sh_seed_price;
        ImageView sh_seed_image, sh_seed_image_bg;
        Button seed_cart;

        public ViewHolderSeeds(@NonNull View itemView) {
            super(itemView);
            sh_seed_name = itemView.findViewById(R.id.sh_seed_name);
            sh_seed_price = itemView.findViewById(R.id.sh_seed_price);
            sh_seed_image = itemView.findViewById(R.id.sh_seed_image);
            sh_seed_image_bg = itemView.findViewById(R.id.sh_seed_image_bg);
            seed_cart = itemView.findViewById(R.id.seed_cart);
        }
    }
}
