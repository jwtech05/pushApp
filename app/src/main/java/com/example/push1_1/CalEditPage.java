package com.example.push1_1;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.*;

public class CalEditPage extends AppCompatActivity {

    Context mContext;
    TextView textToDo, textLocation, textDate;
    String todo;
    String location;
    String date;
    Button detailButton, okButton, calDeleteButton;
    LinearLayout detailLayout;
    EditText detailText;
    int detailCnt = 0;
    int tagCnt = 0;
    CalPageItems calPageItems;
    ArrayList<CalPageItems> calPageItem;
    String detail = "";
    LinkedHashMap<String, Integer> detailInfo;
    ArrayList<Integer> checked = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_edit_page);

        detailInfo = new LinkedHashMap<>();
        mContext =this;
        Intent intent = getIntent();
        calDeleteButton = (Button) findViewById(R.id.calDeleteButton);
        okButton = (Button) findViewById(R.id.okButton);
        textToDo = (TextView) findViewById(R.id.textToDo);
        textLocation = (TextView) findViewById(R.id.textLocation);
        textDate = (TextView) findViewById(R.id.textDate);

        detailButton = findViewById(R.id.detailButton);
        detailLayout = findViewById(R.id.detailLayout);
        detailText = (EditText) findViewById(R.id.detailItem);

        todo = intent.getStringExtra("todo");
        location = intent.getStringExtra("location");
        date = intent.getStringExtra("date");

        textToDo.setText(todo);
        textLocation.setText(location);
        textDate.setText(date);

        String info = PreferenceManager.getString(mContext, date+","+todo, LoginPage.loginer+"일정DB");
        int idx = info.indexOf("{");
        int idx2 = info.indexOf("}");
        String getDetail = info.substring(idx+1,idx2);
        if(!getDetail.equals("")) {
            String[] items = getDetail.split(", ");
            for (String x : items) {
                int idxSub = x.indexOf("=");
                String key = x.substring(0, idxSub);
                int value = Integer.parseInt(x.substring(idxSub + 1));
                detailInfo.put(key, value);
            }
            for (Map.Entry<String, Integer> x : detailInfo.entrySet()) {
                addView(x.getKey());
            }
        }

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detail = detailText.getText().toString();
                if(detailCnt > 4){
                    Toast.makeText(mContext, "세부사항은 5개 이상 만들 수 없습니다.", Toast.LENGTH_SHORT).show();
                    detailText.setText("");
                }else {
                    if (detail.equals("")) {
                        Toast.makeText(mContext, "세부사항을 적어주세요.", Toast.LENGTH_SHORT).show();
                    } else {
                        addView();
                    }
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NavActivity.class);
                calPageItems = new CalPageItems(todo, location, date, detailInfo);
                PreferenceManager.setString(mContext, date+","+todo, calPageItems.todo +","+ calPageItems.location + ","+ calPageItems.details
                        ,LoginPage.loginer+"일정DB");
                v.getContext().startActivity(intent);
            }
        });

        calDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NavActivity.class);
                PreferenceManager.removeKey(mContext, date+","+todo,LoginPage.loginer+"일정DB");
                PreferenceManager.removeKey(mContext, date+","+todo,LoginPage.loginer+"태그가능목록");
                v.getContext().startActivity(intent);
            }
        });

    }
    //새로운 뷰 데이터 추가
    private void addView() {

        final View detailView = getLayoutInflater().inflate(R.layout.register_page_detail,null,false);

        CheckBox detailBox = (CheckBox) detailView.findViewById(R.id.detailBox);
        ImageView imageClose = (ImageView)detailView.findViewById(R.id.closeBox);

        detailBox.setText(detail);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                removeView(detailView);
                detailInfo.remove(detailBox.getText().toString());
            }
        });

        detailLayout.addView(detailView);
        detailInfo.put(detailBox.getText().toString(),0);
        if(detailInfo.get(detail) == 1){
            detailBox.setChecked(true);
        }else{
            detailBox.setChecked(false);
        }

        detailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(detailBox.isChecked()){
                    detailInfo.replace(detail, 1);
                    tagCnt += 1;
                }else{
                    detailInfo.replace(detail, 0);
                    tagCnt -= 1;
                }
            }
        });
        detailCnt++;
        detailText.setText("");
    }
    //기존에 있던 데이터 뷰
    private void addView(String detail){

        final View detailView = getLayoutInflater().inflate(R.layout.register_page_detail,null,false);

        CheckBox detailBox = (CheckBox) detailView.findViewById(R.id.detailBox);
        ImageView imageClose = (ImageView)detailView.findViewById(R.id.closeBox);
        detailBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(detailBox.isChecked()){
                    detailInfo.replace(detail, 1);
                    tagCnt += 1;
                }else{
                    detailInfo.replace(detail, 0);
                    tagCnt -= 1;
                }
                Log.d("detailBox2", String.valueOf(tagCnt));
            }
        });

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(detailBox.isChecked()){
                    tagCnt -=1;
                }
                removeView(detailView);
                detailInfo.remove(detailBox.getText().toString());
                Log.d("removeView",detailBox.getText().toString());
            }
        });

        detailBox.setText(detail);
        if(detailInfo.get(detail) == 1){
            detailBox.setChecked(true);
        }else{
            detailBox.setChecked(false);
        }
        detailLayout.addView(detailView);
        detailCnt++;
    }

    private void removeView(View view){

        detailLayout.removeView(view);
        detailCnt--;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("calEditPage", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        int a = 0;
        for(String x : detailInfo.keySet()){
            a += detailInfo.get(x);
        }
        if(a == detailInfo.size()){
            PreferenceManager.setString(mContext, date+","+todo, todo, LoginPage.loginer+"태그가능목록");
        }else{
            PreferenceManager.removeKey(mContext, date+","+todo,LoginPage.loginer+"태그가능목록");
        }
    }
}