package com.example.eq62roket.CashTime.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.CashTime.R;
import com.example.eq62roket.CashTime.helper.SQLiteHelper;
import com.example.eq62roket.CashTime.helper.UserCrud;
import com.example.eq62roket.CashTime.models.User;

public class HomeneedsActivity extends AppCompatActivity {

    SQLiteHelper myHelper;
    EditText edtHomeneeds;
    Button btnHomeneeds;
    UserCrud userCrud;
<<<<<<< HEAD
    private static final String TAG = "SavingsActivity";
=======
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeneeds);

        edtHomeneeds = (EditText) findViewById(R.id.amtHomeneeds);
        btnHomeneeds = (Button) findViewById(R.id.btnHomeneeds);
        myHelper = new SQLiteHelper(this);

        userCrud = new UserCrud(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userCrud = new UserCrud(this);

        AddHomeneeds();

    }

    public void AddHomeneeds(){
        btnHomeneeds.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
<<<<<<< HEAD

                        if (  !edtHomeneeds.getText().toString().equals("")  ){
                            Log.d(TAG, "am here---" + edtHomeneeds.getText()+"---");

                            int yVal = Integer.parseInt(String.valueOf(edtHomeneeds.getText()));

                            boolean isInseted = myHelper.insertHomeneeds(yVal);


                            if (isInseted) {

                                // if user creates goal for the first time, award them 3 points
=======
                        if (!edtHomeneeds.getText().toString().equals("")) {
                            int yVal = Integer.parseInt(String.valueOf(edtHomeneeds.getText()));
                            boolean isInseted = myHelper.insertHomeneeds(yVal);
                            if (isInseted) {
                                // if user spends on any expense, award them 2 points
>>>>>>> origin/master
                                User user = userCrud.getLastUserInserted();
                                user.setPoints(2);
                                userCrud.updateUser(user);

<<<<<<< HEAD
                                Toast.makeText(HomeneedsActivity.this, "Your Home needs have been stored", Toast.LENGTH_LONG).show();
                                Intent Homeneeds = new Intent(HomeneedsActivity.this, ExpenditureActivity.class);
                                HomeneedsActivity.this.startActivity(Homeneeds);
                            }
                            else {
                                Toast.makeText(HomeneedsActivity.this, "Your Home needs have not been stored", Toast.LENGTH_LONG).show();
=======
                                Toast.makeText(HomeneedsActivity.this, "Your home needs costs have been stored", Toast.LENGTH_LONG).show();
                                Intent Homeneedsintent = new Intent(HomeneedsActivity.this, ExpenditureActivity.class);
                                HomeneedsActivity.this.startActivity(Homeneedsintent);
                            }
                            else {
                                Toast.makeText(HomeneedsActivity.this, "Your home needs costs where not stored", Toast.LENGTH_LONG).show();
>>>>>>> origin/master
                            }
                        }
                        else {
                            Toast.makeText(HomeneedsActivity.this, "Please input amount before submitting", Toast.LENGTH_LONG).show();
                        }

<<<<<<< HEAD
//                        int yVal = Integer.parseInt(String.valueOf(edtHomeneeds.getText()));
//                        boolean isInseted = myHelper.insertHomeneeds(yVal);
//                        if (isInseted = true)
//                            Toast.makeText(HomeneedsActivity.this, "Your home needs costs have been stored", Toast.LENGTH_LONG).show();
//                        else
//                            Toast.makeText(HomeneedsActivity.this, "Your home needs costs where not stored", Toast.LENGTH_LONG).show();
//                        Intent Homeneedsintent = new Intent(HomeneedsActivity.this, ExpenditureActivity.class);
//                        HomeneedsActivity.this.startActivity(Homeneedsintent);
=======
>>>>>>> origin/master
                    }

                }
        );
    }
}
