package com.bbvawifi.amcereijo.bbvawifilogin.net;

import android.app.Activity;
import android.app.ProgressDialog;

import com.bbvawifi.amcereijo.bbvawifilogin.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import javax.inject.Singleton;

/**
 * Created by amcereijo on 19/04/15.
 */
@Singleton
public class BBVAWifiConnect {
    private static final String BASE_URL = "https://authwifi3.grupobbva.com:6082/php/uid.php?vsys=1&url=http://www.medianet.es";

    private static AsyncHttpClient client;
    private RequestParams params;

    public BBVAWifiConnect() {
        client = new AsyncHttpClient();
        params = new RequestParams();
        client.setTimeout(5000);
    }

    public void login(String user, String pass, Activity parentActivity, final AsyncHttpResponseHandler responseHandler) {
        params.add("user", user);
        params.add("passwd", pass);
        params.add("ok", "login");

        final ProgressDialog progress = ProgressDialog.show(parentActivity,
                parentActivity.getText(R.string.loginLoadingTitle),
                parentActivity.getText(R.string.loginLoadingText), true);

        client.post(BASE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progress.dismiss();
                responseHandler.onSuccess(statusCode,headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progress.dismiss();
                responseHandler.onFailure(statusCode, headers, responseBody, error);
            }
        });

    }
}
