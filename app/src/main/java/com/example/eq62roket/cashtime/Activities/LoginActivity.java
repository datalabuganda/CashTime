package com.example.eq62roket.cashtime.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.InternetConnectionStatusHelper;
import com.example.eq62roket.cashtime.Helper.ParseRegistrationHelper;
import com.example.eq62roket.cashtime.Helper.ParseUserHelper;
import com.example.eq62roket.cashtime.Helper.ProgressDialogHelper;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulLoginListener;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText loginUsername, loginPassword;

    TextView register;
    CardView login;

    private ProgressDialogHelper mProgressDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressDialogHelper = new ProgressDialogHelper(LoginActivity.this);
        mProgressDialogHelper.setProgreDialogTitle("Logging in ...");
        mProgressDialogHelper.setProgressDialogMessage("Please Wait While We Log You in");

        loginUsername = (EditText)findViewById(R.id.loginUsername);
        loginPassword = (EditText)findViewById(R.id.loginPassword);

        register = (TextView)findViewById(R.id.registerUser);
        login = (CardView)findViewById(R.id.loginUser);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!loginUsername.getText().toString().trim().equals("") &&
                        !loginPassword.getText().toString().trim().equals("")){
                    String username = loginUsername.getText().toString().trim();
                    String password = loginPassword.getText().toString().trim();

                    mProgressDialogHelper.showProgressDialog();
                    timerDelayRemoveDialog(10000,mProgressDialogHelper);

                    User registeredUser = new User();
                    registeredUser.setUserName(username);
                    registeredUser.setPassword(password);

                    new ParseRegistrationHelper(LoginActivity.this).loginUserToParseDb(registeredUser, new OnSuccessfulLoginListener() {
                        @Override
                        public void onResponse(String success) {
                            mProgressDialogHelper.dismissProgressDialog();
                            Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(loginIntent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(String error) {
                            mProgressDialogHelper.dismissProgressDialog();
                            Toast.makeText(LoginActivity.this, "Failed to login " + error, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, "All fields must be filled to login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });

    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean hasActiveInternetConnection(Context context) {
        if (isNetworkAvailable(context)) {
            try {
                HttpURLConnection urlc = (HttpURLConnection)
                        (new URL("http://clients3.google.com/generate_204")
                                .openConnection());
                urlc.setRequestProperty("User-Agent", "Android");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 204 &&
                        urlc.getContentLength() == 0);
            } catch (IOException e) {
                Log.e(TAG, "Error checking internet connection", e);
            }
        }else {
            Log.d(TAG, "No network available!");
        }
        return false;
    }

    public void timerDelayRemoveDialog(long time, final ProgressDialogHelper d){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                d.dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "Failed to sign in. Check your internet connection", Toast.LENGTH_LONG).show();
            }
        }, time);
    }
}
