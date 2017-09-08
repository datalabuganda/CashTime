package com.example.eq62roket.CashTime.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "RegisterActivity";

    private EditText etHousehold, sexSpinner, etAge, spinnerEducationLevel, etPhoneNumber, spinnerCountryList;
    private Button btnProceed;

    SharedPreferences preferences;
    public static final String PREF_NAME = "registerPreference";


    private UserCrud mUserCrud;
    User user;

    public static String[] gender = {"Male", "Female"};
    public static String[] educationLevel = {"Primary", "OLevel", "Alevel", "Institution"};
    public static String[] countryList = new String[]{"Kenya", "South Sudan", "Tanzania", "Uganda"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    @SuppressLint("CutPasteId")
    private void init(){
        etHousehold = (EditText) findViewById(R.id.etHousehold);
        sexSpinner = (EditText) findViewById(R.id.sexSpinner);
        etAge = (EditText) findViewById(R.id.etAge);
        spinnerEducationLevel = (EditText) findViewById(R.id.spinnerEducationLevel);
        spinnerCountryList = (EditText) findViewById(R.id.tvCountry);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnProceed = (Button) findViewById(R.id.btnProceed);

        mUserCrud = new UserCrud(this);
        user = new User();

        // adapter for gender
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                gender
        );

        MaterialBetterSpinner materialSexSpinner = (MaterialBetterSpinner) findViewById(R.id.sexSpinner);
        materialSexSpinner.setAdapter(genderAdapter);

        // adapter for educationLevel
        ArrayAdapter<String> educationLevelAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                educationLevel
        );

        MaterialBetterSpinner materialEducationLevelSpinner = (MaterialBetterSpinner) findViewById(R.id.spinnerEducationLevel);
        materialEducationLevelSpinner.setAdapter(educationLevelAdapter);

        // adapter for countryList
        ArrayAdapter<String> countryListAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                countryList
        );

        MaterialBetterSpinner materialCountrySpinner = (MaterialBetterSpinner) findViewById(R.id.tvCountry);
        materialCountrySpinner.setAdapter(countryListAdapter);

        this.btnProceed.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProceed:
                Editable household = etHousehold.getText();
                Editable sex = sexSpinner.getText();
                Editable age = etAge.getText();
                Editable educationLevel = spinnerEducationLevel.getText();
                Editable nationality = spinnerCountryList.getText();
                Editable phoneNumber = etPhoneNumber.getText();
                if (!TextUtils.isEmpty(household) &&
                        !TextUtils.isEmpty(sex) &&
                        !TextUtils.isEmpty(educationLevel) &&
                        !TextUtils.isEmpty(nationality) &&
                        !TextUtils.isEmpty(phoneNumber)){

                    int user_household = Integer.parseInt(household.toString());
                    String user_sex = sex.toString();
                    int user_age = Integer.parseInt(age.toString());
                    String user_level_education = educationLevel.toString();
                    String user_nationality = nationality.toString();
                    String user_phoneNumber = phoneNumber.toString();
                    long user_points = 0L;

                    // set attributes of user object with use input
                    user.setHousehold(user_household);
                    user.setSex(user_sex);
                    user.setAge(user_age);
                    user.setEducationlevel(user_level_education);
                    user.setNationality(user_nationality);
                    user.setPhonenumber(user_phoneNumber);
                    user.setPoints(user_points);
                    user.setSyncStatus(0);


                    // add the user to database
                    mUserCrud.createUser(user);

                    Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();

                    // if user reaches here....set boolean of is registered to TRUE
                    preferences = getSharedPreferences(RegisterActivity.PREF_NAME, 0);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putBoolean("isRegistered", true);
                    editor.commit();


                    Intent intent =  new Intent(RegisterActivity.this, GoalActivity.class);
                    intent.putExtra("points", 0);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(this, R.string.empty_fields_message, Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
