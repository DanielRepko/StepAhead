package com.danielrepko83.jordancampbell01.stepahead;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationTracker extends Service {

    //properties for location tracking
    private double currentDistance;
    private TextView distanceLabel;
    private Location lastLocation;
    private LocationCallback callBack;
    private static boolean paused = false;

    //properties for
    private static long startTime = 0L;
    private static TextView durationLabel;
    private static Handler customHandler = new Handler();
    private static long timeInMillis = 0L;
    private static long timeSwapBuff = 0L;
    private static long updatedTime = 0L;
    /**
     * LocationTracker extends Service and allows for location tracking
     * with real time updates, and can do this even when the app is in the
     * background. It also gives functionality to the home screen timer
     */
    public LocationTracker() {
        this.distanceLabel = MainFragment.distance;
        currentDistance = Double.parseDouble(distanceLabel.getText().toString());

        startTime = SystemClock.uptimeMillis();
        durationLabel = MainFragment.duration;
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
                        if(!paused) {
                            Location location = locationResult.getLastLocation();
                            //check if this is the first location update
                            if (lastLocation != null) {
                                //if not calculate the distance from the last location update
                                currentDistance += location.distanceTo(lastLocation) / 1000;
                                System.out.println(currentDistance);
                                distanceLabel.setText(String.format("%.2f", currentDistance));
                                lastLocation = location;
                            } else {
                                //if so just set the last location
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

        }
    };

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

        super.onDestroy();
    }
}
