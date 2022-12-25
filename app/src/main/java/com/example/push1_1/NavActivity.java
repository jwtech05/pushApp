package com.example.push1_1;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import org.jetbrains.annotations.NotNull;

public class NavActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    MypageFragment mypageFragment = new MypageFragment();
    CalFragment calFragment = new CalFragment();

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {

                    }

                }

            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_page);

        Intent intent = getIntent();

        String name = intent.getStringExtra("userName");
        String email = intent.getStringExtra("userEmail");

        bottomNavigationView = findViewById(R.id.navigationView);


        Bundle bundle = new Bundle();
        bundle.putString("userName", name);
        bundle.putString("userEmail", email);
        mypageFragment.setArguments(bundle);

        //Log.d("regi", bundle.toString());


        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, new PeedFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NotNull MenuItem item) {


                switch(item.getItemId()){
                    case R.id.peed:
                        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, new PeedFragment()).commit();
                        break;
                    case R.id.canlander:
                        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, calFragment).commit();
                        break;
                    case R.id.mypage:
                        getSupportFragmentManager().beginTransaction().replace(R.id.menu_frame_layout, mypageFragment).commit();
                        break;
                }
                return true;
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Nav","ONSTART");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Nav","ONRESUME");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Nav","ONPAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Nav","ONSTOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Nav","ONDESTROY");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Nav", "ONRESTART");

    }

}
