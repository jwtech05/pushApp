package com.example.push1_1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.TimeUnit;


public class TimerService extends Service {
    String times;
    public static long millis;
    Thread a;
    boolean infi = true;

    public TimerService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Handler timer = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch(msg.what){
                case 0:
                    if(millis>= 1000) {
                        millis-= 1000;
                        Log.d("handler",String.valueOf(millis));
                        removeMessages(0);
                        break;
                    }
                case 1:
                    break;
            }
        }
    };
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent != null) {
            long serviceMillis = intent.getLongExtra("시간", 0);
            millis = serviceMillis;
        }
        infi = true;
        a = new Thread(new Runnable() {
            @Override
            public void run() {
                while (infi) {
                    try {
                        timer.sendEmptyMessage(0);
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("TIMERSERIVCE", "INTERRUPT");
                    }
                }
            }
        });
        a.start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        PreferenceManager.setString(getApplicationContext(), "밀리타이머", String.valueOf(millis), "현재로그인사용자");
        infi = false;
        a.interrupt();
    }


}