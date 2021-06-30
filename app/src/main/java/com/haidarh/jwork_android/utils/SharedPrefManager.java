package com.haidarh.jwork_android.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Shared pref manager. menyimpan sesi login, id, dan email user.
 *
 * @author Haidar Hanif
 */
public class SharedPrefManager {
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    /**
     * Instantiates a new Shared pref manager (jwork).
     *
     * @param context the Context
     */
    public SharedPrefManager(Context context){
        sp = context.getSharedPreferences("jwork", Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    /**
     * Save string ke shared pref
     *
     * @param keySP the key sp
     * @param value the value
     */
    public void saveSPString(String keySP, String value){
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    /**
     * Save int ke shared pref
     *
     * @param keySP the key sp
     * @param value the value
     */
    public void saveSPInt(String keySP, int value){
        spEditor.putInt(keySP, value);
        spEditor.commit();
    }

    /**
     * Save boolean ke shared pref
     *
     * @param keySP the key sp
     * @param value the value
     */
    public void saveSPBoolean(String keySP, boolean value){
        spEditor.putBoolean(keySP, value);
        spEditor.commit();
    }

    /**
     * mengambil id dari shared pref
     *
     * @return the id (int)
     */
    public int getSPid(){
        return sp.getInt("sp_id", 1);
    }

    /**
     * mengambil email dari shared pref
     *
     * @return the email (string)
     */
    public String getSPEmail(){
        return sp.getString("sp_email", "");
    }

    /**
     * mengambil login_status dari shared pref
     *
     * @return the login status (boolean)
     */
    public Boolean getSPLoginStatus(){
        return sp.getBoolean("sp_login_status", false);
    }

}
