package com.danielrepko83.jordancampbell01.stepahead;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationTracker extends Service {

    private double currentDistance;
    private TextView distanceLabel;

    /**
     * LocationTracker extends Service and allows for location tracking
     * with real time updates, and can do this even when the app is in the
     * background
     */
    public LocationTracker() {
        this.distanceLabel = MainFragment.distance;
        currentDistance = Double.parseDouble(distanceLabel.getText().toString());
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
    }

    public void requestLocationUpdates(){
        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request, new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    System.out.println(locationResult);


                    //distanceLabel.setText(currentDistance+"");
                }
            }, null);
        }
    }



}
