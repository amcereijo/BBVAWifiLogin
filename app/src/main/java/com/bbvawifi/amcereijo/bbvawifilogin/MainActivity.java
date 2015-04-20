package com.bbvawifi.amcereijo.bbvawifilogin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
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
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActionBarActivity {

    @Inject
    private DataStore dataStore;
    @Inject
    private BBVAWifiConnect bbvaWifiConnect;
    @InjectView(R.id.status_message)
    private TextView statusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doAccess();
    }

    private void doAccess() {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        statusMessage.setText(R.string.loginLoadingText);
        doAccess();
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
        startActivityForResult(intent, LoginActivity.RESULT_CODE);
    }

    private void showResultMessage(int message) {
        final Toast resultToast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        resultToast.show();
    }

}
