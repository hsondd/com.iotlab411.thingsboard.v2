package com.iotlab411.thingsboard.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharePreferenceUtils {

    private static SharedPreferences preferences;
    private SharedPreferences.Editor editor;


    public SharePreferenceUtils(Context context) {
        preferences = context.getSharedPreferences(Const.SHARE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public static void deleteSave(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }

    public static void deleteKeySave(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key).commit();
    }

    public static void insertStringData(Context context, String key, String value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static int getIntData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int r = preferences.getInt(key, 10);
        return r;
    }

    public static void insertIntData(Context context, String key, int value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

//    public static void insertLongData(Context context, String key, long value) {
//        preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putLong(key, value);
//        editor.commit();
//    }
//
//    public static long getLongData(Context context, String key) {
//        preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        long r = preferences.getLong(key, 0);
//        return r;
//    }

    public static void insertFloatData(Context context, String key, Float value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static Float getFloatData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Float r = preferences.getFloat(key, 0);
        return r;
    }

    public static boolean getBooleanData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean r = preferences.getBoolean(key, false);
        return r;
    }

    public static void insertBooleanData(Context context, String key, boolean value) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }




    public static String getStringData(Context context, String key) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String r = preferences.getString(key, "0");
        return r;
    }


}
