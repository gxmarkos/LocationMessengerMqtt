package com.example.gx.locationmessengermqtt;

import android.app.IntentService;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.vision.text.Text;

/**
 * Created by GX on 10/8/2016.
 */
public class MyService extends IntentService implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private Double lat;
    private Double lon;
    private String LATITUDE;
    private String LONGITUDE;
    private LocationListener locationListener;






    private static final String TAG = "com.example.gx.locationmessengermqtt";

    public MyService() {
        super("mqtt_service");



    }



    private class LocationListener implements com.google.android.gms.location.LocationListener{
        @Override
        public void onLocationChanged(Location location) {
            if (location!=null){


                Double LONGITUDE = location.getLongitude();
                Double LATITUDE = location.getLatitude();





                Intent intent = new Intent ();





                intent.putExtra(MainActivity.MSG,"longitude: "+LONGITUDE.toString()+"\n"+"latitude: "+LATITUDE.toString());

                intent.setAction(MainActivity.MYFILTER);

                //intent.putExtra(MainActivity.MSG,String.valueOf(LATITUDE));
              //  Log.i(TAG,"Lat"+location.getLatitude());
                //Log.i(TAG,"Lon"+location.getLongitude());
                sendBroadcast(intent);



            }
        }

    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


    Log.e(TAG, "onStartCommand");
    //super.onStartCommand(intent, flags, startId);
    boolean stopService = false;
    if (intent != null)
    stopService = intent.getBooleanExtra("stopservice", false);

    System.out.println("stopservice " + stopService);

    locationListener = new LocationListener();

    if (stopService)
    stopLocationUpdates();
    else {
        if (!googleApiClient.isConnected())
            googleApiClient.connect();



        if (googleApiClient.isConnected()) {
            Toast.makeText(this, "Sindethike to google", Toast.LENGTH_LONG).show();
        }
    }

    return START_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }





    @Override
    public void onCreate() {
        super.onCreate();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        Toast.makeText(this,"Service is created",Toast.LENGTH_LONG).show();





    }
    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, locationListener);

        if (googleApiClient.isConnected())
            googleApiClient.disconnect();
    }
    @Override
    protected void onHandleIntent(Intent intent) {



        Log.i(TAG,"service Started");




    }


    @Override
    public void onConnectionFailed( ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5 * 1000);
        locationRequest.setFastestInterval(15 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        requestLocationUpdates();
    }

    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }


    public void setLocationRequest(){
/*
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();*/

/*
        locationRequest = new LocationRequest();
        locationRequest.setInterval(5 * 1000);
        locationRequest.setFastestInterval(15 * 1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);*/


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (googleApiClient.isConnected())
            googleApiClient.disconnect();



        Toast.makeText(this,"Service is stoped",Toast.LENGTH_LONG).show();
    }
}





