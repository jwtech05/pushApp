package com.example.push1_1;


import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static com.example.push1_1.TimerPage.timer;

public class PeedAdapter extends RecyclerView.Adapter<PeedAdapter.CustomViewHolder> {
    private Context context;
    private RecyclerView 리사이클러뷰;
    private ArrayList<PeedRegisterItems> mainItems;
    public static boolean flag;
    String strCnt;
    ScaleAnimation scaleAnimation;
    BounceInterpolator bounceInterpolator;//애니메이션이 일어나는 동안의 회수, 속도를 조절하거나 시작과 종료시의 효과를 추가 할 수 있다
    Handler timer;
    public PeedAdapter(ArrayList<PeedRegisterItems> peedInfo) {

        this.mainItems = peedInfo;

    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{

        private final TextView tview, cview, dview, cview2, goodCnt, status;
        CompoundButton button_favorite;
        CircleImageView miniProfile;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            status = (TextView) itemView.findViewById(R.id.workingMark);
            dview = (TextView) itemView.findViewById(R.id.postDate);
            tview = (TextView) itemView.findViewById(R.id.userId);
            cview = (TextView) itemView.findViewById(R.id.postTag);
            cview2 = (TextView) itemView.findViewById(R.id.commentTag);
            goodCnt = (TextView) itemView.findViewById(R.id.goodCnt);
            리사이클러뷰 = (RecyclerView) itemView.findViewById(R.id.pictureRecycler);
            button_favorite = (CompoundButton) itemView.findViewById(R.id.goodButton);
            miniProfile = (CircleImageView) itemView.findViewById(R.id.profileImage);
            scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f);

            scaleAnimation.setDuration(500);
            bounceInterpolator = new BounceInterpolator();
            scaleAnimation.setInterpolator(bounceInterpolator);

        }
    }

    @NonNull
    @NotNull
    @Override
    public PeedAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mypage_item,parent,false);
        return new PeedAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PeedAdapter.CustomViewHolder holder,
                                 int position) {
        //PeedSubAdapter.cnt = (DBPage.picture.size()-1)-position;

        String tag ="";
        strCnt = mainItems.get((mainItems.size()-1)-position).like;
        NpaLinearLayoutManager 리니어매니저 = new NpaLinearLayoutManager(context ,RecyclerView.HORIZONTAL,false);
        리사이클러뷰.setLayoutManager(리니어매니저);
        PeedSubAdapter 리싸이클러어댑터 = new PeedSubAdapter(mainItems.get((mainItems.size()-1)-position).pictures);
        리사이클러뷰.setAdapter(리싸이클러어댑터);
        holder.tview.setText(mainItems.get((mainItems.size()-1)-position).nickName);
        holder.cview2.setText(mainItems.get((mainItems.size()-1)-position).comments);
        holder.dview.setText(mainItems.get((mainItems.size()-1)-position).days);
        holder.goodCnt.setText(strCnt+"Likes");
        for(String x : mainItems.get((mainItems.size()-1)-position).tags) {
            tag += "#" + x + " ";
        }
        holder.cview.setText(tag);
        if(DBPage.imageBitmap != null){
            holder.miniProfile.setImageBitmap(DBPage.imageBitmap);
        }
        if(mainItems.get((mainItems.size()-1)-position).nickName.equals(LoginPage.loginer) && PreferenceManager.getString(리사이클러뷰.getContext(), "할일중", "현재로그인사용자").equals("작동중")){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(TimerPage2.infi) {
                        timer.sendEmptyMessage(0);
                    }
                }
            }).start();

            timer = new Handler(Looper.myLooper()){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);

                    if(msg.what == 0){
                            holder.status.setText(String.format("%02d:%02d:%02d",
                                    TimeUnit.MILLISECONDS.toHours(TimerService.millis),
                                    TimeUnit.MILLISECONDS.toMinutes(TimerService.millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(TimerService.millis)),
                                    TimeUnit.MILLISECONDS.toSeconds(TimerService.millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(TimerService.millis))));
                            removeMessages(0);
                    }
                }
            };

        }
        holder.button_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buttonView.startAnimation(scaleAnimation);
                if(isChecked){
                    int cnt = Integer.parseInt(mainItems.get((mainItems.size()-1)-position).like);
                    cnt += 1;
                    strCnt = String.valueOf(cnt);
                }else{
                    int cnt = Integer.parseInt(mainItems.get((mainItems.size()-1)-position).like);
                    cnt -= 1;
                    strCnt = String.valueOf(cnt);
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mainItems.size();
    }


}