package com.example.finalapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;

import android.os.Bundle;

public class MainActivity3 extends AppCompatActivity {

    private Button buttonOpenActivityCal;

    private File file, file1;
    private OutputStreamWriter outStream, outStream1;

    // private final BroadcastReceiver FoundReceiver = null;
    private ArrayAdapter<String> btArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        this.buttonOpenActivityCal = (Button) this.findViewById(R.id.button_openActivityCal);

        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, permissions, 1);

        this.buttonOpenActivityCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity3.this, MainActivity4.class));

            }
        });


        final BluetoothAdapter myBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        final Button scanb = (Button) findViewById(R.id.button_scan);
        final ListView Deviceslist = (ListView) findViewById(R.id.listView);
        btArrayAdapter = new ArrayAdapter<String>(MainActivity3.this, android.R.layout.simple_list_item_1);
        Deviceslist.setAdapter(btArrayAdapter);

        //Turn on Bluetooth
        if (myBlueToothAdapter == null)
            Toast.makeText(MainActivity3.this, "Your device doesnot support Bluetooth", Toast.LENGTH_LONG).show();
        else if (!myBlueToothAdapter.isEnabled()) {
            Intent BtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(BtIntent, 0);
            Toast.makeText(MainActivity3.this, "Turning on Bluetooth", Toast.LENGTH_LONG).show();
        }
        //scan
        scanb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btArrayAdapter.clear();

                myBlueToothAdapter.startDiscovery();
                Toast.makeText(MainActivity3.this, "Scanning Devices", Toast.LENGTH_LONG).show();

            }
        });

        registerReceiver(FoundReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));


    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        unregisterReceiver(FoundReceiver);
    }


    private final BroadcastReceiver FoundReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "BluetoothTest.txt");
                outStream = new OutputStreamWriter(new FileOutputStream(file));
            } catch (IOException e) {
                System.out.print("Error creating file!");
            }


            // TODO Auto-generated method stub

            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                int rss = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MAX_VALUE);


                btArrayAdapter.add(device.getName() + "\n" + device.getAddress() + "\n" + rss);
                try {
                    outStream.write(device.getName() + "\n" + device.getAddress() + "\n" + rss);
                    outStream.flush();
                }catch(IOException e){}
                btArrayAdapter.notifyDataSetChanged();


            }

        }};

}
