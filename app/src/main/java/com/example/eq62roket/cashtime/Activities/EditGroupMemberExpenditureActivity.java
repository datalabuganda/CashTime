package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Models.GroupMemberExpenditure;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class EditGroupMemberExpenditureActivity extends AppCompatActivity {

    private static String TAG = "EditGroupExpenditureActivity";
    private EditText mGroupExpenditureAmount, mGroupExpenditureNotes;
    private Button groupExpenditureDeleteBtn, groupExpenditureUpdateBtn;
    private MaterialBetterSpinner materialExpenditureCategorySpinner;
    private String memberExpenditureLocalUniqueID = "";
    private String selectedExpenditureCategory;
    private ParseExpenditureHelper mParseHelper;
    private TextView memberName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_member_expenditure);

        mParseHelper = new ParseExpenditureHelper(this);

        mGroupExpenditureAmount = (EditText)findViewById(R.id.editGroupMemberExpenditureAmount);
        mGroupExpenditureNotes = (EditText)findViewById(R.id.editGroupMemberExpenditureNotes);
        groupExpenditureDeleteBtn = (Button)findViewById(R.id.editGroupMemberExpenditureDeleteButton);
        groupExpenditureUpdateBtn = (Button)findViewById(R.id.editGroupMemberExpenditureUpdateButton);
        materialExpenditureCategorySpinner = (MaterialBetterSpinner) findViewById(R.id.editGroupMemberExpenditureCategory);
        memberName = (TextView)findViewById(R.id.memberName);

        Intent intent = getIntent();
        final String groupMemberExpenditureCategory = intent.getStringExtra("memberExpenditureCategory");
        String groupMemberExpenditureAmount = intent.getStringExtra("memberExpenditureAmount");
        String groupMemberExpenditureDate = intent.getStringExtra("memberExpenditureDate");
        String groupMemberExpenditureNotes = intent.getStringExtra("memberExpenditureNotes");
        memberExpenditureLocalUniqueID = intent.getStringExtra("memberExpenditureLocalUniqueID");
        String nameOfMember = intent.getStringExtra("memberUsername");

        mGroupExpenditureAmount.setText(groupMemberExpenditureAmount);
        mGroupExpenditureNotes.setText(groupMemberExpenditureNotes);
        memberName.setText(nameOfMember);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit " + nameOfMember + "'s" + " " + "Expenditure");
        actionBar.setHomeButtonEnabled(true);

        groupExpenditureUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateGroupExpenditure();
            }
        });

        groupExpenditureDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GroupMemberExpenditure groupMemberExpenditureToDelete = new GroupMemberExpenditure();
                        groupMemberExpenditureToDelete.setLocalUniqueID(memberExpenditureLocalUniqueID);
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

                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        getSelectedExpenditureCategory(getExpenditureCategories());

    }

    public void getSelectedExpenditureCategory(List<String> expenditureCategories){
        ArrayAdapter<String> expenditureCategoriesAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                expenditureCategories
        );
        materialExpenditureCategorySpinner.setAdapter(expenditureCategoriesAdapter);

        materialExpenditureCategorySpinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                selectedExpenditureCategory = editable.toString();
            }
        });

    }

    public List<String> getExpenditureCategories(){
        List<String> expenditureCategories = new ArrayList<>();
        expenditureCategories.add("Rent");
        expenditureCategories.add("Food");
        expenditureCategories.add("Medical");
        expenditureCategories.add("Transport");
        expenditureCategories.add("Leisure");
        expenditureCategories.add("Entertainment");
        expenditureCategories.add("Gift");
        expenditureCategories.add("Clothes");
        expenditureCategories.add("Others");

        return expenditureCategories;
    }

    private void updateGroupExpenditure(){
        if ( !mGroupExpenditureAmount.getText().toString().equals("") &&
                selectedExpenditureCategory != null){
            String groupMemberExpenditureAmount = mGroupExpenditureAmount.getText().toString();
            String groupMemberExpenditureNotes = mGroupExpenditureNotes.getText().toString();

            GroupMemberExpenditure groupMemberExpenditure = new GroupMemberExpenditure();
            groupMemberExpenditure.setCategory(selectedExpenditureCategory);
            groupMemberExpenditure.setAmount(groupMemberExpenditureAmount);
            groupMemberExpenditure.setDate(new PeriodHelper().getDateToday());
            groupMemberExpenditure.setNotes(groupMemberExpenditureNotes);
            if (!memberExpenditureLocalUniqueID.equals("")){
                groupMemberExpenditure.setLocalUniqueID(memberExpenditureLocalUniqueID);
            }
            mParseHelper.updateGroupMembersExpenditureInParseDb(groupMemberExpenditure);

            startTabbedExpenditureActivity();

            Toast.makeText(EditGroupMemberExpenditureActivity.this, "Group Expenditure " + groupMemberExpenditure.getCategory() + " Updated", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(EditGroupMemberExpenditureActivity.this, "Expenditure amount and category are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedExpenditureActivity(){
        Intent tabbedExpenditureIntent = new Intent(EditGroupMemberExpenditureActivity.this, TabbedExpenditureActivity.class);
        tabbedExpenditureIntent.putExtra("position", "1");
        startActivity(tabbedExpenditureIntent);
        finish();
    }
}
