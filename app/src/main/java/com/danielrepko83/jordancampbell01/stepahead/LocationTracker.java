package com.danielrepko83.jordancampbell01.stepahead;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LocationTracker extends Service {
    public LocationTracker() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
