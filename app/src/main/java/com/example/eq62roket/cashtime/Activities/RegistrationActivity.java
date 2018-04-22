package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseRegistrationHelper;
import com.example.eq62roket.cashtime.Helper.ProgressDialogHelper;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulRegistrationListener;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    EditText username, userPhone, userHousehold, userBusiness, userGender, userEducationLevel, userNationality, userLocation, userPassword;
    CardView userRegister;

    private ProgressDialogHelper mProgressDialogHelper;

    public static String[] nationalityCategories = {"Ugandan", "Kenyan", "Rwandan", "Congolese", "Tanzanian",
            "South Sudanese"};

    public static String[] genderCategories = {"Male", "Female"};

    public static String[] levelOfEducationCategories = {"Primary", "O Level", "A Level", "University",
            "Institution"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mProgressDialogHelper = new ProgressDialogHelper(RegistrationActivity.this);
        mProgressDialogHelper.setProgreDialogTitle("Registration in Progress ...");
        mProgressDialogHelper.setProgressDialogMessage("Please Wait While We Register You");

        username = (EditText)findViewById(R.id.username);
        userPhone = (EditText)findViewById(R.id.userPhoneNumber);
        userHousehold = (EditText)findViewById(R.id.userHousehold);
        userBusiness = (EditText)findViewById(R.id.userBusiness);
        userGender = (EditText)findViewById(R.id.userGender);
        userEducationLevel = (EditText)findViewById(R.id.userEducationLevel);
        userNationality = (EditText)findViewById(R.id.userNationality);
        userLocation = (EditText)findViewById(R.id.userLocation);
        userPassword = (EditText)findViewById(R.id.userPassword);
        userRegister = (CardView) findViewById(R.id.userRegister);

        userRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!username.getText().toString().equals("") &&
                        !userPhone.getText().toString().equals("") &&
                        !userHousehold.getText().toString().equals("") &&
                        !userBusiness.getText().toString().equals("") &&
                        !userGender.getText().toString().equals("") &&
                        !userEducationLevel.getText().toString().equals("") &&
                        !userNationality.getText().toString().equals("") &&
                        !userLocation.getText().toString().equals("") &&
                        !userPassword.getText().toString().equals("")){

                    mProgressDialogHelper.showProgressDialog();

                    User newUser = new User();
                    newUser.setUserName(username.getText().toString());
                    newUser.setPhoneNumber(userPhone.getText().toString());
                    newUser.setHousehold(userHousehold.getText().toString());
                    newUser.setBusiness(userBusiness.getText().toString());
                    newUser.setGender(userGender.getText().toString());
                    newUser.setEducationLevel(userEducationLevel.getText().toString());
                    newUser.setNationality(userNationality.getText().toString());
                    newUser.setLocation(userLocation.getText().toString());
                    newUser.setPassword(userPassword.getText().toString());
                    newUser.setIsLeader(false);
                    newUser.setPoints(3);

                    new ParseRegistrationHelper(RegistrationActivity.this).saveRegisteredUserToParseDb(newUser, new OnSuccessfulRegistrationListener() {
                        @Override
                        public void onResponse(String success) {
                            mProgressDialogHelper.dismissProgressDialog();
                            Intent homeIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(homeIntent);
                            finish();
                            Toast.makeText(RegistrationActivity.this, "You have registered", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String error) {
                            mProgressDialogHelper.dismissProgressDialog();
                            Toast.makeText(RegistrationActivity.this, "Registration Failed " + error, Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(RegistrationActivity.this, "All Fields Must Be Filled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        genderCategory();
        nationalityCategory();
        levelOfEducationCategory();

    }

    public void nationalityCategory(){
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                nationalityCategories
        );

        MaterialBetterSpinner materialNationalitySpinner = (MaterialBetterSpinner) findViewById(R.id.userNationality);
        materialNationalitySpinner.setAdapter(nationalityAdapter);
    }

    public void genderCategory(){
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                genderCategories
        );

        MaterialBetterSpinner materialGenderSpinner = (MaterialBetterSpinner) findViewById(R.id.userGender);
        materialGenderSpinner.setAdapter(genderAdapter);
    }

    public void levelOfEducationCategory(){
        ArrayAdapter<String> levelOfEducationAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                levelOfEducationCategories
        );

        MaterialBetterSpinner materialLevelOfEducationSpinner = (MaterialBetterSpinner) findViewById(R.id.userEducationLevel);
        materialLevelOfEducationSpinner.setAdapter(levelOfEducationAdapter);
    }

}
