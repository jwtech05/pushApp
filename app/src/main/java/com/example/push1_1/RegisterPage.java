package com.example.push1_1;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterPage extends AppCompatActivity {
    Intent intent;

    String detail = "";
    EditText editTextToDo;
    TextView editTextDate;
    EditText editTextLocation;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String data = result.getData().getExtras().getString("data");
                        editTextLocation.setText(data);
                        new Thread(){
                            @Override
                            public void run() {
                                getNaverAddress(data);
                            }
                        }.start();
                    }
                }

            }
    );

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        //Log.d("regi","ONCREATE");


        intent = getIntent();

        editTextToDo = (EditText) findViewById(R.id.whatToDoEdit);
        editTextDate = (TextView) findViewById(R.id.dateEdit);
        editTextDate.setText(intent.getStringExtra("date"));
        editTextLocation = (EditText) findViewById(R.id.locationEdit);


        Button regiButton = (Button) findViewById(R.id.regibutton);
        regiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), ToDoPage.class);
                intent.putExtra("regi_todo", editTextToDo.getText().toString());
                intent.putExtra("regi_date", editTextDate.getText().toString());
                intent.putExtra("regi_location", editTextLocation.getText().toString());

                startActivity(intent);
            }
        });

        editTextLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("주소설정페이지", "주소입력창 클릭");
                int status = NetworkStatus.getConnectivityStatus(getApplicationContext());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {

                    Intent intent = new Intent(getApplicationContext(), AddressSearch.class);
                    overridePendingTransition(0, 0);
                    activityResultLauncher.launch(intent);

                }else {
                    Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    @Override
    protected void onRestart() {
        super.onRestart();

        editTextToDo.setText("");
        editTextDate.setText("");
        editTextLocation.setText("");
        //Log.d("regi", "ONRESTART");

    }

    protected void getNaverAddress(String add){
        String clientId = "mxb28e0kjp";
        String clientSecret = "pGLQxJzAJAP1r6f358N37DgmIJpfBIdEmAGOYr8N";

        try {
            Log.d("이거나오나요1","address : " );
            String addr = URLEncoder.encode(add, "UTF-8");

            // Geocoding 개요에 나와있는 API URL 입력.
            String apiURL = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + addr;	// JSON

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Geocoding 개요에 나와있는 요청 헤더 입력.
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            // 요청 결과 확인. 정상 호출인 경우 200
            int responseCode = con.getResponseCode();
            Log.d("이거나오나요3", String.valueOf(responseCode));
            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;

            StringBuffer response = new StringBuffer();

            while((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            br.close();

            JSONTokener tokener = new JSONTokener(response.toString());
            JSONObject object = new JSONObject(tokener);
            JSONArray arr = object.getJSONArray("addresses");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject temp = (JSONObject) arr.get(i);
                Log.d("이거나오나요3","address : " );
                Log.d("이거나오나요","address : " + temp.get("roadAddress"));
                Log.d("이거나오나요","jibunAddress : " + temp.get("jibunAddress"));
                Log.d("이거나오나요","위도 : " + temp.get("y"));
                Log.d("이거나오나요","경도 : " + temp.get("x"));
            }


        } catch (Exception  e) {
            Log.d("이거나오나요예외예외", String.valueOf(e));
        }
    }
}
