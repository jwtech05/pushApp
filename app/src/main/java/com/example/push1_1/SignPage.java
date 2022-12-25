package com.example.push1_1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;

public class SignPage extends AppCompatActivity {
    private Context mContext;
    EditText loginId;
    EditText username;
    EditText email;
    EditText password;
    public SignPageItems newMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_page);
        mContext = this;
        Intent intent = new Intent(this, LoginPage.class);
        loginId = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        Button signingButton = (Button) findViewById(R.id.signing);
        signingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = loginId.getText().toString();
                String userPw = password.getText().toString();
                String userName = username.getText().toString();
                String userEmail = email.getText().toString();
                if(userId.equals("")){
                    Toast.makeText(mContext, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    loginId.requestFocus();
                }else if(userName.equals("")){
                    Toast.makeText(mContext, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                }else if(userEmail.equals("")){
                    Toast.makeText(mContext, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                }else if(userPw.equals("")){
                    Toast.makeText(mContext, "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    password.requestFocus();
                }else if(PreferenceManager.duplicateCheck(mContext, userId, "회원목록")){
                    Toast.makeText(mContext, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                    loginId.requestFocus();
                }else{
                    PreferenceManager.setString(mContext,userId, userPw+","+userName+","+userEmail, "회원목록");
                    Toast.makeText(mContext, "환영합니다.회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                    startActivity(intent);
                }

                /*else{

                    Toast.makeText(mContext, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT);
                }*/

/*                intent.putExtra("username", username.getText().toString());
                intent.putExtra("email", email.getText().toString());
                DBPage.usingName = username.getText().toString();
                DBPage.loginId = loginId.getText().toString();
                DBPage.loginPassword=password.getText().toString();
                DBPage.email=email.getText().toString();
                newMember = new SignPageItems(username.getText().toString(),email.getText().toString(),loginId.getText().toString(),password.getText().toString());
                DBPage.sign.put(loginId.getText().toString(), newMember);*/
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onStop() {
        super.onStop();

        loginId.setText("");
        username.setText("");
        email.setText("");
        password.setText("");

        Toast.makeText(this, "회원가입작성 초기화", Toast.LENGTH_SHORT).show();
        Log.d("SIGN","ONSTOP");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("SIGN","ONSTOP");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //  Log.d("SIGN", "ONRESTART");

    }

}