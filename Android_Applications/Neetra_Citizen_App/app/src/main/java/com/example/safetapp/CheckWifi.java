package com.example.safetapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class CheckWifi extends AppCompatActivity {
    final Context context = this;
    ListView list_wifi;
    String List[] = {"sanchi", "Sonia", "Puru", "Rajput"};



    WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_wifi);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        list_wifi = (ListView)findViewById(R.id.wifi_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.wifi_list_item, R.id.text, List);
        list_wifi.setAdapter(arrayAdapter);

        list_wifi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                wifiManager.setWifiEnabled(true);
                openDialog();
            }
        });
    }

    public void openDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_found_wifi);

        dialog.show();
    }
}