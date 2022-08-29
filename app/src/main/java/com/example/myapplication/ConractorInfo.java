package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class ConractorInfo {


    private static final String PREF_NAMES = "userinfos";
    private static final String KEY_USERNAMES = "usernames";
    private static final String KEY_IDS = "ids";
    private static final String KEY_EMAILS = "emails";
    SharedPreferences prefsre;
    SharedPreferences.Editor editorse;
    String idd;
    Context context;

    public ConractorInfo(Context context){
        this.context = context;

        prefsre = context.getSharedPreferences(PREF_NAMES, context.MODE_PRIVATE);
        editorse = prefsre.edit();
    }

    public void setUsername(String username) {
        editorse.putString ( KEY_USERNAMES , username );
        editorse.apply ();
    }
    public void setId(String id) {


        idd=id;
        editorse.putString ( KEY_IDS, id );
        editorse.apply ();

    }

    public void setEmail(String email) {
        editorse.putString ( KEY_EMAILS , email );
        editorse.apply ();
    }

    public void clearUserInfo() {
        editorse.clear ();
        editorse.commit ();
    }

    public String getKeyUsername() {
        return prefsre.getString ( KEY_USERNAMES , "" );
    }

    public String getKeyEmail() {
        return prefsre.getString ( KEY_EMAILS , "" );
    }
    public String getKeyId() {
        return prefsre.getString ( KEY_IDS,idd);
    }
}
