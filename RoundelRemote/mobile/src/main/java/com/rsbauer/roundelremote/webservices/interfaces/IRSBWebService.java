package com.rsbauer.roundelremote.webservices.interfaces;

import com.google.gson.JsonObject;
import com.rsbauer.roundelremote.webservices.GsonRequest;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by astro on 2/8/15.
 */
public interface IRSBWebService<T> {
    public GsonRequest<T> request(Type classType, int method, String serviceName, Map<String, String> headers, Map<String, String> parameters, JsonObject payload, IGsonResponse<T> response);
    public GsonRequest<T> postRequest(Class<T> clazz, String serviceName, Map<String, String> parameters, JsonObject payload, IGsonResponse<T> response);
    public GsonRequest<T> getRequest(Class<T> clazz, String serviceName, Map<String, String> parameters, JsonObject payload, IGsonResponse<T> response);
}
