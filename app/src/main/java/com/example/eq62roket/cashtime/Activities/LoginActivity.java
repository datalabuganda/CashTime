package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.R;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    EditText loginUsername, loginPassword;

    TextView registerView, login;
    CardView loginView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Parse.initialize(this);

        loginUsername = (EditText)findViewById(R.id.loginUsername);
        loginPassword = (EditText)findViewById(R.id.loginPassword);

        registerView = (TextView)findViewById(R.id.register);
        login = (TextView)findViewById(R.id.login);
        loginView = (CardView)findViewById(R.id.cardView);

        loginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = loginUsername.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null){
                            Toast.makeText(LoginActivity.this, "Welcome back", Toast.LENGTH_SHORT).show();
                            Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(loginIntent);
                        }else {
                            Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

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
