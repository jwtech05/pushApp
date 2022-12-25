package com.example.push1_1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;


public class LoginPage extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1000;
    static String TAG = "TAG";
    Intent intent;
    public static String loginer;
    public static String loginerNick;
    Context mContext;
    String sharedPw;
    String sharedEmail;
    String sharedName;
    String userId;
    String userPw;
    public static GoogleSignInClient mGoogleSignInClient;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {


        mContext = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        EditText id = (EditText) findViewById(R.id.id);
        EditText pass = (EditText) findViewById(R.id.password);
        Button login_btn = (Button) findViewById(R.id.login);
        CheckBox 자동로그인 = (CheckBox) findViewById(R.id.checkBox2);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        자동로그인.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(자동로그인.isChecked()){
                    PreferenceManager.setString(mContext, "로그인상태","1","자동로그인");
                }else{
                    PreferenceManager.setString(mContext, "로그인상태","0","자동로그인");
                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userId = id.getText().toString();
                userPw = pass.getText().toString();

                if(!PreferenceManager.duplicateCheck(mContext, userId, "회원목록")){
                    Toast.makeText(mContext, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT).show();
                }else{
                    String sharedInfo = PreferenceManager.getString(mContext, userId, "회원목록");
                    int idx = sharedInfo.indexOf(",");
                    int lastIdx = sharedInfo.lastIndexOf(",");
                    sharedPw = sharedInfo.substring(0, idx);
                    sharedName = sharedInfo.substring(idx+1,lastIdx);
                    sharedEmail = sharedInfo.substring(lastIdx+1);
                }
                if(!userPw.equals(sharedPw)){
                    Toast.makeText(mContext, "아이디와 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }else{

                    Intent intent = new Intent(getApplicationContext(), NavActivity.class);
                    PreferenceManager.setString(mContext, "현재로그인", userId+","+sharedName+","+sharedEmail, "현재로그인사용자");
                    PreferenceManager.setString(mContext, "프로필사진", "비어있음", "현재로그인사용자");
                    loginer = userId;
                    loginerNick = sharedName;
                    intent.putExtra("userName", sharedName);
                    intent.putExtra("userEmail", sharedEmail);
                    startActivity(intent);
                    Toast.makeText(LoginPage.this, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button sign_btn = (Button) findViewById(R.id.sign);
        sign_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignPage.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount signedIn) {
        if(signedIn == null){

        }else{
            Intent intent = new Intent(getApplicationContext(), NavActivity.class);
            PreferenceManager.setString(mContext, "현재로그인", userId+","+sharedName+","+sharedEmail, "현재로그인사용자");
            PreferenceManager.setString(mContext, "프로필사진", "비어있음", "현재로그인사용자");
            loginer = userId;
            loginerNick = sharedName;
            intent.putExtra("userName", sharedName);
            intent.putExtra("userEmail", sharedEmail);
            startActivity(intent);
            Toast.makeText(LoginPage.this, "로그인에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        String personName = null;
        String personEmail = null;
        String personId = null;
        Uri personPhoto = null;
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
            if(account != null){
                personName = account.getDisplayName();
                personEmail= account.getEmail();
                personId = account.getId();
                personPhoto = account.getPhotoUrl();
            }

            sharedName = personName;
            sharedEmail = personEmail;
            userId = personName;

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}
