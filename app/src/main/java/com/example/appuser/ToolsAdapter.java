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

public class ToolsAdapter extends RecyclerView.Adapter<ToolsAdapter.ViewHolderTools> {

    static ArrayList<CommanModel> tools_list;
    Context tools_context;
    FirebaseDatabase database;
    Firebase firebase;
    public ToolsAdapter(ArrayList<CommanModel> tools_list,Context tools_context){
        this.tools_list = tools_list;
        this.tools_context = tools_context;
        this.database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public ToolsAdapter.ViewHolderTools onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(tools_context).inflate(R.layout.tools_look,parent,false);
        return new ViewHolderTools(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToolsAdapter.ViewHolderTools holder, int position) {
        CommanModel model = tools_list.get(position);
        holder.sh_tool_name.setText(model.getName());
        holder.sh_tool_price.setText(model.getPrice());
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_tool_image);
        Picasso.get().load(model.getImage()).placeholder(R.drawable.ic_launcher_background).into(holder.sh_tool_image_bg);

        holder.tool_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new CommanModel with details from the current model
                CommanModel toolsModel = new CommanModel();
                toolsModel.setName(model.getName());

                // Push the new item to the "buy/tools" node in Firebase Database
                database.getReference("AppUser").child("buy").push().setValue(toolsModel)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(tools_context, "Item added to cart successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(tools_context, "Error occurred!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    @Override
    public int getItemCount() {
        return tools_list.size();
    }

    public class ViewHolderTools extends RecyclerView.ViewHolder {
        TextView sh_tool_name,sh_tool_price;
        ImageView sh_tool_image,sh_tool_image_bg;
        Button tool_cart;
        public ViewHolderTools(@NonNull View itemView) {
            super(itemView);

            sh_tool_name = itemView.findViewById(R.id.sh_tool_name);
            sh_tool_price = itemView.findViewById(R.id.sh_tool_price);
            sh_tool_image = itemView.findViewById(R.id.sh_tool_image);
            sh_tool_image_bg = itemView.findViewById(R.id.sh_tool_image_bg);
            tool_cart = itemView.findViewById(R.id.tool_cart);
        }
    }
}
