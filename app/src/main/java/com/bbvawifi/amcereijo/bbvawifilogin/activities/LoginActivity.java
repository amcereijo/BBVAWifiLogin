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

import java.util.HashMap;
import java.util.Map;

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
        final String user = userEditText.getText().toString();
        final String pass = passEditText.getText().toString();
        loginResult.setVisibility(View.GONE);

        Map parameters = createParameters(user, pass);
        bbvaWifiConnect.login(parameters, this, createAsyncHttpResponseHandler(user, pass));
    }

    private Map createParameters(String user, String pass) {
        Map parameters = new HashMap();
        parameters.put("user", user);
        parameters.put("passwd", pass);
        return parameters;
    }

    private AsyncHttpResponseHandler createAsyncHttpResponseHandler(final String user, final String pass) {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //check body
                String body = new String(responseBody);
                int pos = body.indexOf("Inicio - MediaNet Software");
                if (pos != -1) {
                    dataStore.saveAccess(user, pass);
                    showResultMessage(R.string.loginResultOK);
                } else {
                    showResultMessage(R.string.loginResultKO);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                showResultMessage(R.string.loginResultKO);
            }
        };
    }

    private void showResultMessage(int message) {
        loginResult.setText(message);
        loginResult.setVisibility(View.VISIBLE);
    }

}
