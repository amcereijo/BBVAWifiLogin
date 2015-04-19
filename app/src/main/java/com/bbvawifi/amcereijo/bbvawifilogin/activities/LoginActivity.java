package com.bbvawifi.amcereijo.bbvawifilogin.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bbvawifi.amcereijo.bbvawifilogin.R;
import com.bbvawifi.amcereijo.bbvawifilogin.data.DataStore;
import com.bbvawifi.amcereijo.bbvawifilogin.net.BBVAWifiConnect;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import javax.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_login)
public class LoginActivity extends RoboActionBarActivity {

    @InjectView(R.id.username)
    private EditText userEditText;
    @InjectView(R.id.password)
    private EditText passEditText;
    @InjectView(R.id.loginResult)
    private TextView loginResult;

    @Inject
    private DataStore dataStore;
    @Inject
    private BBVAWifiConnect bbvaWifiConnect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    private void loadData() {
        userEditText.setText(dataStore.getUser());
    }

    public void login(View view) {
        //login service
        final String user = userEditText.getText().toString();
        final String pass = passEditText.getText().toString();
        loginResult.setVisibility(View.GONE);
        bbvaWifiConnect.login(user, pass, this, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //check body
                String body = new String(responseBody);
                int pos = body.indexOf("Inicio - MediaNet Software");
                if(pos != -1) {
                    //ok
                    dataStore.saveAccess(user, pass);
                    loginResult.setText(R.string.loginResultOK);
                    loginResult.setVisibility(View.VISIBLE);
                } else {
                    //error
                    loginResult.setText(R.string.loginResultKO);
                    loginResult.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //error
                loginResult.setText(R.string.loginResultKO);
                loginResult.setVisibility(View.VISIBLE);
            }
        });
    }

}
