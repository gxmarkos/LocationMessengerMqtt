package com.example.gx.locationmessengermqtt;


import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class MainActivity extends AppCompatActivity  {

    TextView latlon;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private Double lat;
    private Double lon;
    public static final String MYFILTER="com.my.broadcast.RECEIVER";
    public static final String MYFILTER2="com.my.broadcast.RECEIVER";
    public static final String MSG ="_message";
    public static final String MSG2 = "_message";
    private static final String TAG = "com.example.gx.locationmessengermqtt";

    //registerReceiver(broadcastReceiver, new IntentFilter(MyService.));



    private BroadcastReceiver myReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        // Bundle bundle = intent.getExtras();
           // String iValue = bundle.getString(MSG);
            latlon.setText(intent.getExtras().getString(MSG));




        }
    };







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latlon = (TextView) findViewById(R.id.latlon);

       // setLocationRequest();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MYFILTER);
        registerReceiver(myReceiver,intentFilter);






    }

    public void startMethod(View v){

        Intent intent = new Intent(this,MyService.class);
        startService(intent);


    }


    public void stopMethod(View v){
        Intent i = new Intent (this,MyService.class);
        stopService(i);




    }



    /*
    public void setLocationRequest(){

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        locationRequest = new LocationRequest();
        locationRequest.setInterval(5 * 1000);
        locationRequest.setFastestInterval(15 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }*/







/*
    @Override
    protected void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient,this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(googleApiClient.isConnected()){

            requestLocationUpdates();


        }
    }*/
}
