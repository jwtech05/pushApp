package com.example.push1_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ToDoPage extends AppCompatActivity {

    Context mContext;
    RatingBar ratingBar;
    TextView score;
    String todoLocation;
    Intent intent;
    TextView textViewToDo;
    TextView textViewDate;
    TextView textViewLocation;
    String toDo;
    String date;
    String location;
    HashMap<String, Integer> detail;
    public static ArrayList<String> info = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_page);
        Log.d("ToDo","ONCREATE");
        intent = getIntent();
        mContext=this;
        textViewToDo = (TextView) findViewById(R.id.textToDo);
        toDo = intent.getStringExtra("regi_todo");
        textViewToDo.setText(toDo);

        textViewDate = (TextView) findViewById(R.id.textDate);
        date = intent.getStringExtra("regi_date");
        textViewDate.setText(date);

        textViewLocation = (TextView) findViewById(R.id.textLocation);
        location = intent.getStringExtra("regi_location");
        textViewLocation.setText(location);

        Button okButton = (Button) findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                String loginUser = PreferenceManager.getString(mContext, "현재로그인", "현재로그인사용자");
                String user = loginUser.substring(0,loginUser.indexOf(","));
                if(PreferenceManager.duplicateCheck(mContext, date+","+toDo, user+"일정DB")) {
                    Toast.makeText(mContext, "이미 있는 항목입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    PreferenceManager.setString(mContext, date + "," + toDo, toDo+","+location+","+new HashMap<String,Integer>(), user + "일정DB");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ToDo","ONSTART");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ToDo","ONRESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
/*      intent = new Intent(this, StarPage.class);
        startActivity(intent);*/
        Log.d("ToDo","ONPAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ToDo","ONSTOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ToDo","ONDESTROY");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ToDO", "ONRESTART");

    }


}