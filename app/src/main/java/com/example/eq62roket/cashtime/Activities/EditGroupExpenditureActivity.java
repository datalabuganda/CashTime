package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureHelper;
import com.example.eq62roket.cashtime.Helper.PeriodHelper;
import com.example.eq62roket.cashtime.Models.GroupExpenditure;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class EditGroupExpenditureActivity extends AppCompatActivity {
    private static String TAG = "EditGroupExpenditureActivity";
    private EditText mGroupExpenditureAmount, mGroupExpenditureNotes;
    private Button groupExpenditureDeleteBtn, groupExpenditureUpdateBtn;
    private MaterialBetterSpinner materialExpenditureCategorySpinner;

    private String groupExpenditureLocalUniqueID = "";
    private String selectedExpenditureCategory;
    private ParseExpenditureHelper mParseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_expenditure);

        mParseHelper = new ParseExpenditureHelper(this);

        mGroupExpenditureAmount = (EditText)findViewById(R.id.editGroupExpenditureAmount);
        mGroupExpenditureNotes = (EditText)findViewById(R.id.editGroupExpenditureNotes);
        groupExpenditureDeleteBtn = (Button)findViewById(R.id.editGroupExpenditureDeleteButton);
        groupExpenditureUpdateBtn = (Button)findViewById(R.id.editGroupExpenditureUpdateButton);
        materialExpenditureCategorySpinner = (MaterialBetterSpinner) findViewById(R.id.editGroupExpenditureCategory);

        // get Intent data
        Intent intent = getIntent();
        final String groupExpenditureCategory = intent.getStringExtra("groupExpenditureCategory");
        String groupExpenditureAmount = intent.getStringExtra("groupExpenditureAmount");
        String groupExpenditureDate = intent.getStringExtra("groupExpenditureDate");
        String groupExpenditureNotes = intent.getStringExtra("groupExpenditureNotes");
        groupExpenditureLocalUniqueID = intent.getStringExtra("groupExpenditureLocalUniqueID");

        mGroupExpenditureAmount.setText(groupExpenditureAmount);
        mGroupExpenditureNotes.setText(groupExpenditureNotes);

        getSelectedExpenditureCategory(getExpenditureCategories());

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

                        GroupExpenditure groupExpenditureToDelete = new GroupExpenditure();
                        groupExpenditureToDelete.setLocalUniqueID(groupExpenditureLocalUniqueID);
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

                android.support.v7.app.AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

    }

    public void getSelectedExpenditureCategory(List<String> expenditureCategories){
        ArrayAdapter<String> incomeSourcesAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                expenditureCategories
        );
        materialExpenditureCategorySpinner.setAdapter(incomeSourcesAdapter);

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
            String groupExpenditureAmount = mGroupExpenditureAmount.getText().toString();
            String groupExpenditureNotes = mGroupExpenditureNotes.getText().toString();

            GroupExpenditure groupExpenditure = new GroupExpenditure();
            groupExpenditure.setCategory(selectedExpenditureCategory);
            groupExpenditure.setAmount(groupExpenditureAmount);
            groupExpenditure.setDate(new PeriodHelper().getDateToday());
            groupExpenditure.setNotes(groupExpenditureNotes);
            if (!groupExpenditureLocalUniqueID.equals("")){
                groupExpenditure.setLocalUniqueID(groupExpenditureLocalUniqueID);
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
