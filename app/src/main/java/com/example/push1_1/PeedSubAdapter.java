package com.example.push1_1;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PeedSubAdapter extends RecyclerView.Adapter<PeedSubAdapter.CustomViewHolder>{
    //public static int cnt;
    ArrayList<String> subItems;
    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private final ImageView pview;
        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pview = (ImageView) itemView.findViewById(R.id.multiPicture);
        }
    }

    public PeedSubAdapter(ArrayList<String> subItem) {

        this.subItems = subItem;
    }

    @NonNull
    @NotNull
    @Override
    public PeedSubAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_peed_multi_image,parent,false);
        return new PeedSubAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PeedSubAdapter.CustomViewHolder holder, int position) {
        String strUri = subItems.get(position);
        //Log.d("URI", strUri);
        Uri uri = Uri.parse(strUri);

        holder.pview.setImageURI(uri);
    }

    @Override
    public int getItemCount() {

        return subItems.size();

    }

}
