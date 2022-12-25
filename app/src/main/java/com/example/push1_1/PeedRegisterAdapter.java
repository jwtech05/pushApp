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

public class PeedRegisterAdapter extends RecyclerView.Adapter<PeedRegisterAdapter.CustomViewHolder>{

    int i = 0;
    ArrayList<String> registerPic;
    public PeedRegisterAdapter(ArrayList<String> imageList) {

        registerPic = imageList;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private final ImageView pview;
        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pview = (ImageView) itemView.findViewById(R.id.icon);
        }
    }
    @NonNull
    @NotNull
    @Override
    public PeedRegisterAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mypage_icon,parent,false);
        return new PeedRegisterAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PeedRegisterAdapter.CustomViewHolder holder, int position) {
        String getUri = registerPic.get(i);
        Log.d("REGISTER", getUri);
        Uri uri = Uri.parse(getUri);
        holder.pview.setImageURI(uri);
        i++;
    }

    @Override
    public int getItemCount() {
        return registerPic.size();
    }
}
