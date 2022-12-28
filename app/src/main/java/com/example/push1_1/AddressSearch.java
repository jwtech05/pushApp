package com.example.push1_1;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.webkit.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
@SuppressLint("SetJavaScriptEnabled")
public class AddressSearch extends AppCompatActivity {

    private WebView aView;

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data){
            Log.d("processDATA", data);
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_search);

        aView = (WebView) findViewById(R.id.newAddress);
        aView.getSettings().setJavaScriptEnabled(true);
        aView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");
        aView.setWebViewClient(new WebViewClient(){

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // SSL 인증서 무시
            }

            @Override
            public void onPageFinished(WebView view, String url){
                aView.loadUrl("javascript:sample2_execDaumPostcode();");
            }

        });

        aView.loadUrl("https://jwtech05.github.io/search-address/");
    }
}