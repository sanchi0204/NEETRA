package com.example.safetapp;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.safetapp.Model.CrimeCounter;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GeoFire extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,

        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GeoQueryDataEventListener {

    private GoogleMap mMap;
    private TextToSpeech mTTS;
    ImageButton refresh;
    BottomNavigationView bottomNavigationView;


    private static final int MY_PERMISSION_REQUEST_CODE = 240;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 260;

    private LocationRequest locationRequest;
    private GoogleApiClient googleApiClient;
    private Circle lastUserCircle;
    private long pulseDuration = 2300;
    private ValueAnimator lastPulseAnimator;
    private Location location;
    protected LocationManager locationManager;
    protected LocationManager locationManager1;
    protected LocationListener locationListener;
    protected Context context;

    private static int UPDATE_INTERVAL = 5000;
    private static int FASTEST_INTERVAL = 3000;
    private static int DISPLACEMENT = 10;
    private double Latitude;
    private double Longitude;
    private int molestation, trafficking, kidnaping, rape;
    private CrimeCounter crimeCounter = new CrimeCounter();
    private List<LatLng> dangerousAreaLocation;
    private DatabaseReference crimeDataRef = FirebaseDatabase.getInstance().getReference().child("CrimeCounter");
    int colour[] = new int[4];
    int maxFinder[] = new int[4];
    String Maxname, Latitude1, Longitude1;
    int Max, pos, color;

    VerticalSeekBar seekBar;
    Vibrator vibrator;

    Marker myLocation;
    DatabaseReference ref;
    com.firebase.geofire.GeoFire geoFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fire);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
//        refresh = findViewById(R.id.refresh);
//        refresh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                startActivity(getIntent());
//            }
//        });

        Latitude1 = getIntent().getStringExtra("Lati");
        Longitude1 = getIntent().getStringExtra("loni");
        color = getIntent().getIntExtra("Colour", 0);

        // vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        dangerousAreaLocation = new ArrayList<>();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        //code for handling bottom navigation of app

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_crime_map);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_crime_map:
                        return true;

                    case R.id.nav_report_crime:
                        startActivity(new Intent(getApplicationContext(), Report_Crime.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_fir_token:
                        startActivity(new Intent(getApplicationContext(), Fir_token.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;


                }
                return false;
            }
        });


//        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if (status == TextToSpeech.SUCCESS) {
//                    int result = mTTS.setLanguage(Locale.ENGLISH);
//
//                    if (result == TextToSpeech.LANG_MISSING_DATA
//                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("TTS", "Language not supported");
//                    }
//                } else {
//                    Log.e("TTS", "Initialization failed");
//                }
//            }
//        });

        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mMap.animateCamera(CameraUpdateFactory.zoomTo(i), 2000, null);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("My Location");
        geoFire = new com.firebase.geofire.GeoFire(ref);
//        locationRequest = new LocationRequest();
//        locationRequest.setInterval(UPDATE_INTERVAL);
//        locationRequest.setFastestInterval(FASTEST_INTERVAL);
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setSmallestDisplacement(DISPLACEMENT);
//        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
//
//        googleApiClient.connect();
//        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        Latitude = location.getLatitude();
//        Longitude = location.getLongitude();
//        locationManager1=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        locationManager1.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,);
//        Latitude1=Latitude1.replace(".","!");
//        Longitude1=Longitude1.replace(".","!");
//        Latitude1=Latitude1.substring(0,Latitude1.length()-11);
//        Longitude1=Longitude1.substring(0,Longitude1.length()-10);
//
//        final String finalLat = Latitude1;
//        final String finalLon=Longitude1;
//        crimeDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                crimeCounter=dataSnapshot.child("Data").child(finalLat +" "+finalLon).getValue(CrimeCounter.class);
//                Toast.makeText(GeoFire.this, "TEst", Toast.LENGTH_SHORT).show();
//                molestation=crimeCounter.getMolestation();
//                rape=crimeCounter.getRape();
//                trafficking=crimeCounter.getTrafficking();
//                kidnaping=crimeCounter.getKidnapping();
//                maxFinder[0]=molestation;
//                maxFinder[1]=rape;
//                maxFinder[2]=trafficking;
//                maxFinder[3]=kidnaping;
//                Max=maxFinder[0];
//                pos=0;
//                for(int i=1;i<maxFinder.length;i++)
//                {
//                    if(maxFinder[i]>Max)
//                    {
//                        Max=maxFinder[i];
//                        pos=i;
//                    }
//                }
//                if(pos==1) {
//                    colour[0] = 0x44ff0000;//red;
//                }
//                else if(pos==0) {
//                    colour[0] = 0x4400ecff;//blue
//                }
//                else if(pos==2) {
//                    colour[0] = 0x4434e10d;//green
//                }
//                else if(pos==3)
//                {
//                    colour[0]=0x44ff0000;//purple
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        setUpLocation();
        initArea();
    }


    private void getData() {

//        Latitude1=Latitude1.replace(".","!");
//        Longitude1=Longitude1.replace(".","!");
//        Latitude1=Latitude1.substring(0,Latitude1.length()-11);
//        Longitude1=Longitude1.substring(0,Longitude1.length()-10);
//
//        final String finalLat = Latitude1;
//        final String finalLon=Longitude1;
//        crimeDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                crimeCounter=dataSnapshot.child("Data").child(finalLat +" "+finalLon).getValue(CrimeCounter.class);
//                Toast.makeText(GeoFire.this, "TEst", Toast.LENGTH_SHORT).show();
//                molestation=crimeCounter.getMolestation();
//                rape=crimeCounter.getRape();
//                trafficking=crimeCounter.getTrafficking();
//                kidnaping=crimeCounter.getKidnapping();
//                maxFinder[0]=molestation;
//                maxFinder[1]=rape;
//                maxFinder[2]=trafficking;
//                maxFinder[3]=kidnaping;
//                Max=maxFinder[0];
//                pos=0;
//                for(int i=1;i<maxFinder.length;i++)
//                {
//                    if(maxFinder[i]>Max)
//                    {
//                        Max=maxFinder[i];
//                        pos=i;
//                    }
//                }
//                if(pos==1) {
//                    colour[0] = 0x44ff0000;//red;
//                }
//                    else if(pos==0) {
//                    colour[0] = 0x4400ecff;//blue
//                }
//                    else if(pos==2) {
//                    colour[0] = 0x4434e10d;//green
//                }
//                else if(pos==3)
//                {
//                    colour[0]=0x44a30fe5;//purple
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }


    private void initArea() {
        dangerousAreaLocation.add(new LatLng(28.7525, 77.4988));
        dangerousAreaLocation.add(new LatLng(28.6482, 77.2270));
        dangerousAreaLocation.add(new LatLng(28.5221, 77.2102));
        dangerousAreaLocation.add(new LatLng(28.6640, 77.2712));
        colour[0] = color;
        colour[1] = 0x44008000;
        colour[2] = 0x440397F7;
        colour[3] = 0x449355F2;
    }
    ArrayList<LatLng>latLngs=new ArrayList<>();


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (checkPlayServices()) {
                        buildGoogleApiClient();
                        createLocationRequest();
                        displayLocation();
                    }
                }
                break;


        }
    }

    private void setUpLocation() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, MY_PERMISSION_REQUEST_CODE);
        } else {
            if (checkPlayServices()) {
                buildGoogleApiClient();
                createLocationRequest();
                displayLocation();
            }
        }
    }

    private void displayLocation() {

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
            return;

        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (location != null) {
            Latitude = location.getLatitude();
            Longitude = location.getLongitude();


            //adding to firebase
            geoFire.setLocation("You", new GeoLocation(Latitude, Longitude), new com.firebase.geofire.GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {


                    //adding marker
                    if (myLocation != null)
                        myLocation.remove();  //remove old marker
                    myLocation = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(Latitude, Longitude))
                            .title("You"));
                    addPulsatingEffect(location);


                    //move camera to this location
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 12.0f));

                }
            });


           // Toast.makeText(this, String.format("Your location has been changed to : %f, %f", Latitude, Longitude), Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Error detecting your location", Toast.LENGTH_SHORT).show();


    }

    private void addPulsatingEffect(Location location) {
        if (lastPulseAnimator != null) {
            lastPulseAnimator.cancel();
            Log.d("onLocationUpdated: ", "cancelled");
        }
        final LatLng userLatlng = new LatLng(Latitude, Longitude);
        if (lastUserCircle != null)
            lastUserCircle.setCenter(userLatlng);
        lastPulseAnimator = valueAnimate(location.getAccuracy(), pulseDuration, new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (lastUserCircle != null)
                    lastUserCircle.setRadius((Float) animation.getAnimatedValue());
                else {
                    lastUserCircle = mMap.addCircle(new CircleOptions()
                            .center(userLatlng)
                            .radius((Float) animation.getAnimatedValue())
                            .strokeColor(Color.BLACK)
                            .fillColor(0x449355F2)
                            .strokeWidth(3f));
                }
            }
        });
    }


    private ValueAnimator valueAnimate(float accuracy, long pulseDuration, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        Log.d("valueAnimate: ", "called");
        ValueAnimator va = ValueAnimator.ofFloat(2300, 2500);
        va.setDuration(pulseDuration);
        va.addUpdateListener(animatorUpdateListener);
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.setRepeatMode(ValueAnimator.RESTART);

        va.start();
        return va;
    }


    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    private void buildGoogleApiClient() {

        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        googleApiClient.connect();
    }

    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(this, "This device is not supported!", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int i = 0;
        String l = String.valueOf(Longitude);

        //Create Dangerous area

        for (LatLng latLng : dangerousAreaLocation) {
            while (i < 4) {
                mMap.addCircle(new CircleOptions()
                        .center(latLng)
                        .radius(2500)   //1km
                        .strokeColor(Color.BLACK)
                        .fillColor(colour[i])
                        .strokeWidth(3.0f)
                        .clickable(true)
                );

                i++;
                break;
            }

            GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 1f);
            geoQuery.addGeoQueryDataEventListener(GeoFire.this);
        }


        //adding geoquery
        //1f=10000m=1km

    }

    @Override
    protected void onDestroy() {
        if (mTTS != null) {
            mTTS.stop();
            mTTS.shutdown();
        }

        super.onDestroy();
    }

    private void sendNotification(String title, String content) {
        Notification.Builder builder = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content);

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(this, GeoFire.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;

        notificationManager.notify(new Random().nextInt(), notification);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        displayLocation();
        startLocationUpdates();
    }

    private void startLocationUpdates() {

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED))
            return;
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location l) {
        location = l;
        displayLocation();

    }

    @Override
    public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {
        sendNotification("Safety App", String.format(("%s Entered dangerous Area"), location));
        String text = "Alert! You entered dangerous area.Be careful.";
        vibrator.vibrate(2000);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDataExited(DataSnapshot dataSnapshot) {
        sendNotification("Safety App", String.format(("%s Exited dangerous Area"), location));
        String text = "Thank God! You exited dangerous area.You are safe now.";
        vibrator.vibrate(2000);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);

    }

    @Override
    public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {
        Toast.makeText(GeoFire.this, String.format("%s Moved within the dangerous area [%f, %f]", location, location.latitude, location.longitude), Toast.LENGTH_SHORT).show();
        String text = "Alert! You are in dangerous area.Be careful.";
        vibrator.vibrate(2000);
        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {

    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Log.e("ERROR", "" + error);
    }
}
