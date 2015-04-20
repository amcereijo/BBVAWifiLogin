package com.bbvawifi.amcereijo.bbvawifilogin.net;

import android.app.Activity;
import android.app.ProgressDialog;

import com.bbvawifi.amcereijo.bbvawifilogin.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.Map;

import javax.inject.Singleton;

/**
 * Created by amcereijo on 19/04/15.
 */
@Singleton
public class BBVAWifiConnect {
    private static final String BASE_URL = "https://authwifi3.grupobbva.com:6082/php/uid.php?vsys=1&url=http://www.medianet.es";
    private static final int TIME_OUT = 5000;

    private static AsyncHttpClient client;


    public BBVAWifiConnect() {
        client = new AsyncHttpClient();
        client.setTimeout(TIME_OUT);
    }

    public void login(Map parameters, Activity parentActivity, final AsyncHttpResponseHandler responseHandler) {
        final RequestParams params = new RequestParams(parameters);
        params.add("ok", "login");
        client.post(BASE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                responseHandler.onSuccess(statusCode,headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                responseHandler.onFailure(statusCode, headers, responseBody, error);
            }
        });

    }

}
