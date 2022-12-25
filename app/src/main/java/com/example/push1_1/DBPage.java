package com.example.push1_1;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

public class DBPage {
    //회원가입용
    public static String usingName;
    public static String email;
    public static String loginId;
    public static String loginPassword;
    public static HashMap<String, SignPageItems> sign = new HashMap<>();
    //피드&마이페이지 사진
/*  public static ArrayList<ArrayList<String>> picture = new ArrayList<>();
    public static ArrayList<String> comment = new ArrayList<>();
    public static String day;
    public static HashMap<String, PeedRegisterItems> peedInfo = new HashMap<String, PeedRegisterItems>();
    public static ArrayList<String> imageList;*/
    //
    public static ArrayList<PeedRegisterItems> peedUpload = new ArrayList<>();
    //PeedRegister 사진
    public static Bitmap imageBitmap;
    //Calendar 정보
    public static ArrayList<String> 전체정보 = new ArrayList<>();
    //
    public static ArrayList<String> 날짜별정보;
    //
    public static String changeDate;


}
