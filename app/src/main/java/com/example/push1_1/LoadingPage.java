package com.example.push1_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingPage extends AppCompatActivity {
    Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_page);
        startLoading();

        TextView push = (TextView)findViewById(R.id.push);
        String content = push.getText().toString();
        SpannableString spannableString = new SpannableString(content);

        String word = "P";
        int start = content.indexOf(word);
        int end = start + word.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")),start,end,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        push.setText(spannableString);
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run(){
/*                String 상태 = PreferenceManager.getString(getApplicationContext(), "로그인상태", "자동로그인");
                if(상태.equals("0")) {
                    startActivity(new Intent(getApplicationContext(), LoginPage.class));
                }else{
                    startActivity(new Intent(getApplicationContext(), NavActivity.class));
                }*/
                startActivity(new Intent(getApplicationContext(), LoginPage.class));
                finish();
            }
        },2000);
    }
}
