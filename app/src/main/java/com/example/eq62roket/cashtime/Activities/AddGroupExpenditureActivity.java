package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AddGroupExpenditureActivity extends AppCompatActivity {
    private static String TAG = "AddGroupExpenditureActivity";
    private EditText  mGroupExpenditureCategory, mGroupExpenditureAmount, mGroupExpenditureNotes;
    private Button groupExpenditureCancelBtn, groupExpenditureSaveBtn;
    private TextView mGroupName;

    public static String[] expenditureCategories = {"Rent", "Food", "Medical", "Transport", "Leisure", "Others", "Communication",
            "Entertainment", "Gift", "Clothes"};

    private String groupLocalUniqueID = "";
    private String categoryId = "";
    private ParseExpenditureHelper mParseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_expenditure);
        mGroupExpenditureCategory = (EditText) findViewById(R.id.groupExpenditureCategory);
        mGroupExpenditureAmount = (EditText)findViewById(R.id.groupExpenditureAmount);
//        mGroupExpenditureDate = (EditText)findViewById(R.id.groupExpenditureDate);
        mGroupExpenditureNotes = (EditText)findViewById(R.id.groupExpenditureNotes);
        mGroupName = (TextView) findViewById(R.id.groupName);
        groupExpenditureSaveBtn = (Button)findViewById(R.id.groupExpenditureSaveBtn);
        groupExpenditureCancelBtn = (Button)findViewById(R.id.groupExpenditureCancelBtn);

        Intent intent = getIntent();
        String groupName = intent.getStringExtra("groupName");
        groupLocalUniqueID = intent.getStringExtra("groupLocalUniqueID");
        mGroupName.setText(groupName);

        groupExpenditureCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedExpenditureActivity();
            }
        });

        groupExpenditureSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGroupExpenditure();
            }
        });


        groupExpenditureCategory();
    }

    public void groupExpenditureCategory(){
        ArrayAdapter<String> expenditureAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                expenditureCategories
        );

        MaterialBetterSpinner materialExpenditureSpinner = (MaterialBetterSpinner) findViewById(R.id.groupExpenditureCategory);
        materialExpenditureSpinner.setAdapter(expenditureAdapter);
    }

    private void saveGroupExpenditure(){
        if ( !mGroupExpenditureCategory.getText().toString().equals("") &&
                !mGroupExpenditureAmount.getText().toString().equals("")){
            String groupExpenditureCategory = mGroupExpenditureCategory.getText().toString();
            String groupExpenditureAmount = mGroupExpenditureAmount.getText().toString();
            String groupExpenditureNotes = mGroupExpenditureNotes.getText().toString();
            String groupName = mGroupName.getText().toString();
            String currentUserId = ParseUser.getCurrentUser().getObjectId();

            GroupExpenditure groupExpenditures = new GroupExpenditure();
            groupExpenditures.setCategory(groupExpenditureCategory);
            groupExpenditures.setNotes(groupExpenditureNotes);
            groupExpenditures.setDate(new PeriodHelper().getDateToday());
            groupExpenditures.setAmount(groupExpenditureAmount);
            groupExpenditures.setGroupLocalUniqueID(groupLocalUniqueID);
            groupExpenditures.setGroupName(groupName);
            groupExpenditures.setUserId(currentUserId);

            new ParseExpenditureHelper(this).saveGroupExpenditureToParseDb(groupExpenditures);
            startTabbedExpenditureActivity();

            Toast.makeText(AddGroupExpenditureActivity.this, "Group Expenditure " + groupExpenditures.getAmount() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(AddGroupExpenditureActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedExpenditureActivity(){
        Intent tabbedExpenditureIntent = new Intent(AddGroupExpenditureActivity.this, TabbedExpenditureActivity.class);
        tabbedExpenditureIntent.putExtra("position", "0");
        startActivity(tabbedExpenditureIntent);
        finish();
    }
}
