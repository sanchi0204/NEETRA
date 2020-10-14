package com.example.safetapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class BackgroundWorker extends Service {

    private BroadcastReceiver broadcastReceiver;
    private int powerCounter;
    private Thread powerCounterThread;
    private FirestoreHandler firestoreHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        powerCounter = 0;                   //to check number of power button clicks
        createForegroundService();
        registerScreenListener();
        firestoreHandler = new FirestoreHandler(this);

        firestoreHandler.listenStressSignal();
    }

    private void registerScreenListener() {
        broadcastReceiver = new BroadcastReceiver() {
            @SuppressLint("MissingPermission")
            @Override
            public void onReceive(Context context, final Intent intent) {
                Log.d("BROADCAST MESSAGE", String.valueOf(powerCounter++));

                if (powerCounter == 1) {
                    powerCounterThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(5000);
                                powerCounter = 0;
                            } catch (InterruptedException e) {
                                Log.d("Test1", e.toString());
                            }
                        }
                    });
                    powerCounterThread.start();
                }

                if (powerCounter >= 5 && !Constants.isStressSignalSent) {
                    Log.d("Fire event", "SEND ALARM");
                    FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
                    if (ActivityCompat.checkSelfPermission(BackgroundWorker.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(BackgroundWorker.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful()) {
                                Location bounds = task.getResult();
                                firestoreHandler.sendStressSignal(new LatLng(28.4728342, 77.4835065));
                                sendStressCall();
                            }
                        }
                    });

                    Constants.isStressSignalSent = true;
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void sendStressCall() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Constants.STRESS_CALL_NUMBER));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.d("Call", "Sending call");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }

    private void createForegroundService() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {                         //TODO: add backwards compatibility
            NotificationChannel notificationChannel = new NotificationChannel(Constants.PRIMARY_CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }

            Notification.Builder builder = new Notification.Builder(this, Constants.PRIMARY_CHANNEL_ID)
                    .setContentText(Constants.CHANNEL_NAME)
                    .setContentText("Tracking Actions")
                    .setSmallIcon(R.drawable.logo)
                    .setAutoCancel(true);

            Notification notification = builder.build();
            startForeground(1, notification);
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
