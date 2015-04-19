package com.bbvawifi.amcereijo.bbvawifilogin.application;

import android.app.Application;

import roboguice.RoboGuice;

/**
 * Created by amcereijo on 18/04/15.
 */
public class BBVAWifiLoginApplication extends Application {
    // to solve a bug with RoboGuice
    static {
        RoboGuice.setUseAnnotationDatabases(false);
    }
}
