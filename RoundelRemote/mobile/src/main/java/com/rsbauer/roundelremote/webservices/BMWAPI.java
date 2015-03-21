package com.rsbauer.roundelremote.webservices;

import android.text.format.DateFormat;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rsbauer.roundelremote.webservices.interfaces.IGsonResponse;
import com.rsbauer.roundelremote.webservices.models.request.BMWAuthenticate;
import com.rsbauer.roundelremote.webservices.models.response.Execution;
import com.rsbauer.roundelremote.webservices.models.response.ExecutionStatus;
import com.rsbauer.roundelremote.webservices.models.response.UserLocation;
import com.rsbauer.roundelremote.webservices.models.response.Vehicle;
import com.rsbauer.roundelremote.webservices.models.response.VehicleLocation;
import com.rsbauer.roundelremote.webservices.models.response.VehicleStatus;
import com.rsbauer.roundelremote.webservices.models.response.Vehicles;
import com.rsbauer.roundelremote.webservices.models.response.BMWAuthenticated;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by astro on 2/8/15.
 */
public class BMWAPI {

    private final String serviceUrl = "https://b2vapi.bmwgroup.us/";

    public StringRequest serviceExecutionStatus(final BMWAuthenticated authenticated, Vehicle vehicle, ExecutionStatus status, final IGsonResponse<Execution> responseCallback) {
        return this.executeServiceExecutionStatus(authenticated, vehicle, status, responseCallback);
    }

    public StringRequest climateNow(final BMWAuthenticated authenticated, Vehicle vehicle, final IGsonResponse<Execution> responseCallback) {
        return this.executeCommand("CLIMATE_NOW", authenticated, vehicle, responseCallback);
    }


    public StringRequest hornBlow(final BMWAuthenticated authenticated, Vehicle vehicle, final IGsonResponse<Execution> responseCallback) {
        return this.executeCommand("HORN_BLOW", authenticated, vehicle, responseCallback);
    }

    public StringRequest lock(final BMWAuthenticated authenticated, Vehicle vehicle, final IGsonResponse<Execution> responseCallback) {
        return this.executeCommand("DOOR_LOCK", authenticated, vehicle, responseCallback);
    }

    public StringRequest headlightFlash(final BMWAuthenticated authenticated, Vehicle vehicle, final IGsonResponse<Execution> responseCallback) {
        return this.executeCommand("LIGHT_FLASH", authenticated, vehicle, responseCallback);
    }

    public StringRequest map(final BMWAuthenticated authenticated, Vehicle vehicle, final IGsonResponse<Execution> responseCallback) {
        return this.executeCommand("VEHICLE_FINDER", authenticated, vehicle, responseCallback);
    }

    public StringRequest executeServiceExecutionStatus(final BMWAuthenticated authenticated, Vehicle vehicle, ExecutionStatus status, final IGsonResponse<Execution> responseCallback) {

        // /webapi/v1/user/vehicles/WBA1J7C56EVW84688/serviceExecutionStatus?eventId=565738343638381C5FF0F800%40bmw.de&serviceType=HORN_BLOW
        String url = this.serviceUrl + "webapi/v1/user/vehicles/" + vehicle.getVin() + "/serviceExecutionStatus?eventId=" + status.getEventId() + "&serviceType=" + status.getServiceType();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Log.d("BMWAPI", response);

                        Gson gson = new Gson();

                        Execution executed = gson.fromJson(response, Execution.class);
                        if(responseCallback != null) {
                            responseCallback.onResponse(executed);
                        }
                    }
                }, new Response.ErrorListener() {
            private ExecutionStatus status;

            private Response.ErrorListener init(ExecutionStatus status) {
                this.status = status;
                return this;
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse.statusCode == 404) {
                    Execution executed = new Execution();
                    executed.setExecutionStatus(this.status);
                    if(responseCallback != null) {
                        responseCallback.onResponse(executed);
                    }

                    return;
                }

                if(error != null) {
                    Log.d("BMWAPI", "FAILED");
                    if(responseCallback != null) {
                        responseCallback.onErrorResponse(error);
                    }
                }
            }
        }.init(status)) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", authenticated.getToken_type() + " " + authenticated.getAccess_token());
                headers.put("Accept", "application/json");

                return headers;
            }

        };

        return request;
    }

    public StringRequest vehicleLocation(final BMWAuthenticated authenticated, Vehicle vehicle, UserLocation location, final IGsonResponse<VehicleLocation> responseCallback) {

        // /webapi/v1/user/vehicles/WBA1J7C56EVW84688/status?deviceTime=2015-01-31T15%3A05%3A12&dlat=41.40428905844819&dlon=-81.57176736749855
        // 2015-01-31T15%3A05%3A12
        // 2015-01-31T15:05:12
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String dateTimeString = dateformat.format(location.dateTime);

        String url = this.serviceUrl + "webapi/v1/user/vehicles/" + vehicle.getVin() + "/status?deviceTime=" + dateTimeString + "&dlat=" + location.latitude + "&dlon=" + location.longitude;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Log.d("BMWAPI", response);

                        Gson gson = new Gson();

                        VehicleLocation status = gson.fromJson(response, VehicleLocation.class);
                        if(responseCallback != null) {
                            responseCallback.onResponse(status);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    Log.d("BMWAPI", "FAILED");
                    if(responseCallback != null) {
                        responseCallback.onErrorResponse(error);
                    }
                }
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", authenticated.getToken_type() + " " + authenticated.getAccess_token());
                headers.put("Accept", "application/json");

                return headers;
            }

        };

        return request;
    }

    public StringRequest executeCommand(String command, final BMWAuthenticated authenticated, Vehicle vehicle, final IGsonResponse<Execution> responseCallback) {

        String url = this.serviceUrl + "webapi/v1/user/vehicles/" + vehicle.getVin() + "/executeService?serviceType=" + command;

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Log.d("BMWAPI", response);

                        Gson gson = new Gson();

                        Execution executed = gson.fromJson(response, Execution.class);
                        if(responseCallback != null) {
                            responseCallback.onResponse(executed);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    Log.d("BMWAPI", "FAILED");
                    if(responseCallback != null) {
                        responseCallback.onErrorResponse(error);
                    }
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                // TODO Auto-generated method stub
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected String getParamsEncoding() {
                return "utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", authenticated.getToken_type() + " " + authenticated.getAccess_token());
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");

                return headers;
            }

        };

        return request;
    }

    public GsonRequest<Vehicles> getVehicles(BMWAuthenticated authenticated, IGsonResponse<Vehicles> responseCallback) {

        RSBWebService<Vehicles> webService = new RSBWebService<Vehicles>(new Gson(), this.serviceUrl);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authenticated.getToken_type() + " " + authenticated.getAccess_token());

        Type type = new TypeToken<Vehicles>() {}.getType();
        return webService.request(type, Request.Method.GET, "webapi/v1/user/vehicles/", headers, null, null, responseCallback);
    }

    public StringRequest authenticate(final BMWAuthenticate requestModel, final IGsonResponse<BMWAuthenticated> responseCallback) {
        final Gson gson = new Gson();

        String url = this.serviceUrl + "webapi/oauth/token";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        // Log.d("BMWAPI", response);

                        BMWAuthenticated authenticated = gson.fromJson(response, BMWAuthenticated.class);
                        if(responseCallback != null) {
                            responseCallback.onResponse(authenticated);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    Log.d("BMWAPI", "FAILED");
                }
            }
        }) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestModel.toUrlEncoded().getBytes();
            }

            @Override
            public String getBodyContentType() {
                // TODO Auto-generated method stub
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected String getParamsEncoding() {
                return "utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Basic blF2NkNxdHhKdVhXUDc0eGYzQ0p3VUVQOjF6REh4NnVuNGNEanliTEVOTjNreWZ1bVgya0VZaWdXUGNRcGR2RFJwSUJrN3JPSg==");
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/x-www-form-urlencoded");

                return headers;
            }

        };

        return request;
    }
}