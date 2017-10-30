package com.example.krishna.mylocation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;

/**
 * Created by HP on 08-01-2017.
 */

public class InternetGpsConnectionChecker {

    private static InternetGpsConnectionChecker instance = null;
    private Context context;

    private InternetGpsConnectionChecker(){}

    public static InternetGpsConnectionChecker getInstance(Context context) {
        if(instance == null) {
            instance = new InternetGpsConnectionChecker();
            instance.context = context;
        }
        return instance;
    }

    public static InternetGpsConnectionChecker getInstance() {
        return instance;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMan.getActiveNetworkInfo() != null && conMan.getActiveNetworkInfo().isConnected();
    }

    public static boolean isGpsEnabled(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            return true;
        }
        else if (manager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)){
            return true;
        }
        else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            return true;
        }
        else
        {
            return false;
        }
    }
    public static boolean checkLocationPermission(Context context)
    {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    public static void requestLocationPermission(Context context)
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            //Toast.makeText(context, "GPS permission allows us to access location data. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        }
    }

    public static boolean checkSmsPermission(Context context){
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestSmsPermission(Context context){
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
    }

    public static class NetworkUtil{
        public static int TYPE_WIFI = 1;
        public static int TYPE_MOBILE = 2;
        public static int TYPE_NOT_CONNECTED = 0;


        public static int getConnectivityStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
            return TYPE_NOT_CONNECTED;
        }

        public static String getConnectivityStatusString(Context context) {
            int conn = NetworkUtil.getConnectivityStatus(context);
            String status = null;
            if (conn == NetworkUtil.TYPE_WIFI) {
                status = "Wifi enabled";
            } else if (conn == NetworkUtil.TYPE_MOBILE) {
                status = "Mobile data enabled";
            } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
                status = "No Internet Connection!";
            }
            return status;
        }
    }
}
