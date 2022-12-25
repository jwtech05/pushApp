package com.example.push1_1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class TimerPage extends AppCompatActivity {
    static ProgressBar progressBarCircle;
    static TextView showTime, motiveText;
    static LinearLayout timeSetting;
    static long millis2;
    static long millis;
    Thread motiveThread;
    String[] motive = {"중요한 것은 꺽이지 않는 마음 \n -호날두-", "탁월한 능력은 새로운 과제를 만날 때마다 스스로 발전하고 드러낸다 \n -발타사르-", "노력을 이기는 재능은 없다, 즉 노력이 최고의 재능이다 \n -배고파-", "기회는 일어나는 것이 아니라 만들어내는 것이다 \n -짜장면-", "움직이면 1이라도 있지만 안하면 0이다. \n -햄버거-"};
    int hours, minutes, seconds , bannerNum;
    static NumberPicker timeHour, timeMinute, timeSecond;
    static Button startButton, stopButton;
    static Handler timer, banner;
    boolean flag = true;
    static int status = 0;
    static SimpleDateFormat kFormat = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
    private static int timerTime = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page);

        progressBarCircle = (ProgressBar)findViewById(R.id.progressBarCircle);
        timeSetting = (LinearLayout) findViewById(R.id.timeSetting);
        showTime = (TextView) findViewById(R.id.showTime);
        timeHour = (NumberPicker) findViewById(R.id.numpicker_hours);
        timeHour.setMaxValue(23);

        timeMinute = (NumberPicker) findViewById(R.id.numpicker_minutes);
        timeMinute.setMaxValue(59);

        timeSecond = (NumberPicker) findViewById(R.id.numpicker_seconds);
        timeSecond.setMaxValue(59);

        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        motiveText = (TextView) findViewById(R.id.motiveText);



        timer = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch(msg.what){
                    case 0:
                        if(millis != 1000) {
                            showTime.setText(String.format("%02d:%02d:%02d",
                                    TimeUnit.MILLISECONDS.toHours(millis),
                                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
                            removeMessages(0);
                            millis -= 1000;
                            PreferenceManager.setString(getApplicationContext(), "할일중", String.format("%02d:%02d:%02d",
                                    TimeUnit.MILLISECONDS.toHours(millis),
                                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))), "현재로그인사용자");
                            sendEmptyMessageDelayed(0, 1000);
                            progressBarCircle.setMax((int) millis2 / 1000);
                            progressBarCircle.setProgress((int) millis / 1000);
                            break;
                        }else{
                            status = 0;
                            timeSetting.setVisibility(View.VISIBLE);
                            progressBarCircle.setVisibility(View.GONE);
                            showTime.setVisibility(View.GONE);
                            startButton.setText("START");
                            break;
                        }
                    case 1:
                        removeMessages(0);
                        showTime.setText( String.format("%02d:%02d:%02d",
                                TimeUnit.MILLISECONDS.toHours(millis),
                                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
                        break;
                    case 2:
                        removeMessages(0);
                        timeSetting.setVisibility(View.VISIBLE);
                        progressBarCircle.setVisibility(View.GONE);
                        showTime.setVisibility(View.GONE);
                        break;
                }

            }
        };

        banner = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                switch(msg.what) {
                    case 0:
                        motiveText.setText(motive[bannerNum]);
                        bannerNum++;
                        break;
                }

            }

        };


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartPauseButton();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStopButton();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        motiveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        if(bannerNum == motive.length){
                            bannerNum = 0;
                        }
                        banner.sendEmptyMessage(0);
                        Thread.sleep(4000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        motiveThread.start();
    }

    public void onStartPauseButton(){

        if(status == 0)
        {
            status = 1;
            timeSetting.setVisibility(View.GONE);
            progressBarCircle.setVisibility(View.VISIBLE);
            showTime.setVisibility(View.VISIBLE);
            hours = timeHour.getValue();
            minutes = timeMinute.getValue();
            seconds = timeSecond.getValue();
            millis = (hours * 3600000) + (minutes * 60000) + (seconds * 1000);
            millis2 = millis;
            timer.sendEmptyMessage(0);
            startButton.setText("PAUSE");
        }else if(status == 1){
            status = 2;
            timer.sendEmptyMessage(1);
            startButton.setText("START");
        }else{
            status = 1;
            timer.sendEmptyMessage(0);
            startButton.setText("PAUSE");
        }

    }

    protected void onStopButton() {
        status = 0;
        timer.sendEmptyMessage(2);
        startButton.setText("START");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        motiveThread.interrupt();
        if(timer != null)
            timer.removeMessages(0);
        PreferenceManager.setString(getApplicationContext(), "할일중", "0", "현재로그인사용자");
    }
}