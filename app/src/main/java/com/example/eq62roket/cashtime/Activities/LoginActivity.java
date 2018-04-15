package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseRegistrationHelper;
import com.example.eq62roket.cashtime.Helper.ProgressDialogHelper;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulLoginListener;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText loginUsername, loginPassword;

    TextView register;
    CardView login;

//    private ProgressDialog mProgressDialog;
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
}
