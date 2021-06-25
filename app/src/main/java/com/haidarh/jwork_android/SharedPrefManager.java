package com.haidarh.jwork_android;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences("jwork", Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    public int getSPid(){
        return sp.getInt("sp_id", 1);
    }

    public String getSPEmail(){
        return sp.getString("sp_email", "");
    }

    public Boolean getSPLoginStatus(){
        return sp.getBoolean("sp_login_status", false);
    }

}
