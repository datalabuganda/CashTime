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
    public static String[] countryList = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",
            "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",
            "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",
            "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei", "Bulgaria",
            "Burkina Faso", "Burma (Myanmar)", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",
            "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island",
            "Cocos (Keeling) Islands", "Colombia", "Comoros", "Cook Islands", "Costa Rica",
            "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo",
            "Denmark", "Djibouti", "Dominica", "Dominican Republic",
            "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
            "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia",
            "Gabon", "Gambia", "Gaza Strip", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",
            "Greenland", "Grenada", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",
            "Haiti", "Holy See (Vatican City)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",
            "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica",
            "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait",
            "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",
            "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia",
            "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mayotte", "Mexico",
            "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco",
            "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",
            "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea",
            "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama",
            "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",
            "Portugal", "Puerto Rico", "Qatar", "Republic of the Congo", "Romania", "Russia", "Rwanda",
            "Saint Barthelemy", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin",
            "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino",
            "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",
            "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea",
            "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland",
            "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau",
            "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",
            "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "US Virgin Islands", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",
            "Wallis and Futuna", "West Bank", "Yemen", "Zambia", "Zimbabwe"};


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
