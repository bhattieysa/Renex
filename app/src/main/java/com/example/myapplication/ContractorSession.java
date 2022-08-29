package com.example.myapplication;


import android.content.Context;
import android.content.SharedPreferences;

public class ContractorSession {
    private static final String TAG = ContractorSession.class.getSimpleName();
    private static final String PREF_NAMES = "logout";
    private static final String KEY_IS_LOGED_IN = "isloggedout";
    SharedPreferences prefsr;
    SharedPreferences.Editor editors;
    Context Ctx;


    public ContractorSession(Context context){
        this.Ctx = context;

        prefsr = Ctx.getSharedPreferences(PREF_NAMES, Context.MODE_PRIVATE);
        editors = prefsr.edit();
    }
    public void setLoggedin(boolean isLoggedin){
        editors.putBoolean(KEY_IS_LOGED_IN, isLoggedin);
        editors.apply();
    }

    public  boolean isUserLoggedin(){return prefsr.getBoolean(KEY_IS_LOGED_IN, false);}
}
