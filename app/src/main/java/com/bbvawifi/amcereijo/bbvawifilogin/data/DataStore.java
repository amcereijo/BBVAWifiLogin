package com.bbvawifi.amcereijo.bbvawifilogin.data;

import android.content.Context;
import android.content.SharedPreferences;


import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by amcereijo on 18/04/15.
 */
@Singleton
public class DataStore {

    private final static String FILE_DATA = "bbva_data";
    private final static String NAME = "bbva_user";
    private final static String PASS = "bbva_pass";

    private SharedPreferences sharedPref;
    private Context context;

    @Inject
    public DataStore(Context context) {
        this.context = context;
        sharedPref = this.context.getSharedPreferences(FILE_DATA, Context.MODE_PRIVATE);
    }

    public boolean hashLoginData() {
        return !("".equals(sharedPref.getString(DataStore.NAME, "")) ||
                "".equals(sharedPref.getString(DataStore.PASS, "")));
    }

    public String getUser() {
        return sharedPref.getString(NAME, "");
    }

    public String getPass() {
        return sharedPref.getString(NAME, "");
    }
    /**
     * Save user and password
     * @param user
     * @param pass
     */
    public void saveAccess(String user, String pass) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(NAME, user);
        editor.putString(PASS, pass);
        editor.commit();
    }

}
