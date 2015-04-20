package com.bbvawifi.amcereijo.bbvawifilogin.activities;

import android.app.Activity;
import android.content.Intent;
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

    public static final int RESULT_CODE = 1024;

    @InjectView(R.id.username)
    private EditText userEditText;
    @InjectView(R.id.password)
    private EditText passEditText;

    @Inject
    private DataStore dataStore;

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

        dataStore.saveAccess(user, pass);

        setResult(RESULT_CODE);
        finish();
    }

}
