package com.rsbauer.roundelremote.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.rsbauer.roundelremote.R;
import com.rsbauer.roundelremote.RoundelApplication;
import com.rsbauer.roundelremote.webservices.BMWAPI;
import com.rsbauer.roundelremote.webservices.interfaces.IGsonResponse;
import com.rsbauer.roundelremote.webservices.models.request.BMWAuthenticate;
import com.rsbauer.roundelremote.webservices.models.response.Execution;
import com.rsbauer.roundelremote.webservices.models.response.ExecutionStatus;
import com.rsbauer.roundelremote.webservices.models.response.UserLocation;
import com.rsbauer.roundelremote.webservices.models.response.Vehicle;
import com.rsbauer.roundelremote.webservices.models.response.VehicleLocation;
import com.rsbauer.roundelremote.webservices.models.response.Vehicles;
import com.rsbauer.roundelremote.webservices.models.response.BMWAuthenticated;

import java.util.Date;
import java.util.HashMap;

import roboguice.activity.RoboActionBarActivity;
import roboguice.inject.InjectView;

/*
Actions:

flash lights
honk horn
lock doors
run ventilation
map car

 */

public class MainActivity extends RoboActionBarActivity {

    private final Integer LOCATION_REFRESH_TIME = 60000;           // milliseconds
    private final Integer LOCATION_REFRESH_DISTANCE = 10;          // meters


    private BMWAuthenticated authenticated;
    private Vehicles vehicles;
    private HashMap<String, Integer> executionStatusForEventId;
    private LocationManager mLocationManager;
    private Location currentLocation;

    public static Integer MAX_EXECUTION_STATUS_RETRIES = 120;

    @InjectView(R.id.vehicleDescription) protected TextView vehicleDescription;

    @InjectView(R.id.fanButton) protected ImageButton fanButton;
    @InjectView(R.id.hornButton) protected ImageButton hornButton;
    @InjectView(R.id.headlightButton) protected ImageButton headlightButton;
    @InjectView(R.id.lockButton) protected ImageButton lockButton;
    @InjectView(R.id.mapButton) protected ImageButton mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        executionStatusForEventId = new HashMap<String, Integer>();

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                LOCATION_REFRESH_DISTANCE, mLocationListener);

        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            unpackIntentBundle(bundle);
        }

        if(this.authenticated == null) {
            authenticate();
        }
    }

    private void unpackIntentBundle(Bundle bundle) {
        Gson gson = new Gson();
        String vehicleString = bundle.getString("vehicles");
        String authenticateString = bundle.getString("authentication");
        Vehicles vehicles = gson.fromJson(vehicleString, Vehicles.class);
        BMWAuthenticated authenticated = gson.fromJson(authenticateString, BMWAuthenticated.class);

        setVehicles(vehicles);
        setAuthenticated(authenticated);
    }

    @Override
    public void onPause() {
        super.onPause();
        endGPS();
    }

    private void setLocation(Location location) {
        this.currentLocation = location;
        endGPS();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            //your code here
            setLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void authenticate() {
        Intent intent = new Intent(this, LoginActivity.class);

        Bundle bundle = new Bundle();
        /*
        bundle.putString("username", "username");
        bundle.putString("password", "password");
        intent.putExtras(bundle);
        */
        startActivity(intent);
    }

    public void setAuthenticated(BMWAuthenticated responseObject) {
        this.authenticated = responseObject;
    }

    public void setVehicles(Vehicles vehicles) {
        this.vehicles = vehicles;
        if(this.vehicles.getVehicles().size() > 0) {
            Vehicle vehicle = this.vehicles.getVehicles().get(0);
            String description = vehicle.getYearOfConstruction().toString() + " " + vehicle.getBrand() + " " + vehicle.getModel() + "\n" + vehicle.getColor();
            vehicleDescription.setText(description);
        }

    }

    public void getVehicles(BMWAuthenticated responseObject) {
        BMWAPI bmwService = new BMWAPI();

        RoundelApplication.getInstance().addToRequestQueue(bmwService.getVehicles(responseObject, createVehiclesCallback()));
    }

    private IGsonResponse<Vehicles> createVehiclesCallback() {
        return new IGsonResponse<Vehicles>() {
            @Override
            public void onResponse(Vehicles responseObject) {
                setVehicles(responseObject);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    Log.d("MainActivity", "Vehicles error");
                }
            }
        };
    }


    private IGsonResponse<BMWAuthenticated> createAuthenticateCallback() {
        return new IGsonResponse<BMWAuthenticated>() {
            @Override
            public void onResponse(BMWAuthenticated responseObject) {
                setAuthenticated(responseObject);
                Log.d("MainActivity:Authenticated", responseObject.getAccess_token());
                getVehicles(responseObject);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    Log.d("MainActivity", "Authentication error");
                }
            }
        };
    }

    public void headlightButtonPressed(View view) {
        BMWAPI bmwService = new BMWAPI();

        if(this.vehicles != null && this.vehicles.getVehicles().size() > 0) {
            disableButton(headlightButton);
            RoundelApplication.getInstance().addToRequestQueue(bmwService.headlightFlash(this.authenticated, this.vehicles.getVehicles().get(0), createCommandCallback(headlightButton)));
        }
    }

    public void lockButtonPressed(View view) {
        BMWAPI bmwService = new BMWAPI();

        if(this.vehicles != null && this.vehicles.getVehicles().size() > 0) {
            disableButton(lockButton);
            RoundelApplication.getInstance().addToRequestQueue(bmwService.lock(this.authenticated, this.vehicles.getVehicles().get(0), createCommandCallback(lockButton)));
        }
    }

    public void hornButtonPressed(View view) {
        BMWAPI bmwService = new BMWAPI();

        if(this.vehicles != null && this.vehicles.getVehicles().size() > 0) {
            disableButton(hornButton);
            RoundelApplication.getInstance().addToRequestQueue(bmwService.hornBlow(this.authenticated, this.vehicles.getVehicles().get(0), createCommandCallback(hornButton)));
        }
    }

    public void fanButtonPressed(View view) {
        BMWAPI bmwService = new BMWAPI();

        if(this.vehicles != null && this.vehicles.getVehicles().size() > 0) {
            disableButton(fanButton);
            RoundelApplication.getInstance().addToRequestQueue(bmwService.climateNow(this.authenticated, this.vehicles.getVehicles().get(0), createCommandCallback(fanButton)));
        }
    }

    public void mapButtonPressed(View view) {
        BMWAPI bmwService = new BMWAPI();

        if(this.vehicles != null && this.vehicles.getVehicles().size() > 0) {
            disableButton(mapButton);
            RoundelApplication.getInstance().addToRequestQueue(bmwService.map(this.authenticated, this.vehicles.getVehicles().get(0), createMapCommandCallback(mapButton)));
        }
    }

    private IGsonResponse<Execution> createMapCommandCallback(ImageButton mapButton) {
        return new IGsonResponse<Execution>() {
            private ImageButton button;

            private IGsonResponse<Execution> init(ImageButton button) {
                this.button = button;
                return this;
            }

            @Override
            public void onResponse(Execution responseObject) {
                if(responseObject.getExecutionStatus() != null) {
                    checkStatus(responseObject, this.button);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    resetButtonForService(this.button);
                    toastMessage("Map request failed.");
                }

            }
        }.init(mapButton);

    }

    private IGsonResponse<VehicleLocation> createVehicleLocationCommandCallback(ImageButton mapButton) {
        return new IGsonResponse<VehicleLocation>() {
            private ImageButton button;

            private IGsonResponse<VehicleLocation> init(ImageButton button) {
                this.button = button;
                return this;
            }

            @Override
            public void onResponse(VehicleLocation responseObject) {
                // Log.d("MainActivity:VehLoc", responseObject.getVehicleStatus().getPosition().getStatus());
                resetButtonForService(this.button);
                showMap(responseObject);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    resetButtonForService(this.button);
                    toastMessage("Failed to get vehicle location.");
                }
            }
        }.init(mapButton);
    }

    private void showMap(VehicleLocation vehicleLocation) {
        if(vehicleLocation.getVehicleStatus().getPosition().getStatus().equals("INVALID")) {
            toastMessage("Unable to get vehicle location.");
            return;
        }

        Intent intent = new Intent(this, MapsActivity.class);
        Gson gson = new Gson();
        String vechileLocationJson = gson.toJson(vehicleLocation);
        String locationJson = gson.toJson(this.currentLocation);

        Bundle bundle = new Bundle();
        bundle.putString("VehicleLocation", vechileLocationJson);
        bundle.putString("UserLocation", locationJson);

        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void disableButton(ImageButton imageButton) {
        imageButton.setSelected(true);
        imageButton.setEnabled(false);
    }

    private void enableButton(ImageButton imageButton) {
        imageButton.setSelected(false);
        imageButton.setEnabled(true);
    }

    private void requestExecutionStatus(ImageButton imageButton, Execution responseObject) {
        BMWAPI bmwService = new BMWAPI();
        RoundelApplication.getInstance().addToRequestQueue(bmwService.serviceExecutionStatus(this.authenticated, this.vehicles.getVehicles().get(0), responseObject.getExecutionStatus(), createExecutionStatusCallback(imageButton)));
    }

    private void resetButtonForService(ImageButton imageButton) {
        enableButton(imageButton);
    }

    private Integer getRetryCountForEventId(String eventId) {
        Integer count = this.executionStatusForEventId.get(eventId);
        if(count == null) {
            return new Integer(0);
        }

        return count;
    }

    private void setRetryCountForEventId(String eventId, Integer count) {
        this.executionStatusForEventId.put(eventId, count);
    }

    private void toastMessage(String message) {
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    private IGsonResponse<Execution> createExecutionStatusCallback(ImageButton imageButton) {

        return new IGsonResponse<Execution>() {

            private ImageButton imageButton;

            private IGsonResponse<Execution> init(ImageButton imageButton) {
                this.imageButton = imageButton;
                return this;
            }

            @Override
            public void onResponse(Execution responseObject) {
                if(responseObject.getExecutionStatus() != null) {
                    checkStatus(responseObject, this.imageButton);
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    resetButtonForService(this.imageButton);
                    toastMessage("Command verification failed");
                }
            }
        }.init(imageButton);
    }

    private void checkStatus(Execution responseObject, ImageButton imageButton) {
        ExecutionStatus status = responseObject.getExecutionStatus();
        if(status.getEventId() != null) {

            Integer count2 = getRetryCountForEventId(status.getEventId());
            Log.d("MainAct.onResp", "Count: " + count2 + ": " + status.getStatus());

            if(status.getStatus().equals("TIMED_OUT")) {
                executionStatusForEventId.remove(status.getEventId());
                resetButtonForService(imageButton);
                toastMessage(status.getServiceType() +  ": Request timed out.");
                return;
            }

            if(status.getStatus().equals("EXECUTED") || status.getStatus().equals("DELIVERED")) {
                executionStatusForEventId.remove(status.getEventId());
                if(imageButton == mapButton) {
                    getVehicleLocation();
                    return;
                }

                resetButtonForService(imageButton);
                toastMessage(status.getServiceType() +  ": Success!");
            }
            else {
                Integer count = getRetryCountForEventId(status.getEventId());
                count = count + 1;
                if(count < MAX_EXECUTION_STATUS_RETRIES) {
                    setRetryCountForEventId(status.getEventId(), count);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        private ImageButton imageButton;
                        private Execution responseObject;

                        private Runnable init(ImageButton imageButton, Execution responseObject) {
                            this.imageButton = imageButton;
                            this.responseObject = responseObject;
                            return this;
                        }

                        public void run() {
                            // Actions to do after 10 seconds
                            requestExecutionStatus(this.imageButton, this.responseObject);
                        }
                    }.init(imageButton, responseObject), 2000);

                }
                else {
                    executionStatusForEventId.remove(status.getEventId());
                    resetButtonForService(imageButton);
                    toastMessage(status.getServiceType() +  ": Status unknown. Unable to get status.");

                }
            }
        }
    }


    private IGsonResponse<Execution> createCommandCallback(ImageButton imageButton) {
        return new IGsonResponse<Execution>() {
            private ImageButton imageButton;

            private IGsonResponse<Execution> init(ImageButton imageButton) {
                this.imageButton = imageButton;
                return this;
            }

            @Override
            public void onResponse(Execution responseObject) {
                if(responseObject.getExecutionStatus().getEventId() != null) {
                    executionStatusForEventId.put(responseObject.getExecutionStatus().getEventId(), 0);
                    requestExecutionStatus(this.imageButton, responseObject);
                }
                Log.d("MainActivity", "Success");
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                if(error != null) {
                    Log.d("MainActivity", "Authentication error");
                }
            }
        }.init(imageButton);
    }

    private void getVehicleLocation() {
        BMWAPI bmwService = new BMWAPI();

        if(this.currentLocation != null) {
            UserLocation userLocation = new UserLocation();
            userLocation.dateTime = new Date();
            userLocation.longitude = Double.toString(this.currentLocation.getLongitude());
            userLocation.latitude = Double.toString(this.currentLocation.getLatitude());

            RoundelApplication.getInstance().addToRequestQueue(bmwService.vehicleLocation(this.authenticated, this.vehicles.getVehicles().get(0), userLocation, createVehicleLocationCommandCallback(mapButton)));
        }
        else {
            toastMessage("Your current location is unknown.");
        }
    }

    private void endGPS() {
        mLocationManager.removeUpdates(mLocationListener);
    }

    /*
    public class SetAuthAndVehicleReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
            Gson gson = new Gson();
            Bundle bundle = intent.getExtras();
            String vehicleString = bundle.getString("vehicles");
            String authenticateString = bundle.getString("authentication");
            Vehicles vehicles = gson.fromJson(vehicleString, Vehicles.class);
            BMWAuthenticated authenticated = gson.fromJson(authenticateString, BMWAuthenticated.class);

            setVehicles(vehicles);
            setAuthenticated(authenticated);
        }
    }
    */

}
