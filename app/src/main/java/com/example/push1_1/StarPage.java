package com.example.push1_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class StarPage extends Activity {
    RatingBar ratingBar;
    TextView score;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);*/
        setContentView(R.layout.ratingstar_page);

        score = findViewById(R.id.starCount);
        score.setText("0점");

        Button button = (Button) findViewById(R.id.submit_Button);

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                int rates = (int) rating;
                String message = null;
                score.setText(rating + "점");

                if(rates < 2) {
                    message = "좀 더 좋은 앱이 되도록 노력하겠습니다.";
                }else if(rates < 3) {
                    message = "냉정한 평가 감사합니다.";
                }else if(rates < 4) {
                    message = "좋은 평가 감사합니다.";
                }else if(rates < 5) {
                    message = "감사합니다. 앞으로도 자주 이용해 주세요!";
                }else {
                    message = "너무 좋은 평점 감사합니다 :):):)";
                }

                Toast.makeText(StarPage.this, message, Toast.LENGTH_SHORT).show();
            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
