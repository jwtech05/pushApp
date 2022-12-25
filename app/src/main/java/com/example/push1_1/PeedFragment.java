package com.example.push1_1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;

public class PeedFragment extends Fragment {
    ArrayList<PeedRegisterItems> peedRegisterItem;
    Handler handler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.peed_fragment_page, container, false);

        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        peedRegisterItem = PreferenceManager.getPeedItems(view.getContext(), "공용피드DB");
        RecyclerView 리싸이클러뷰 = (RecyclerView) view.findViewById(R.id.peedpageRecycler);

        NpaLinearLayoutManager 리니어매니저 = new NpaLinearLayoutManager(getActivity());
        리싸이클러뷰.setLayoutManager(리니어매니저);

        PeedAdapter 리싸이클러어댑터 = new PeedAdapter(peedRegisterItem);
        리싸이클러뷰.setAdapter(리싸이클러어댑터);

        return view;
    }

/*    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.recyclerview_mypage_item_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.fix:
                Log.d("수정", "수정기능시작합니다.");
            case R.id.delete:
                Log.d("삭제", "삭제기능시작합니다.");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/



    @Override
    public void onResume() {
        super.onResume();

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Toast.makeText(getActivity(), "장시간의 사용은 좋지 않습니다. 할꺼 해주세요.", Toast.LENGTH_SHORT).show();
            }
        }, 3000);

    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeMessages(0);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
