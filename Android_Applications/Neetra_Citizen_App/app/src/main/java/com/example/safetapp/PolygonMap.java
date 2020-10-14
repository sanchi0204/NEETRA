package com.example.safetapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class PolygonMap extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnPolygonClickListener {

    ArrayList<LatLng> latLngs = new ArrayList<>();
    private GoogleMap mMap;
    private int COLOR_WHITE_ARGB = 0xffffffff;
    private int COLOR_RED_ARGB = 0xbcf44336;
    private int COLOR_PURPLE_ARGB = 0xbc9C27B0;
    private int COLOR_GREEN_ARGB = 0xbc4CAF50;
    private int COLOR_BLUE_ARGB = 0xbc1565C0;
    private int COLOR_BLACK_ARGB = 0xff000000;
    private int POLYGON_STROKE_WIDTH_PX = 6;
    BottomNavigationView bottomNavigationView;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polygon_map);

        //code to check for internet connection availability
        if(!AppStatus.getInstance(this).isOnline()) {

            openDialog();  // if no internet; then open dialog to connect

        } else {

            Toast.makeText(this,"Internet Connection Available",Toast.LENGTH_SHORT).show();
            //else display Toast

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        //code for handling bottom navigation of app

        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.nav_crime_map);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.nav_crime_map:
                        return true;

                    case R.id.nav_fir_token:
                        startActivity(new Intent(getApplicationContext(), Fir_token.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_report_crime:
                        startActivity(new Intent(getApplicationContext(), Report_Crime.class));
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        return true;

                    case R.id.nav_geofence:
                        startActivity(new Intent(getApplicationContext(), GeoFence.class));
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




    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final Polygon CP = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(new LatLng(28.635284624, 77.208159217),
                        new LatLng(28.633281978, 77.209237221),
                        new LatLng(28.631474238, 77.209889959),
                        new LatLng(28.629172803, 77.209998206),
                        new LatLng(28.619943045, 77.218974326),
                        new LatLng(28.626280800, 77.231471764),
                        new LatLng(28.630508462, 77.222074860),
                        new LatLng(28.631191186, 77.222433958),
                        new LatLng(28.632413508, 77.222637394),
                        new LatLng(28.633385484, 77.222618669),
                        new LatLng(28.634728000, 77.222329485),
                        new LatLng(28.635364813, 77.220673753),
                        new LatLng(28.637981535, 77.219787896),
                        new LatLng(28.641717325, 77.217786417),
                        new LatLng(28.640464750, 77.208440061),
                        new LatLng(28.635284624, 77.208159217)));


        CP.setTag("CP");
        stylePolygon(CP);
        //polygon1.setFillColor(Color.RED);

        final Polygon KG = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(new LatLng(28.665360039, 77.230238207),
                        new LatLng(28.665496431, 77.230951702),
                        new LatLng(28.665833983, 77.231341498),
                        new LatLng(28.666007314, 77.231519788),
                        new LatLng(28.666409784, 77.231690712),
                        new LatLng(28.666837378, 77.231837963),
                        new LatLng(28.667542689, 77.231568288),
                        new LatLng(28.667823862, 77.231371930),
                        new LatLng(28.668118831, 77.231370051),
                        new LatLng(28.668445668, 77.231218329),
                        new LatLng(28.668552112, 77.231006866),
                        new LatLng(28.668756376, 77.230747104),
                        new LatLng(28.668836808, 77.230632364),
                        new LatLng(28.668924038, 77.230405205),
                        new LatLng(28.669127425, 77.229892231),
                        new LatLng(28.669071978, 77.229053227),
                        new LatLng(28.669116400, 77.228050159),
                        new LatLng(28.668882401, 77.226912608),
                        new LatLng(28.668584189, 77.225263350),
                        new LatLng(28.667462595, 77.224693641),
                        new LatLng(28.666231353, 77.224467054),
                        new LatLng(28.665460457, 77.225008144),
                        new LatLng(28.665671879, 77.226691525),
                        new LatLng(28.665888322, 77.227859792),
                        new LatLng(28.666072465, 77.229326752),
                        new LatLng(28.665360039, 77.230238207)));

        KG.setTag("KG");
        stylePolygon(KG);

        final Polygon NDRST = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(new LatLng(28.643407666, 77.218154505),
                        new LatLng(28.642323318, 77.218838168),
                        new LatLng(28.641420822, 77.219224618),
                        new LatLng(28.639678290, 77.219943556),
                        new LatLng(28.638372788, 77.221025795),
                        new LatLng(28.637373746, 77.222005045),
                        new LatLng(28.636744747, 77.222573852),
                        new LatLng(28.635664532, 77.223436691),
                        new LatLng(28.634983131, 77.224404946),
                        new LatLng(28.634194011, 77.230331665),
                        new LatLng(28.635131282, 77.230644899),
                        new LatLng(28.636067982, 77.230959687),
                        new LatLng(28.636865759, 77.230565773),
                        new LatLng(28.637838709, 77.230341360),
                        new LatLng(28.638806901, 77.230916033),
                        new LatLng(28.639305403, 77.231088932),
                        new LatLng(28.640271974, 77.231536563),
                        new LatLng(28.640889504, 77.231574362),
                        new LatLng(28.641682849, 77.231648886),
                        new LatLng(28.642358587, 77.231687162),
                        new LatLng(28.642629192, 77.230917881),
                        new LatLng(28.642842113, 77.230190482),
                        new LatLng(28.643022215, 77.229722094),
                        new LatLng(28.643231395, 77.229253050),
                        new LatLng(28.643501050, 77.228584543),
                        new LatLng(28.643650901, 77.228251566),
                        new LatLng(28.644185575, 77.227519354),
                        new LatLng(28.644395741, 77.227087559),
                        new LatLng(28.644844043, 77.226120033),
                        new LatLng(28.645283274, 77.225448390),
                        new LatLng(28.645723808, 77.224611424),
                        new LatLng(28.646666895, 77.223406818),
                        new LatLng(28.645750539, 77.217484390),
                        new LatLng(28.643407666, 77.218154505)));
        NDRST.setTag("NDRST");
        stylePolygon(NDRST);

        final Polygon Saket = googleMap.addPolygon(new PolygonOptions()
                .clickable(true)
                .add(new LatLng(28.523094820, 77.202709413),
                        new LatLng(28.522723174, 77.203450770),
                        new LatLng(28.522327753, 77.203433336),
                        new LatLng(28.521962813, 77.203109777),
                        new LatLng(28.521935309, 77.202294858),
                        new LatLng(28.522033697, 77.201649292),
                        new LatLng(28.521118336, 77.201491338),
                        new LatLng(28.520688594, 77.202961123),
                        new LatLng(28.520167536, 77.205296220),
                        new LatLng(28.519771440, 77.207576853),
                        new LatLng(28.519607686, 77.213093475),
                        new LatLng(28.520612827, 77.213431122),
                        new LatLng(28.521272473, 77.213118060),
                        new LatLng(28.522364273, 77.212372357),
                        new LatLng(28.522797048, 77.212738148),
                        new LatLng(28.522918974, 77.213814112),
                        new LatLng(28.523223018, 77.213676615),
                        new LatLng(28.523913029, 77.213444028),
                        new LatLng(28.525056822, 77.213221111),
                        new LatLng(28.525816430, 77.212003954),
                        new LatLng(28.525980330, 77.211001538),
                        new LatLng(28.527267892, 77.211224548),
                        new LatLng(28.527928791, 77.210613390),
                        new LatLng(28.528282693, 77.210085455),
                        new LatLng(28.528011099, 77.208363416),
                        new LatLng(28.527930244, 77.206937721),
                        new LatLng(28.527861686, 77.205432388),
                        new LatLng(28.527876168, 77.204708734),
                        new LatLng(28.526971148, 77.203232137),
                        new LatLng(28.526609352, 77.202745289),
                        new LatLng(28.526128162, 77.203089276),
                        new LatLng(28.525686235, 77.203551948),
                        new LatLng(28.525607148, 77.203098204),
                        new LatLng(28.525801434, 77.202196751),
                        new LatLng(28.525344435, 77.201919217),
                        new LatLng(28.524630638, 77.201947126),
                        new LatLng(28.523094820, 77.202709413)));
        Saket.setTag("Saket");
        stylePolygon(Saket);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.6289206, 77.2065322), 12.0f));
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                String type = "";

                if (polygon.getTag() != null) {
                    type = polygon.getTag().toString();
                }

                List<PatternItem> pattern = null;
                int strokeColor = COLOR_BLACK_ARGB;
                int fillColor = COLOR_WHITE_ARGB;

                switch (type) {
                    // If no type is given, allow the API to use the default.
                    case "CP":
                        // Apply a stroke pattern to render a dashed line, and define colors.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.6309923, 77.2163169), 14.0f));
                        Toast.makeText(PolygonMap.this, "Connaught Place", Toast.LENGTH_SHORT).show();
                        COLOR_RED_ARGB = 0xbcf44336;
                        COLOR_PURPLE_ARGB = 0xbc9C27B0;
                        COLOR_GREEN_ARGB = 0xbc4CAF50;
                        COLOR_BLUE_ARGB = 0xda1565C0;
                        stylePolygon(Saket);
                        stylePolygon(CP);
                        stylePolygon(NDRST);
                        stylePolygon(KG);
                        CP.setStrokeWidth(2);
                        break;
                    case "KG":
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.667810, 77.227481), 14.0f));
                        // Apply a stroke pattern to render a dashed line, and define colors.
                        Toast.makeText(PolygonMap.this, "Kashmere Gate", Toast.LENGTH_SHORT).show();
                        COLOR_RED_ARGB = 0xbcf44336;
                        COLOR_PURPLE_ARGB = 0xbc9C27B0;
                        COLOR_GREEN_ARGB = 0xda4CAF50;
                        COLOR_BLUE_ARGB = 0xbc1565C0;
                        stylePolygon(Saket);
                        stylePolygon(CP);
                        stylePolygon(NDRST);
                        stylePolygon(KG);
                        KG.setStrokeWidth(2);
                        break;
                    case "NDRST":
                        // Apply a stroke pattern to render a dashed line, and define colors.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.640315, 77.224085), 14.0f));
                        fillColor = COLOR_RED_ARGB;
                        Toast.makeText(PolygonMap.this, "New Delhi Railway Station", Toast.LENGTH_SHORT).show();
                        COLOR_RED_ARGB = 0xdaf44336;
                        COLOR_PURPLE_ARGB = 0xbc9C27B0;
                        COLOR_GREEN_ARGB = 0xbc4CAF50;
                        COLOR_BLUE_ARGB = 0xbc1565C0;
                        stylePolygon(Saket);
                        stylePolygon(CP);
                        stylePolygon(NDRST);
                        stylePolygon(KG);
                        NDRST.setStrokeWidth(2);
                        break;
                    case "Saket":
                        // Apply a stroke pattern to render a dashed line, and define colors.
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(28.524396, 77.207969), 14.0f));
                        fillColor = COLOR_PURPLE_ARGB;
                        Toast.makeText(PolygonMap.this, "Saket", Toast.LENGTH_SHORT).show();
                        COLOR_RED_ARGB = 0xbcf44336;
                        COLOR_PURPLE_ARGB = 0xda9C27B0;
                        COLOR_GREEN_ARGB = 0xbc4CAF50;
                        COLOR_BLUE_ARGB = 0xbc1565C0;
                        stylePolygon(Saket);
                        stylePolygon(CP);
                        stylePolygon(NDRST);
                        stylePolygon(KG);
                        Saket.setStrokeWidth(2);
                        break;
                }
            }

        });
    }


    private void stylePolygon(Polygon polygon) {
        String type = "";
        // Get the data object stored with the polygon.
        if (polygon.getTag() != null) {
            type = polygon.getTag().toString();
        }

        List<PatternItem> pattern = null;
        int strokeColor = COLOR_BLACK_ARGB;
        int fillColor = COLOR_WHITE_ARGB;

        switch (type) {
            // If no type is given, allow the API to use the default.
            case "CP":
                // Apply a stroke pattern to render a dashed line, and define colors.

                fillColor = COLOR_BLUE_ARGB;
                break;
            case "KG":
                // Apply a stroke pattern to render a dashed line, and define colors.

                fillColor = COLOR_GREEN_ARGB;
                break;
            case "NDRST":
                // Apply a stroke pattern to render a dashed line, and define colors.

                fillColor = COLOR_RED_ARGB;
                break;
            case "Saket":
                // Apply a stroke pattern to render a dashed line, and define colors.

                fillColor = COLOR_PURPLE_ARGB;
                break;
        }

        polygon.setStrokeWidth(POLYGON_STROKE_WIDTH_PX);
        polygon.setStrokeColor(strokeColor);
        polygon.setFillColor(fillColor);
    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    public void openDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_internet);
        Button cancel = dialog.findViewById(R.id.btn_cancel);
        Button connect = dialog.findViewById(R.id.btn_connect);

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(PolygonMap.this, CheckWifi.class);
                startActivity(i);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
