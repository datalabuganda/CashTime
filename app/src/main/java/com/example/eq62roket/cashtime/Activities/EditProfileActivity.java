package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Helper.ParseUserHelper;
import com.example.eq62roket.cashtime.Helper.ProgressDialogHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.Models.User;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

public class EditProfileActivity extends AppCompatActivity {

    private static final String TAG = "EditProfileActivity";

    private String userUniqueID;

    private EditText username, userPhone, userHousehold, userBusiness, userLocation,
            userEducationLevel, userNationality, userGender;

    private CardView userEdit;

    private ProgressDialogHelper mProgressDialogHelper;
    private ParseUserHelper mParseUserHelper;

    public static String[] nationalityCategories = {"Ugandan", "Kenyan", "Rwandan", "Congolese", "Tanzanian",
            "South Sudanese"};

    public static String[] genderCategories = {"Male", "Female"};

    public static String[] levelOfEducationCategories = {"Primary", "O Level", "A Level", "University",
            "Institution"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mParseUserHelper = new ParseUserHelper(EditProfileActivity.this);

        mProgressDialogHelper = new ProgressDialogHelper(EditProfileActivity.this);
        mProgressDialogHelper.setProgreDialogTitle("Updating your profile ...");
        mProgressDialogHelper.setProgressDialogMessage("Please Wait as your profile is being update");

        username = findViewById(R.id.editUsername);
        userPhone = findViewById(R.id.editUserPhoneNumber);
        userHousehold = findViewById(R.id.editUserHousehold);
        userBusiness = findViewById(R.id.editUserBusiness);
        userGender = findViewById(R.id.editUserGender);
        userEducationLevel = findViewById(R.id.editUserEducationLevel);
        userNationality = findViewById(R.id.editUserNationality);
        userLocation = findViewById(R.id.editUserLocation);
        userEdit = findViewById(R.id.editUserBtn);

        Intent editProfileIntent = getIntent();
        userUniqueID = editProfileIntent.getStringExtra("objectId");
        final String location = editProfileIntent.getStringExtra("userLocation");
        final String name = editProfileIntent.getStringExtra("username");
        final String phone = editProfileIntent.getStringExtra("userPhone");
        final String business = editProfileIntent.getStringExtra("userBusiness");
        final String nationality = editProfileIntent.getStringExtra("userNationality");
        final String household = editProfileIntent.getStringExtra("userHousehold");
        final String gender = editProfileIntent.getStringExtra("userGender");
        final String education = editProfileIntent.getStringExtra("userEducationLevel");

        username.setText(name);
        userPhone.setText(phone);
        userHousehold.setText(household);
        userBusiness.setText(business);
        userGender.setText(gender);
        userEducationLevel.setText(education);
        userNationality.setText(nationality);
        userLocation.setText(location);


        userEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!username.getText().toString().equals("") &&
                    !userPhone.getText().toString().equals("") &&
                    !userHousehold.getText().toString().equals("") &&
                    !userBusiness.getText().toString().equals("") &&
                    !userGender.getText().toString().equals("") &&
                    !userEducationLevel.getText().toString().equals("") &&
                    !userNationality.getText().toString().equals("") &&
                    !userLocation.getText().toString().equals("")){

                    User userToUpdate = new User();
                    userToUpdate.setUserName(username.getText().toString().trim());
                    userToUpdate.setPhoneNumber(userPhone.getText().toString().trim());
                    userToUpdate.setHousehold(userHousehold.getText().toString().trim());
                    userToUpdate.setBusiness(userBusiness.getText().toString().trim());
                    userToUpdate.setGender(userGender.getText().toString().trim());
                    userToUpdate.setEducationLevel(userEducationLevel.getText().toString().trim());
                    userToUpdate.setNationality(userNationality.getText().toString().trim());
                    userToUpdate.setLocation(userLocation.getText().toString().trim());
                    userToUpdate.setParseId(userUniqueID);
                    mParseUserHelper.updateUserInParseDb(userToUpdate);

                    startUserActivity();
                    Toast.makeText(
                            EditProfileActivity.this,
                            "Your profile was Successfully Updated",
                            Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(EditProfileActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        nationalityCategory();
        genderCategory();
        levelOfEducationCategory();
    }

    public void startUserActivity(){
        Intent intent = new Intent(EditProfileActivity.this, GroupsActivity.class);
        startActivity(intent);
        finish();
    }

    public void nationalityCategory(){
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                nationalityCategories
        );

        MaterialBetterSpinner materialNationalitySpinner = findViewById(R.id.editUserNationality);
        materialNationalitySpinner.setAdapter(nationalityAdapter);
    }

    public void genderCategory(){
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                genderCategories
        );

        MaterialBetterSpinner materialGenderSpinner = findViewById(R.id.editUserGender);
        materialGenderSpinner.setAdapter(genderAdapter);
    }

    public void levelOfEducationCategory(){
        ArrayAdapter<String> levelOfEducationAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                levelOfEducationCategories
        );

        MaterialBetterSpinner materialLevelOfEducationSpinner =  findViewById(R.id.editUserEducationLevel);
        materialLevelOfEducationSpinner.setAdapter(levelOfEducationAdapter);
    }
}
