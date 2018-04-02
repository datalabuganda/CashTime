package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseIncomeHelper;
import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AddGroupMembersExpendituresActivity extends AppCompatActivity {
    private static String TAG = "AddGroupMembersExpendituresActivity";
    EditText membersExpenditureCategory, membersExpenditureAmount, membersExpenditureDate,
        membersExpenditureNotes;
    TextView groupMemberUserName;
    Button membersExpenditureCancelBtn, membersExpenditureSaveBtn;

    private String groupMemberParseId = "";
    private ParseExpenditureHelper mParseHelper;

    public static String[] expenditureCategories = {"Rent", "Food", "Medical", "Transport", "Leisure", "Others"};

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


        Intent intent = getIntent();
        String memberUserName = intent.getStringExtra("userName");
        groupMemberParseId = intent.getStringExtra("parseId");

        Log.d(TAG, "username " + memberUserName);
        Log.d(TAG, "parseId " + groupMemberParseId);

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

        groupMemberExpenditureCategory();
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


            GroupMemberExpenditure groupMemberExpenditure = new GroupMemberExpenditure();
            groupMemberExpenditure.setCategory(groupMemberExpenditureCategory);
            groupMemberExpenditure.setAmount(groupMemberExpenditureAmount);
            groupMemberExpenditure.setDueDate(groupMemberExpenditureNotes);
            groupMemberExpenditure.setNotes(groupMemberExpenditureDate);
            groupMemberExpenditure.setMemberUserName(groupMemberUsername);
            groupMemberExpenditure.setMemberParseId(groupMemberParseId);

            Log.d(TAG, "groupMemberUserName: " + groupMemberExpenditure.getMemberUserName());

            new ParseExpenditureHelper(this).saveGroupMembersExpenditureToParseDb(groupMemberExpenditure);
            startTabbedExpenditureActivity();

            Toast.makeText(AddGroupMembersExpendituresActivity.this, "Group Income " + groupMemberExpenditure.getCategory() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(AddGroupMembersExpendituresActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedExpenditureActivity(){
        Intent tabbedExpenditureIntent = new Intent(AddGroupMembersExpendituresActivity.this, TabbedExpenditureActivity.class);
        startActivity(tabbedExpenditureIntent);
        finish();
    }
}
