package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseExpenditureCategoryHelper;
import com.example.eq62roket.cashtime.Models.ExpenditureCategories;
import com.example.eq62roket.cashtime.R;
import com.parse.ParseUser;

public class AddExpenditureCategoryActivity extends AppCompatActivity {
    private static String TAG = "AddExpenditureCategoryActivity";
    EditText expenditureCategory;
    Button expenditureCategoryCancelBtn, expenditureCategorySaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenditure_category);

        expenditureCategory = (EditText)findViewById(R.id.addExpenditureCategory);

        expenditureCategoryCancelBtn = (Button)findViewById(R.id.expenditureCategoryCancelBtn);
        expenditureCategorySaveBtn = (Button)findViewById(R.id.expenditureCategorySaveBtn);

        expenditureCategorySaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveExpenditureCategory();
            }
        });

        expenditureCategoryCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTabbedExpenditureActivity();
            }
        });
    }

    private void saveExpenditureCategory(){
        if ( !expenditureCategory.getText().toString().equals("")){
            String nameOfCategory = expenditureCategory.getText().toString();
            String currentUserId = ParseUser.getCurrentUser().getObjectId();

            ExpenditureCategories expenditureCategories = new ExpenditureCategories();
            expenditureCategories.setName(nameOfCategory);
            expenditureCategories.setUserId(currentUserId);


            // TODO: 3/22/18 =====> save object to db
            new ParseExpenditureCategoryHelper(this).saveCategoriesToParseDb(expenditureCategories);
            startTabbedExpenditureActivity();

            Toast.makeText(AddExpenditureCategoryActivity.this, "Expenditure Category " + expenditureCategories.getName() + " saved", Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(AddExpenditureCategoryActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startTabbedExpenditureActivity(){
        Intent tabbedExpenditureCategoryIntent = new Intent(AddExpenditureCategoryActivity.this, TabbedExpenditureActivity.class);
        startActivity(tabbedExpenditureCategoryIntent);
        finish();
    }
}
