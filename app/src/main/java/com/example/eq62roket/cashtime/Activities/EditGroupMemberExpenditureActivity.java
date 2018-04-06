package com.example.eq62roket.cashtime.Activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.ParseHelper;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.example.eq62roket.cashtime.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditGroupMemberExpenditureActivity extends AppCompatActivity {

    private static String TAG = "EditGroupExpenditureActivity";
    EditText mGroupExpenditureCategory, mGroupExpenditureAmount, mGroupExpenditureDate, mGroupExpenditureNotes;
    Button groupExpenditureDeleteBtn, groupExpenditureUpdateBtn;

    private String groupMemberExpenditureParseId = "";
    private ParseExpenditureHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_member_expenditure);

        mParseHelper = new ParseExpenditureHelper(this);

        mGroupExpenditureCategory = (EditText)findViewById(R.id.editGroupMemberExpenditureCategory);
        mGroupExpenditureAmount = (EditText)findViewById(R.id.editGroupMemberExpenditureAmount);
        mGroupExpenditureDate = (EditText)findViewById(R.id.editGroupMemberExpenditureDate);
        mGroupExpenditureNotes = (EditText)findViewById(R.id.editGroupMemberExpenditureNotes);

        groupExpenditureDeleteBtn = (Button)findViewById(R.id.editGroupMemberExpenditureDeleteButton);
        groupExpenditureUpdateBtn = (Button)findViewById(R.id.editGroupMemberExpenditureUpdateButton);

        // get Intent data
        Intent intent = getIntent();
        final String groupMemberExpenditureCategory = intent.getStringExtra("groupMembersExpenditureCategory");
        String groupMemberExpenditureAmount = intent.getStringExtra("groupMembersExpenditureAmount");
        String groupMemberExpenditureDate = intent.getStringExtra("groupMembersExpenditureDate");
        String groupMemberExpenditureNotes = intent.getStringExtra("groupMembersExpenditureNotes");
        groupMemberExpenditureParseId = intent.getStringExtra("groupMembersExpenditureParseId");


        mGroupExpenditureCategory.setText(groupMemberExpenditureCategory);
        mGroupExpenditureAmount.setText(groupMemberExpenditureAmount);
        mGroupExpenditureDate.setText(groupMemberExpenditureDate);
        mGroupExpenditureNotes.setText(groupMemberExpenditureNotes);

        groupExpenditureUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGroupExpenditure();
            }
        });

        groupExpenditureDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start a dialog fragment
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                // Add the buttons
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GroupMemberExpenditure groupMemberExpenditureToDelete = new GroupMemberExpenditure();
                        groupMemberExpenditureToDelete.setParseId(groupMemberExpenditureParseId);
                        mParseHelper.deleteGroupMembersExpenditureFromParseDb(groupMemberExpenditureToDelete);
                        startTabbedExpenditureActivity();
                        Toast.makeText(EditGroupMemberExpenditureActivity.this, "Expenditure deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting Group Expenditure '" + groupMemberExpenditureCategory + "' Can not be undone." + "Are You Sure You want to delete this expenditure?").setTitle("Delete Group Expenditure");


                // Create the AlertDialog
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }

    private void updateGroupExpenditure(){
        if ( !mGroupExpenditureCategory.getText().toString().equals("") &&
                !mGroupExpenditureAmount.getText().toString().equals("")){
            String groupMemberExpenditureCategory = mGroupExpenditureCategory.getText().toString();
            String groupMemberExpenditureAmount = mGroupExpenditureAmount.getText().toString();
            String groupMemberExpenditureDate = mGroupExpenditureDate.getText().toString();
            String groupMemberExpenditureNotes = mGroupExpenditureNotes.getText().toString();

            GroupMemberExpenditure groupMemberExpenditure = new GroupMemberExpenditure();
            groupMemberExpenditure.setCategory(groupMemberExpenditureCategory);
            groupMemberExpenditure.setAmount(groupMemberExpenditureAmount);
            groupMemberExpenditure.setDueDate(groupMemberExpenditureDate);
            groupMemberExpenditure.setNotes(groupMemberExpenditureNotes);
            if (!groupMemberExpenditureParseId.equals("")){
                groupMemberExpenditure.setParseId(groupMemberExpenditureParseId);
            }
            mParseHelper.updateGroupMembersExpenditureInParseDb(groupMemberExpenditure);

            startTabbedExpenditureActivity();

            Toast.makeText(EditGroupMemberExpenditureActivity.this, "Group Expenditure " + groupMemberExpenditure.getCategory() + " Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(EditGroupMemberExpenditureActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedExpenditureActivity(){
        Intent tabbedExpenditureIntent = new Intent(EditGroupMemberExpenditureActivity.this, TabbedExpenditureActivity.class);
        startActivity(tabbedExpenditureIntent);
        finish();
    }
}