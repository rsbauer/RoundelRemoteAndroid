package com.rsbauer.roundelremote;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;

import roboguice.RoboGuice;

/**
 * Created by astro on 2/8/15.
 */
public class RoundelApplication extends android.app.Application {
    static {
        RoboGuice.setUseAnnotationDatabases(false);
    }

    public static final String TAG = RoundelApplication.class.getSimpleName();
    private RequestQueue requestQueue;
    private static RoundelApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized RoundelApplication getInstance() {
        return instance;
    }

    public Context getContext() {
        return null;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        addToRequestQueue(req, TAG);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    /*
    @Override
    public String getRSBServiceURL() {

        return Constants.RSB_SERVER_URL;
    }
    */
}