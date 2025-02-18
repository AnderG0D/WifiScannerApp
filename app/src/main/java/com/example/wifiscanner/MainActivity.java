package com.example.wifiscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager wifiManager;
    ListView listView_redes_wifi;
    Button button_buscar_wifi;
    List<ScanResult> results;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter adapter;
    int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Solicitar permisos en tiempo de ejecución
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        listView_redes_wifi = findViewById(R.id.listView_redes_wifi);
        button_buscar_wifi = findViewById(R.id.button_buscar_wifi);

        button_buscar_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanWifi();
            }
        });

        wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
        // Validar resultado anterior
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "Wifi deshabilitado", Toast.LENGTH_SHORT).show();
            //habilitar Wifi
            wifiManager.setWifiEnabled(true);
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        listView_redes_wifi.setAdapter(adapter);

        // Realizar escaneo de inmediato si se tienen permisos
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            scanWifi();
        }
    }

    private void scanWifi() {
        arrayList.clear();

        registerReceiver(wifiReceiver, new
                IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        wifiManager.startScan();
        Toast.makeText(getApplication(),
                "Escaneo de Wifi",
                Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            results = wifiManager.getScanResults();
            
            unregisterReceiver(this);

            for (ScanResult scanResult: results) {
                arrayList.add(scanResult.SSID + " - " +
                        scanResult.capabilities);

                adapter.notifyDataSetChanged();
            }
        };
    };
}