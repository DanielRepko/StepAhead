package com.danielrepko83.jordancampbell01.stepahead;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danielrepko83.jordancampbell01.stepahead.Object_Classes.Weight;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationTracker extends Service {

    //properties for location tracking
    private static double currentDistance;
    private static TextView distanceLabel;
    public static Location lastLocation;
    private LocationCallback callBack;
    private static boolean paused = false;
    private static int distanceUnit = 0;

    //properties for timer
    private static long startTime = 0L;
    private static TextView durationLabel;
    private static Handler customHandler = new Handler();
    private static long timeInMillis = 0L;
    private static long timeSwapBuff = 0L;
    private static long updatedTime = 0L;

    //properties for calorie tracking
    private static Weight weight;
    private static TextView calorieLabel;
    private static double calories = 0;
    private static int timeInterval = 0;
    private double distanceInterval = 0;


    /**
     * LocationTracker extends Service and allows for location tracking
     * with real time updates, and can do this even when the app is in the
     * background. It also gives functionality to the home screen timer
     */
    public LocationTracker() {
        this.distanceLabel = MainFragment.distance;
        currentDistance = Double.parseDouble(distanceLabel.getText().toString());
        //lastLocation = null;

        startTime = SystemClock.uptimeMillis();
        durationLabel = MainFragment.duration;

        calorieLabel = MainFragment.calories;
        DatabaseHandler db = new DatabaseHandler(distanceLabel.getContext());
        if(db.getLastWeight() != null) {
            weight = db.getLastWeight();
        }
        db.close();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;

    }

    @Override
    public void onCreate(){
        super.onCreate();
        requestLocationUpdates();
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
    }

    /**
     * this method starts the location tracking
     */
    public void requestLocationUpdates(){
            LocationRequest request = new LocationRequest();
            request.setInterval(10000);
            request.setFastestInterval(5000);
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission == PackageManager.PERMISSION_GRANTED) {
                callBack = new LocationCallback(){
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        //check to see if the pause button has been pressed
                        if(!paused) {
                            Location location = locationResult.getLastLocation();
                            //check if this is the first location update
                            if (lastLocation != null) {
                                //if not calculate the distance from the last location update
                                if(distanceUnit == 0){
                                    currentDistance += location.distanceTo(lastLocation) / 1000;
                                } else if(distanceUnit == 1){
                                    currentDistance += location.distanceTo(lastLocation) * 0.00062137;
                                }

                                System.out.println(currentDistance);


                                //update the distance textview
                                distanceLabel.setText(String.format("%.2f", currentDistance));
                                //set the last location
                                lastLocation = location;

                                //add calories
                                if(weight != null) {
                                    //add distance traveled in meters onto distanceInterval
                                    if(distanceUnit == 0) {
                                        distanceInterval += currentDistance * 1000;
                                    } else if(distanceUnit == 1) {
                                        distanceInterval += currentDistance / 0.00062137;
                                    }
                                    if (distanceInterval >= 533) {
                                        calories += weight.getPounds() * 0.25;
                                        calorieLabel.setText(Math.round(calories) + "");
                                        distanceInterval = 0;
                                    }
                                }
                            } else {
                                //if paused button has been pressed just set the last location
                                lastLocation = location;
                            }
                        }
                    }
                };
                client.requestLocationUpdates(request, callBack, null);
            }
    }


    public static void pause(){
        //if tracker is already paused
        if(paused == true){
            //unpause the tracker
            paused = false;
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
        } else {
            //otherwise pause tracker
            paused = true;
            timeSwapBuff+= timeInMillis;
            customHandler.removeCallbacks(updateTimerThread);
        }
    }

    private static Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMillis = SystemClock.uptimeMillis() - startTime;
            updatedTime =  timeInMillis + timeSwapBuff;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            durationLabel.setText(mins+":"+String.format("%02d",secs));
            customHandler.postDelayed(this,0);

            if(weight != null){
                //check if 20 seconds have passed (thats how often I want it to update)
                if(((secs % 20) == 0) && (secs > timeInterval) || ((secs % 20) == 0) && (mins > 0) && (secs < timeInterval)){
                    //don't need to calculate for weight in kg because they
                    //technically weigh the same whether it is lbs or kg
                    calories+= 0.03 * weight.getPounds();
                    calorieLabel.setText(Math.round(calories)+"");
                    timeInterval = secs;
                }
            }

        }
    };

    //change the unit of measurement for distance travelled if necessary
    public static void adjustUnit(int preference){
        //check if currentDistance has a value
        //if not then there is no need to convert it
        if(currentDistance != 0){
            //check to see if the current preference is different
            //this is because the method may be called even if the preference was not changed
            if(preference != distanceUnit){
                //make sure LocationTracker is using the unit selected in settings
                distanceUnit = preference;

                switch(distanceUnit){
                    //if the unit has gone from miles to km
                    case 0:
                        //convert distance travelled to km
                        currentDistance/= 0.62137;
                        distanceLabel.setText(String.format("%.2f", currentDistance));
                        break;
                    //if the unit has gone from km to miles
                    case 1:
                        //convert distance travelled to miles
                        currentDistance*= 0.62137;
                        distanceLabel.setText(String.format("%.2f", currentDistance));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        client.removeLocationUpdates(callBack);
        distanceLabel.setText("0.00");
        lastLocation = null;
        currentDistance = 0;

        durationLabel.setText("0:00");
        timeSwapBuff = 0L;
        customHandler.removeCallbacks(updateTimerThread);

        calories = 0;
        calorieLabel.setText("0");
        timeInterval = 0;
        distanceInterval = 0;

        paused = false;

        super.onDestroy();
    }


}
