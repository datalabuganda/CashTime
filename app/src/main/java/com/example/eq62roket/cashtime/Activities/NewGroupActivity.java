package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewGroupActivity extends AppCompatActivity {
    private static final String TAG = "NewGroupActivity";
    EditText groupName, groupLocation, groupCenter;
    Button groupCancelBtn, groupSaveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_group);
        Parse.initialize(this);

        groupName = (EditText)findViewById(R.id.groupName);
        groupLocation = (EditText)findViewById(R.id.groupLocation);
        groupCenter = (EditText)findViewById(R.id.groupCenter);

        groupCancelBtn = (Button)findViewById(R.id.groupCancelBtn);
        groupSaveBtn = (Button)findViewById(R.id.groupSaveBtn);


        groupSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = groupName.getText().toString().trim();
                String location = groupLocation.getText().toString().trim();
                String center = groupCenter.getText().toString().trim();

                String currentUser = ParseUser.getCurrentUser().getObjectId();
                ParseObject group = new ParseObject("Group");
                group.put("name", name);
                group.put("location", location);
                group.put("center", center);
                group.put("Userid", currentUser);


                group.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){
                            Toast.makeText(NewGroupActivity.this, "Your group has been created", Toast.LENGTH_SHORT).show();
                            Intent groupIntent = new Intent(NewGroupActivity.this, HomeActivity.class);
                            startActivity(groupIntent);
                        }else {
                            Toast.makeText(NewGroupActivity.this, "Failed to create group", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}
