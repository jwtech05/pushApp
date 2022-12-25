package com.example.push1_1;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class MypageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final RecyclerViewInterface recyclerViewInterface;

    private final int TYPE_HEADER = 0;
    int takeFlags;
    Uri uri;
    Intent intent;
    Context context;
    String profileImage;
    public static ArrayList<PeedRegisterItems> myPageItem;
    public MypageAdapter(ArrayList<PeedRegisterItems> PeedRegisterItem, RecyclerViewInterface recyclerViewInterface) {
        this.myPageItem = PeedRegisterItem;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;
    public class CustomViewHolder extends RecyclerView.ViewHolder{
        private final ImageView pview;
        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pview = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{
        private final TextView name, email;
        private final CircleImageView profilePic;
        private final ImageView account;
        public HeaderViewHolder(@NonNull @NotNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            //profilePic = (CircleImageView) itemView.findViewById(R.id.profileImage);
            name = (TextView) itemView.findViewById(R.id.name);
            email = (TextView) itemView.findViewById(R.id.tags);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profileImage);
            account = (ImageView) itemView.findViewById(R.id.account);
            profilePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        recyclerViewInterface.onItemClick();
                    }
                }
            });
            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        recyclerViewInterface.accountClick();
                    }
                }
            });
        }
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        RecyclerView.ViewHolder holder;
        View view;

        if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypage_header_item, parent, false);
            holder = new HeaderViewHolder(view, recyclerViewInterface);

        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_mypage_icon, parent, false);
            holder = new CustomViewHolder(view);
        }

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder,
                                 int position) {

        if(holder instanceof HeaderViewHolder){
            String 사용자정보 = PreferenceManager.getString(context, "현재로그인", "현재로그인사용자");
            String 프로필사진 = PreferenceManager.getString(context, "프로필사진", "현재로그인사용자");
            int idx = 사용자정보.indexOf(",");
            int lastIdx = 사용자정보.lastIndexOf(",");
            String name = 사용자정보.substring(idx+1,lastIdx);
            String email = 사용자정보.substring(lastIdx+1);
            if(프로필사진.equals("비어있음")){
                ((HeaderViewHolder) holder).profilePic.setImageURI(Uri.parse("android.resource://"+R.class.getPackage().getName()+"/"+ R.drawable.profile_resize));
            }else {
                profileImage = 프로필사진;
                ((HeaderViewHolder) holder).profilePic.setImageURI(Uri.parse(profileImage));
            }
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            ((HeaderViewHolder) holder).name.setText(name);
            ((HeaderViewHolder) holder).email.setText(email);

        }else {
            CustomViewHolder customViewHolder = (CustomViewHolder) holder;
            String getUri2 = myPageItem.get((myPageItem.size() - 1) - (position-1)).pictures.get(0);
            uri = Uri.parse(getUri2);
            ((CustomViewHolder) holder).pview.setImageURI(uri);
            ((CustomViewHolder) holder).pview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), SinglePeedPage.class);
                    intent.putExtra("num",((myPageItem.size() - 1) - (holder.getBindingAdapterPosition()-1)));
                    intent.putExtra("name", (myPageItem.get((myPageItem.size() - 1) - (holder.getBindingAdapterPosition()-1)).nickName));
                    intent.putExtra("days", (myPageItem.get((myPageItem.size() - 1) - (holder.getBindingAdapterPosition()-1)).days));
                    intent.putExtra("picture", (myPageItem.get((myPageItem.size() - 1) - (holder.getBindingAdapterPosition()-1)).pictures.get(0)));
                    intent.putExtra("comment", (myPageItem.get((myPageItem.size() - 1) - (holder.getBindingAdapterPosition()-1)).comments));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
    @Override
    public int getItemCount() {

        return  myPageItem.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_HEADER;
        else
            return TYPE_ITEM;
    }

}
