package com.rsbauer.roundelremote.webservices;

/**
 * Created by astro on 2/8/15.
 */

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.rsbauer.roundelremote.webservices.interfaces.IGsonResponse;
import com.rsbauer.roundelremote.webservices.interfaces.IRSBWebService;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by astro on 1/3/15.
 */
public class RSBWebService<T> implements Response.Listener<T>, Response.ErrorListener, IRSBWebService<T> {

    private IGsonResponse<T> response;
    private final Gson gson;
    private String serviceUrl;

    @Inject
    RSBWebService(Gson gson, String serviceUrl) {
        this.response = null;
        this.gson = gson;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public GsonRequest<T> request(Type classType, int method, String serviceName, Map<String, String> headers, Map<String, String> parameters, JsonObject payload, IGsonResponse<T> response) {
        this.response = response;

        if(headers == null) {
            headers = new HashMap<String, String>();
        }

        if(parameters == null) {
            parameters = new HashMap<String, String>();
        }

        String parameterString = generateParameterString(parameters);

        String url = this.serviceUrl + serviceName + "?" + parameterString.toString();

        // return new GsonRequest<T>(method, url, clazz, headers, payload, this, this);
        return new GsonRequest<T>(method, url, classType, headers, payload, this, this);
    }


    @Override
    public GsonRequest<T> postRequest(Class<T> clazz, String serviceName, Map<String, String> parameters, JsonObject payload, IGsonResponse<T> response) {
        this.response = response;

        Map<String, String> headers = new HashMap<String, String>();

        if(parameters == null) {
            parameters = new HashMap<String, String>();
        }

        String parameterString = generateParameterString(parameters);

        String url = this.serviceUrl + serviceName + "?" + parameterString.toString();

        Log.d(this.getClass().getSimpleName(), "POST to URL: " + url);
        Log.d(this.getClass().getSimpleName(), "Payload: " + gson.toJson(payload));

        return new GsonRequest<T>(Request.Method.POST, url, clazz, headers, payload, this, this);
    }

    @Override
    public GsonRequest<T> getRequest(Class<T> clazz, String serviceName, Map<String, String> parameters, JsonObject payload, IGsonResponse<T> response) {
        this.response = response;

        String parameterString = generateParameterString(parameters);

        String url = this.serviceUrl + serviceName + "?" + parameterString.toString();

        return new GsonRequest<T>(Request.Method.GET, url, clazz, null, this, this);
    }

    private String generateParameterString(Map<String, String> parameters) {
        StringBuilder parameterString = new StringBuilder();
        for(Map.Entry<String, String> entry : parameters.entrySet()) {
            if(parameterString.length() > 0) {
                parameterString.append("&");
            }

            parameterString.append(entry.getKey() + "=" + entry.getValue());
        }
        return parameterString.toString();
    }

    @Override
    public void onResponse(T model) {
        if(this.response != null) {
            this.response.onResponse(model);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if(this.response != null) {
            this.response.onErrorResponse(error);
        }
    }
}
