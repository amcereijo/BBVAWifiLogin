package com.bbvawifi.amcereijo.bbvawifilogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bbvawifi.amcereijo.bbvawifilogin.activities.LoginActivity;
import com.bbvawifi.amcereijo.bbvawifilogin.data.DataStore;
import com.bbvawifi.amcereijo.bbvawifilogin.net.BBVAWifiConnect;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;

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
            startLoginActivity();
        } else {
            Map parameters = createParameters();
            bbvaWifiConnect.login(parameters, this, createAsyncHttpResponseHandler());
        }
    }

    private Map createParameters() {
        Map parameters = new HashMap();
        parameters.put("user", dataStore.getUser());
        parameters.put("passwd", dataStore.getPass());
        return parameters;
    }


    private AsyncHttpResponseHandler createAsyncHttpResponseHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                showResultMessage(R.string.mainLoginOK);
                //close app
                android.os.Process.killProcess(android.os.Process.myPid());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showResultMessage(R.string.mainLoginKO);
                startLoginActivity();
            }
        };
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void showResultMessage(int message) {
        final Toast resultToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

}
