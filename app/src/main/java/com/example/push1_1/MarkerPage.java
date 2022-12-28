package com.example.push1_1;

import android.graphics.Color;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MarkerPage extends AppCompatActivity implements OnMapReadyCallback {

    Marker marker = new Marker();
    ArrayList<String> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker_page);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if(mapFragment == null){
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull @NotNull NaverMap naverMap) {

        Map<String, Integer> markerCounts = new HashMap<>();
        float intLat = 0;
        float intLng = 0;

        location =  PreferenceManager.getStringAll(getApplicationContext(), LoginPage.loginer+"일정DB");
        for(String x: location) {
            try {
                JSONObject jsonObj = new JSONObject(x);
                String Lat = jsonObj.get("위도").toString();
                String Lng = jsonObj.get("경도").toString();
                intLat = Float.parseFloat(Lat);
                intLng = Float.parseFloat(Lng);

                String markerKey = Lat + "," + Lng;

/*                if (markerCounts.containsKey(markerKey)) {
                    int count = markerCounts.get(markerKey);
                    markerCounts.put(markerKey, count + 1);
                } else {
                    markerCounts.put(markerKey, 1);
                }*/

                int count = markerCounts.getOrDefault(markerKey, 0);
                markerCounts.put(markerKey, count + 1);
            }catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        for(Map.Entry<String, Integer> entry : markerCounts.entrySet()){

            String key = entry.getKey();
            String[] parts = key.split(",");
            float Lat = Float.parseFloat(parts[0]);
            float Lng = Float.parseFloat(parts[1]);
            Marker marker = new Marker();
            if(entry.getValue() > 3){
                marker.setIconTintColor(Color.RED);
            }
            marker.setCaptionText(Integer.toString(markerCounts.get(key)) + "회");
            marker.setPosition(new LatLng(Lat, Lng));
            marker.setMap(naverMap);

        }

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(intLat, intLng));
        naverMap.moveCamera(cameraUpdate);
    }
}