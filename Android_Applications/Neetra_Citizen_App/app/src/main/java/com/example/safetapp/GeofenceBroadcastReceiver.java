package com.example.safetapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiv";


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        Toast.makeText(context, "Geofence triggered...", Toast.LENGTH_SHORT).show();

//        context = getClass();
        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }

        //geoFence transitions to notify
        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType) {
            //notification when you
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "You entered an eve-teasing prone area", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("You entered an eve-teasing prone area", "", GeoFence.class);

                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "You are in a dangerous area. This area is prone to eve-teasing", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("You are in a dangerous area. This area is prone to eve-teasing", "", GeoFence.class);

                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "You just took an exit from an eve-teasing prone area. Stay safe.", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("You just took an exit from an eve-teasing prone area. Stay safe.", "", GeoFence.class);

                break;
        }

    }

//
//
//
}