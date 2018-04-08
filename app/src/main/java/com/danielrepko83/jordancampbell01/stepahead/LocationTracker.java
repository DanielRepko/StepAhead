package com.danielrepko83.jordancampbell01.stepahead;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.TextView;

public class LocationTracker extends Service {

    private View view;

    public LocationTracker() {
    }

    public LocationTracker(View view){
        this.view = view;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    private void changeText(){
        TextView distance = view.findViewById(R.id.distance);
        distance.setText("it works");
    }


}
