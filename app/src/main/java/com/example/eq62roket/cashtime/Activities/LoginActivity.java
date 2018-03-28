package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.CashTimeSharedPreferences;
import com.example.eq62roket.cashtime.Helper.ParseRegistrationHelper;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulLoginListener;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText loginUsername, loginPassword;

    TextView registerView, login;
    CardView loginView;

    private CashTimeSharedPreferences mCashTimeSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mCashTimeSharedPreferences = new CashTimeSharedPreferences(LoginActivity.this);

        loginUsername = (EditText)findViewById(R.id.loginUsername);
        loginPassword = (EditText)findViewById(R.id.loginPassword);

        registerView = (TextView)findViewById(R.id.register);
        login = (TextView)findViewById(R.id.login);
        loginView = (CardView)findViewById(R.id.cardView);

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!loginUsername.getText().toString().trim().equals("") &&
                        !loginPassword.getText().toString().trim().equals("")){
                    String username = loginUsername.getText().toString().trim();
                    String password = loginPassword.getText().toString().trim();

                    User registeredUser = new User();
                    registeredUser.setUserName(username);
                    registeredUser.setPassword(password);

                    new ParseRegistrationHelper(LoginActivity.this).loginUserToParseDb(registeredUser, new OnSuccessfulLoginListener() {
                        @Override
                        public void onResponse(String success) {

                            Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(loginIntent);
                            Toast.makeText(LoginActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(LoginActivity.this, "Failed to login " + error, Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(LoginActivity.this, "All fields must be filled to login", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);

            }
        });
    }
}
