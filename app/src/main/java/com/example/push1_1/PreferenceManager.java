package com.example.push1_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.util.*;

public class PreferenceManager {

    public static String PREFERENCES_NAME = "";

    public static final String DEFAULT_VALUE_STRING = "";
    public static final boolean DEFAULT_VALUE_BOOLEAN = false;
    public static final int DEFAULT_VALUE_INT = -1;
    public static final long DEFAULT_VALUE_LONG = -1L;
    public static final float DEFAULT_VALUE_FLOAT= -1F;

    private static SharedPreferences getPreferences(Context context) {

        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

    }

    public static void setString(Context context, String key, String value, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();

    }

    public static void setBoolean(Context context, String key, Boolean value, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setInt(Context context, String key, int value, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setLong(Context context, String key, long value, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void setFloat(Context context, String key, float value, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        String value = prefs.getString(key, DEFAULT_VALUE_STRING);
        return value;

    }

    public static boolean getBoolean(Context context, String key, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        boolean value = prefs.getBoolean(key, DEFAULT_VALUE_BOOLEAN);
        return value;
    }

    public static long getLong(Context context, String key, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        long value = prefs.getLong(key, DEFAULT_VALUE_LONG);
        return value;
    }

    public static int getInt(Context context, String key, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        int value = prefs.getInt(key, DEFAULT_VALUE_INT);
        return value;
    }

    public static float getFloat(Context context, String key, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        float value = prefs.getFloat(key, DEFAULT_VALUE_FLOAT);
        return value;
    }

    public static void removeKey(Context context, String key, String 쉐어드분류) {

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.remove(key);
        edit.apply();
    }

    public static void clear(Context context, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.apply();

    }

    public static boolean duplicateCheck(Context context, String key, String 쉐어드분류){

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        if(prefs.contains(key)){
            Log.d("확인","true");
            return true;
        }
        Log.d("확인","false");
        return false;
    }

    public static boolean deleteSharedPreferences(Context context, String 쉐어드분류) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.deleteSharedPreferences(쉐어드분류);
        } else {
            PREFERENCES_NAME = 쉐어드분류;
            SharedPreferences prefs = getPreferences(context);
            prefs.edit().clear().apply();
            File dir = new File(context.getApplicationInfo().dataDir, "shared_prefs");
            return new File(dir, 쉐어드분류 + ".xml").delete();
        }
    }

    public static ArrayList<String> getStringArray(Context context, String 원하는값, String 쉐어드분류){

        ArrayList<String> info = new ArrayList<>();

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        Map<String, String> sharedArray = (Map<String, String>) prefs.getAll();
        for(String x: sharedArray.keySet()){
            if(x.contains(원하는값)){
                info.add(sharedArray.get(x));
            }
        }
        return info;
    }

    public static ArrayList<PeedRegisterItems> getPeedItems(Context context, String 쉐어드분류){

        ArrayList<PeedRegisterItems> info = new ArrayList<>();

        PREFERENCES_NAME = 쉐어드분류;
        SharedPreferences prefs = getPreferences(context);
        Map<String, String> sharedArray = (Map<String, String>) prefs.getAll();
        for(String key: sharedArray.keySet()){
            String x = sharedArray.get(key);
            int idx = x.indexOf("|");
            int secondIdx = x.indexOf("|", idx+1);
            int thirdIdx = x.indexOf("|",secondIdx+1);
            int forthIdx = x.indexOf("|",thirdIdx+1);
            int fifthIdx = x.indexOf("|", forthIdx+1);
            String likes = x.substring(0,idx);
            String nicks = x.substring(idx+1,secondIdx);
            String days = x.substring(secondIdx+1,thirdIdx);
            String comments= x.substring(thirdIdx+1,forthIdx);
            String tag = x.substring(forthIdx+2,fifthIdx-1);
            String picUri = x.substring(fifthIdx+2,x.length()-1);
            String[] splitTag = tag.split(", ");
            ArrayList<String> tags = new ArrayList<>();
            ArrayList<String> picUris = new ArrayList<>();
            for(int i=0; i<splitTag.length; i++){
                tags.add(splitTag[i]);
            }
            String[] splitPicUri = picUri.split(", ");
            for(int i=0; i<splitPicUri.length; i++){
                picUris.add(splitPicUri[i]);
            }
            ArrayList<String> arrayTags = tags; 
            info.add(new PeedRegisterItems(likes,nicks,days, comments,tags,picUris));
        }

        Collections.sort(info, new Comparator<PeedRegisterItems>() {
            @Override
            public int compare(PeedRegisterItems o1, PeedRegisterItems o2) {
                String valA = new String();
                String valB = new String();

                try{
                  valA = o1.getDays();
                  valB = o2.getDays();

                }catch (Exception e){
                  Log.d("아쉽지만", "비교가 안되네요");
                }

                return valA.compareTo(valB);
            }
        });

        //Log.d("프리퍼런스매니저", String.valueOf(info.size()));
        return info;
    }
}
