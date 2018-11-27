package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;
import java.net.InetAddress;

public class InternetConnectionStatusHelper {
    private static final String TAG = "InternetConnectionStatus";

        private Context mContext;

    public InternetConnectionStatusHelper(Context context) {
        this.mContext = context;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        if (isNetworkConnected()) {
            try {
                InetAddress ipAddr = InetAddress.getByName("www.google.com");
                Log.d("", "ipAddr: " + ipAddr);
                return !ipAddr.equals("");

            } catch (Exception e) {
                return false;
            }
        }else {

        }
        return false;
    }
}
