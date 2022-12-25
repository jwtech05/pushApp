package com.example.push1_1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.push1_1.CalAdapter.*;

public class CalFragment extends Fragment {

    public static int finishWork = 0;
    int yearCheck;
    int monthCheck;
    int dayCheck;
    String date;
    String days = null;
    FileInputStream fis = null;
    CheckBox checkBox;
    CalendarView calenderView;
    SimpleDateFormat mFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    RecyclerView 리싸이클러뷰;
    CalPageItems calPageItem;
    ArrayList<CalPageItems> calPageItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.cal_fragment_page, container, false);
        Toolbar toolbar = rootView.findViewById(R.id.calToolbar);
        setHasOptionsMenu(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        checkBox = rootView.findViewById(R.id.todoCheck);
        calenderView = rootView.findViewById(R.id.calendarView);
        리싸이클러뷰 = (RecyclerView) rootView.findViewById(R.id.calRecyclerview);

        calenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                GregorianCalendar cal = new GregorianCalendar(year, month, dayOfMonth);

                LinearLayoutManager 리니어매니저 = new LinearLayoutManager(getContext());
                yearCheck = year;
                monthCheck = month+1;
                dayCheck = dayOfMonth;
                date = getTime(year, month, dayOfMonth);
                checkDay(year, month, dayOfMonth);
                리싸이클러뷰.setLayoutManager(리니어매니저);
                CalAdapter 리싸이클러어댑터 = new CalAdapter(calPageItems);
                리싸이클러뷰.setAdapter(리싸이클러어댑터);
                리싸이클러뷰.setVisibility(View.VISIBLE);
            }
        });


        return rootView;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.register_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.register_todo:
                Intent intent = new Intent(getActivity(), RegisterPage.class);
                intent.putExtra("date", date);
                startActivity(intent);
                return true;
            case R.id.timer:
                intent = new Intent(getActivity(), TimerPage.class);
                startActivity(intent);
                return true;
            case R.id.longTimer:
                //TimerPage2.infi = false;
                intent = new Intent(getActivity(), TimerPage2.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkDay(int cYear, int cMonth, int cDay){

        ArrayList<String> info = PreferenceManager.getStringArray(getActivity(), date+",", LoginPage.loginer+"일정DB");

        calPageItems = new ArrayList<>();

        for(String x: info){
            int idx = x.indexOf(",");
            int lastIdx = x.indexOf(",", idx+1);
            String todo = x.substring(0,idx);
            String loca = x.substring(idx+1, lastIdx);
            calPageItems.add(new CalPageItems(todo, loca, date, new LinkedHashMap<String, Integer>()));
        }
    }

    public String getTime(int year, int month, int dayOfMonth){
        GregorianCalendar cal = new GregorianCalendar(year, month, dayOfMonth);
        long millis = cal.getTimeInMillis();
        Date mDate = new Date(millis);
        return mFormat2.format(mDate);
    }
}
