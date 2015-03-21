package com.rsbauer.roundelremote.webservices.interfaces;

import android.os.Bundle;

import com.android.volley.VolleyError;

/**
 * Created by astro on 2/8/15.
 */
public interface IGsonResponse<T> {
    void onResponse(T responseObject);
    void onErrorResponse(VolleyError error);

}