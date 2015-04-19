package com.bbvawifi.amcereijo.bbvawifilogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bbvawifi.amcereijo.bbvawifilogin.activities.LoginActivity;
import com.bbvawifi.amcereijo.bbvawifilogin.data.DataStore;
import com.bbvawifi.amcereijo.bbvawifilogin.net.BBVAWifiConnect;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import javax.inject.Inject;

import roboguice.activity.RoboActionBarActivity;


public class MainActivity extends RoboActionBarActivity {

    @Inject
    private DataStore dataStore;
    @Inject
    private BBVAWifiConnect bbvaWifiConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!dataStore.hashLoginData()) {
            //acrivity to save user/pass
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            final Toast resultToast = Toast.makeText(this, R.string.mainLoginOK, Toast.LENGTH_LONG);
            final Activity activity = this;

            //execute login service
            bbvaWifiConnect.login(dataStore.getUser(), dataStore.getPass(), this, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    //show OK
                    resultToast.show();
                    //close app
                    android.os.Process.killProcess(android.os.Process.myPid());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    //Show error
                    resultToast.setText(R.string.mainLoginKO);
                    resultToast.show();
                    //redirect login
                    final Intent intent = new Intent(activity, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }



}
