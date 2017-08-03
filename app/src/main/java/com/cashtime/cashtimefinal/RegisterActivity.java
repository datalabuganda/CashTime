package com.cashtime.cashtimefinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cashtime.helper.PersonCrud;
import com.cashtime.models.Person;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "RegisterActivity";

    private EditText etHousehold, sexSpinner, etAge, spinnerEducationLevel, etPhoneNumber;
    AutoCompleteTextView tvCountry;
    private Button btnProceed;

    SharedPreferences preferences;
    public static final String PREF_NAME = "registerPreference";


    private PersonCrud mPersonCrud;

    public static String[] gender = {"Male", "Female"};
    public static String[] educationLevel = {"Primary", "O'Level", "A'level", "Institution"};
    public static String[] countryList = new String[]{"Kenya", "South Sudan", "Tanzania", "Uganda"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
    }

    private void init(){
        etHousehold = (EditText) findViewById(R.id.etHousehold);
        sexSpinner = (EditText) findViewById(R.id.sexSpinner);
        etAge = (EditText) findViewById(R.id.etAge);
        spinnerEducationLevel = (EditText) findViewById(R.id.spinnerEducationLevel);
        tvCountry = (AutoCompleteTextView) findViewById(R.id.tvCountry);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        btnProceed = (Button) findViewById(R.id.btnProceed);
        mPersonCrud = new PersonCrud(this);

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

        tvCountry.setAdapter(countryListAdapter);
        tvCountry.setThreshold(1);

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
                Editable nationality = tvCountry.getText();
                Editable phoneNumber = etPhoneNumber.getText();
                if (!TextUtils.isEmpty(household) &&
                        !TextUtils.isEmpty(sex) &&
                        !TextUtils.isEmpty(educationLevel) &&
                        !TextUtils.isEmpty(nationality) &&
                        !TextUtils.isEmpty(phoneNumber)){

                    // add the person to database
                    Person createdPerson = mPersonCrud.createPerson(
                            Integer.parseInt(household.toString()),
                            sex.toString(),
                            Integer.parseInt(age.toString()),
                            educationLevel.toString(),
                            nationality.toString(),
                            phoneNumber.toString(),
                            0L);

                    Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();

                    // if user reaches here....set boolean of is registered to TRUE
                    preferences = getSharedPreferences(RegisterActivity.PREF_NAME, 0);
                    SharedPreferences.Editor editor = preferences.edit();

                    editor.putBoolean("isRegistered", true);
                    editor.commit();

                    Log.d(TAG, "added Person : " + createdPerson.getId());

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
        mPersonCrud.close();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
