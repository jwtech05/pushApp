package com.example.push1_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class TimerPage2 extends AppCompatActivity {

    ProgressBar progressBarCircle2;
    LinearLayout timeSetting2;
    TextView showTime2;
    NumberPicker timeHour2, timeMinute2, timeSecond2;
    Button startButton2, stopButton2, pauseButton2;
    int hours, minutes, seconds;
    static long millis;
    long maxMillis;
    public static boolean infi;
    int reset;
    Thread timer2 = null;
    Handler activityTimer;
    long sharedMillis;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page2);


        progressBarCircle2 = (ProgressBar) findViewById(R.id.progressBarCircle2);
        timeSetting2 = (LinearLayout) findViewById(R.id.timeSetting2);
        showTime2 = (TextView) findViewById(R.id.showTime2);
        timeHour2 = (NumberPicker) findViewById(R.id.numpicker_hours2);
        timeHour2.setMaxValue(23);

        timeMinute2 = (NumberPicker) findViewById(R.id.numpicker_minutes2);
        timeMinute2.setMaxValue(59);

        timeSecond2 = (NumberPicker) findViewById(R.id.numpicker_seconds2);
        timeSecond2.setMaxValue(59);

        startButton2 = (Button) findViewById(R.id.startButton2);
        stopButton2 = (Button) findViewById(R.id.stopButton2);


        //PreferenceManager.setString(getApplicationContext(),"할일중", "STOP", "현재로그인사용자");
        //PreferenceManager.setString(getApplicationContext(),"밀리타이머", "0", "현재로그인사용자");


        if(!PreferenceManager.getString(getApplicationContext(), "할일중", "현재로그인사용자").equals("작동중")){
            timeSetting2.setVisibility(View.VISIBLE);
            progressBarCircle2.setVisibility(View.GONE);
            showTime2.setVisibility(View.GONE);
            reset = 0;
        }else{
            reset = 1;
            maxMillis = Long.parseLong(PreferenceManager.getString(getApplicationContext(),"총시간", "현재로그인사용자"));
            timeSetting2.setVisibility(View.GONE);
            progressBarCircle2.setVisibility(View.VISIBLE);
            showTime2.setVisibility(View.VISIBLE);
            startButton2.setText("PAUSE");
            showTime2.setText(String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(TimerService.millis),
                    TimeUnit.MILLISECONDS.toMinutes(TimerService.millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(TimerService.millis)),
                    TimeUnit.MILLISECONDS.toSeconds(TimerService.millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(TimerService.millis))));
            progressBarCircle2.setMax((int) maxMillis / 1000);
            progressBarCircle2.setProgress((int) TimerService.millis / 1000);
            onStartButton();
        }


        startButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startButton2.getText().equals("START")) {
                    serviceIntent = new Intent(TimerPage2.this, TimerService.class);
                    if(reset == 0) {
                        hours = timeHour2.getValue();
                        minutes = timeMinute2.getValue();
                        seconds = timeSecond2.getValue();
                        millis = (hours * 3600000) + (minutes * 60000) + (seconds * 1000);
                        maxMillis = millis;
                        PreferenceManager.setString(getApplicationContext(), "할일중", "작동중", "현재로그인사용자");
                        PreferenceManager.setString(getApplicationContext(), "총시간", String.valueOf(maxMillis), "현재로그인사용자");
                        serviceIntent.putExtra("시간", millis);
                        reset = 1;
                    }else{
                        serviceIntent.putExtra("시간", Long.parseLong(PreferenceManager.getString(getApplicationContext(),"밀리타이머", "현재로그인사용자")));
                    }
                    PreferenceManager.setString(getApplicationContext(), "할일중", "작동중", "현재로그인사용자");
                    startService(serviceIntent);
                    onStartButton();
                    startButton2.setText("PAUSE");

                }else if(startButton2.getText().equals("PAUSE")){
                    serviceIntent = new Intent(TimerPage2.this, TimerService.class);
                    stopService(serviceIntent);
                    PreferenceManager.setString(getApplicationContext(), "할일중", "PAUSE", "현재로그인사용자");
                    timer2.interrupt();
                    startButton2.setText("START");
                }

            }
        });

        stopButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopButton();
            }
        });


        activityTimer = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {
                    case 0:
                        timeSetting2.setVisibility(View.GONE);
                        progressBarCircle2.setVisibility(View.VISIBLE);
                        showTime2.setVisibility(View.VISIBLE);
                        showTime2.setText(String.format("%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(TimerService.millis),
                                TimeUnit.MILLISECONDS.toMinutes(TimerService.millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(TimerService.millis)),
                                TimeUnit.MILLISECONDS.toSeconds(TimerService.millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(TimerService.millis))));
                        progressBarCircle2.setMax((int)maxMillis/ 1000);
                        progressBarCircle2.setProgress((int) TimerService.millis / 1000);
                        removeMessages(0);
                        break;
                }
            }
        };
    }

    public void onStartButton(){
            infi = true;
            timer2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (infi && millis > 0) {
                        Log.d("쓰레드","안");
                        activityTimer.sendEmptyMessage(0);
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            infi = false;
                            e.printStackTrace();
                            Log.d("인터럽트", "발생");
                        }
                    }
                }
            });
            timer2.start();
        /*else if(startButton2.getText().equals("PAUSE")){
            Intent intent = new Intent(TimerPage2.this, TimerService.class);
            intent.putExtra("millis", millis);
            stopService(intent);
        }*/
    }

    public void onStopButton(){
        timeSetting2.setVisibility(View.VISIBLE);
        progressBarCircle2.setVisibility(View.GONE);
        showTime2.setVisibility(View.GONE);
        startButton2.setText("START");
        timer2.interrupt();
        reset = 0;
        serviceIntent = new Intent(TimerPage2.this, TimerService.class);
        stopService(serviceIntent);
        PreferenceManager.setString(getApplicationContext(), "밀리타이머", "0", "현재로그인사용자");
        PreferenceManager.setString(getApplicationContext(), "할일중", "STOP", "현재로그인사용자");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer2.interrupt();
        /* Intent intent = new Intent(TimerPage2.this, TimerService.class);
        intent.putExtra("millis", millis);
        stopService(intent);
        Log.d("TimerPage2", "OnDestroy");*/
    }
}