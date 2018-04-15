package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.R;

public class EditGroupExpenditureActivity extends AppCompatActivity {
    private static String TAG = "EditGroupExpenditureActivity";
    EditText mGroupExpenditureCategory, mGroupExpenditureAmount, mGroupExpenditureDate, mGroupExpenditureNotes;
    Button groupExpenditureDeleteBtn, groupExpenditureUpdateBtn;

    private String groupExpenditureParseId = "";
    private ParseExpenditureHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_expenditure);

        mParseHelper = new ParseExpenditureHelper(this);

        mGroupExpenditureCategory = (EditText)findViewById(R.id.editGroupExpenditureCategory);
        mGroupExpenditureAmount = (EditText)findViewById(R.id.editGroupExpenditureAmount);
        mGroupExpenditureDate = (EditText)findViewById(R.id.editGroupExpenditureDate);
        mGroupExpenditureNotes = (EditText)findViewById(R.id.editGroupExpenditureNotes);

        groupExpenditureDeleteBtn = (Button)findViewById(R.id.editGroupExpenditureDeleteButton);
        groupExpenditureUpdateBtn = (Button)findViewById(R.id.editGroupExpenditureUpdateButton);

        // get Intent data
        Intent intent = getIntent();
        final String groupExpenditureCategory = intent.getStringExtra("groupExpenditureCategory");
        String groupExpenditureAmount = intent.getStringExtra("groupExpenditureAmount");
        String groupExpenditureDate = intent.getStringExtra("groupExpenditureDate");
        String groupExpenditureNotes = intent.getStringExtra("groupExpenditureNotes");
        groupExpenditureParseId = intent.getStringExtra("groupExpenditureParseId");


        mGroupExpenditureCategory.setText(groupExpenditureCategory);
        mGroupExpenditureAmount.setText(groupExpenditureAmount);
        mGroupExpenditureDate.setText(groupExpenditureDate);
        mGroupExpenditureNotes.setText(groupExpenditureNotes);

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

                        GroupExpenditure groupExpenditureToDelete = new GroupExpenditure();
                        groupExpenditureToDelete.setParseId(groupExpenditureParseId);
                        mParseHelper.deleteGroupExpenditureFromParseDb(groupExpenditureToDelete);
                        startTabbedExpenditureActivity();
                        Toast.makeText(EditGroupExpenditureActivity.this, "Expenditure deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting Group Expenditure '" + groupExpenditureCategory + "' Can not be undone." + "Are You Sure You want to delete this expenditure?").setTitle("Delete Group Expenditure");


                // Create the AlertDialog
                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    private void updateGroupExpenditure(){
        if ( !mGroupExpenditureCategory.getText().toString().equals("") &&
                !mGroupExpenditureAmount.getText().toString().equals("")){
            String groupExpenditureCategory = mGroupExpenditureCategory.getText().toString();
            String groupExpenditureAmount = mGroupExpenditureAmount.getText().toString();
            String groupExpenditureDate = mGroupExpenditureDate.getText().toString();
            String groupExpenditureNotes = mGroupExpenditureNotes.getText().toString();

            GroupExpenditure groupExpenditure = new GroupExpenditure();
            groupExpenditure.setCategory(groupExpenditureCategory);
            groupExpenditure.setAmount(groupExpenditureAmount);
            groupExpenditure.setDate(groupExpenditureDate);
            groupExpenditure.setNotes(groupExpenditureNotes);
            if (!groupExpenditureParseId.equals("")){
                groupExpenditure.setParseId(groupExpenditureParseId);
            }
            mParseHelper.updateGroupExpenditureInParseDb(groupExpenditure);

            startTabbedExpenditureActivity();

            Toast.makeText(EditGroupExpenditureActivity.this, "Group Expenditure " + groupExpenditure.getCategory() + " Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(EditGroupExpenditureActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedExpenditureActivity(){
        Intent tabbedExpenditureIntent = new Intent(EditGroupExpenditureActivity.this, TabbedExpenditureActivity.class);
        tabbedExpenditureIntent.putExtra("position", "0");
        startActivity(tabbedExpenditureIntent);
        finish();
    }
}
