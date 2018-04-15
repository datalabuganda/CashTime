package com.example.eq62roket.cashtime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddGroupMembersExpendituresActivity extends AppCompatActivity {
    private static String TAG = "AddGroupMembersExpendituresActivity";
    EditText membersExpenditureCategory, membersExpenditureAmount, membersExpenditureDate,
        membersExpenditureNotes;
    TextView groupMemberUserName;
    Button membersExpenditureCancelBtn, membersExpenditureSaveBtn;

    Calendar myCalendar = Calendar.getInstance();
    Context context = this;
    String dateFormat = "dd/MM/yyyy";
    DatePickerDialog.OnDateSetListener date;
    SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);


    private String groupMemberLocalUniqueID = "";
    private ParseExpenditureHelper mParseHelper;

    public static String[] expenditureCategories = {"Rent", "Food", "Medical", "Transport", "Leisure", "Others",
            "Communication", "Entertainment", "Gift", "Clothes"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_members_expenditure);


        membersExpenditureCategory = (EditText)findViewById(R.id.membersExpenditureCategory);
        membersExpenditureAmount = (EditText)findViewById(R.id.membersExpenditureAmount);
        membersExpenditureDate = (EditText) findViewById(R.id.membersExpenditureDate);
        membersExpenditureNotes = (EditText)findViewById(R.id.membersExpenditureNotes);

        groupMemberUserName = (TextView)findViewById(R.id.membersExpenditureName);

        membersExpenditureSaveBtn = (Button)findViewById(R.id.memberExpenditureSaveButton);
        membersExpenditureCancelBtn = (Button)findViewById(R.id.memberExpenditureCancelButton);

        Calendar ca = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        membersExpenditureDate.setText(format.format(ca.getTime()));

        Intent intent = getIntent();
        String memberUserName = intent.getStringExtra("userName");
        groupMemberLocalUniqueID = intent.getStringExtra("groupMemberLocalUniqueID");

        Log.d(TAG, "username " + memberUserName);
        Log.d(TAG, "groupMemberLocalUniqueID " + groupMemberLocalUniqueID);

        groupMemberUserName.setText(memberUserName);


        membersExpenditureSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGroupMembersExpenditure();
            }
        });


        membersExpenditureCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedExpenditureActivity();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        membersExpenditureDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(context, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        groupMemberExpenditureCategory();
    }
    private void updateDate() {
        membersExpenditureDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void groupMemberExpenditureCategory(){
        ArrayAdapter<String> expenditureCategoryAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                expenditureCategories
        );

        MaterialBetterSpinner materialExpenditureCategorySpinner = (MaterialBetterSpinner) findViewById(R.id.membersExpenditureCategory);
        materialExpenditureCategorySpinner.setAdapter(expenditureCategoryAdapter);
    }

    private void saveGroupMembersExpenditure(){
        // add new group goal to db
        if ( !membersExpenditureCategory.getText().toString().equals("") &&
                !membersExpenditureAmount.getText().toString().equals("")){
            String groupMemberExpenditureCategory = membersExpenditureCategory.getText().toString();
            String groupMemberExpenditureAmount = membersExpenditureAmount.getText().toString();
            String groupMemberExpenditureNotes = membersExpenditureNotes.getText().toString();
            String groupMemberExpenditureDate = membersExpenditureDate.getText().toString();
            String groupMemberUsername = groupMemberUserName.getText().toString();
            String currentUser = ParseUser.getCurrentUser().getObjectId();


            GroupMemberExpenditure groupMemberExpenditure = new GroupMemberExpenditure();
            groupMemberExpenditure.setCategory(groupMemberExpenditureCategory);
            groupMemberExpenditure.setAmount(groupMemberExpenditureAmount);
            groupMemberExpenditure.setNotes(groupMemberExpenditureNotes);
            groupMemberExpenditure.setDate(groupMemberExpenditureDate);
            groupMemberExpenditure.setMemberUserName(groupMemberUsername);
            groupMemberExpenditure.setMemberParseId(groupMemberLocalUniqueID);
            groupMemberExpenditure.setUserId(currentUser);

            new ParseExpenditureHelper(this).saveGroupMembersExpenditureToParseDb(groupMemberExpenditure);
            startTabbedExpenditureActivity();

            Toast.makeText(AddGroupMembersExpendituresActivity.this, "Group Income " + groupMemberExpenditure.getCategory() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(AddGroupMembersExpendituresActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedExpenditureActivity(){
        Intent tabbedExpenditureIntent = new Intent(AddGroupMembersExpendituresActivity.this, TabbedExpenditureActivity.class);
        tabbedExpenditureIntent.putExtra("position", "1");
        startActivity(tabbedExpenditureIntent);
        finish();
    }
}
