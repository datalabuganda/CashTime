package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.R;

public class EditGroupActivity extends AppCompatActivity {
    private static final String TAG = "EditGroupActivity";
    EditText groupName, groupLocation, groupCenter;
    Button groupDeleteBtn, groupUpdateBtn;

    private String groupLocalUniqueID;
    private String locationOfGroup;
    private String groupCentreName;
    private String nameOfGroup;
    private ParseGroupHelper mParseGroupHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        mParseGroupHelper = new ParseGroupHelper(EditGroupActivity.this);

        Intent groupIntent = getIntent();
        groupLocalUniqueID = groupIntent.getStringExtra("groupLocalUniqueID");
        groupCentreName = groupIntent.getStringExtra("groupCentreName");
        locationOfGroup = groupIntent.getStringExtra("groupLocation");
        nameOfGroup = groupIntent.getStringExtra("nameOfGroup");



        groupName = (EditText)findViewById(R.id.groupName);
        groupLocation = (EditText)findViewById(R.id.groupLocation);
        groupCenter = (EditText)findViewById(R.id.groupCenter);

        groupName.setText(nameOfGroup);
        groupLocation.setText(locationOfGroup);
        groupCenter.setText(groupCentreName);

        groupDeleteBtn = (Button)findViewById(R.id.groupDeleteBtn);
        groupUpdateBtn = (Button)findViewById(R.id.groupUpdateBtn);

        groupUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!groupName.getText().toString().trim().equals("") &&
                        !groupLocation.getText().toString().trim().equals("") &&
                        !groupCenter.getText().toString().trim().equals("")){

                    String nameOfGroup = groupName.getText().toString().trim();
                    String locationOfGroup = groupLocation.getText().toString().trim();
                    String groupCentreName = groupCenter.getText().toString().trim();

                    Group groupToUpdate = new Group();
                    groupToUpdate.setGroupName(nameOfGroup);
                    groupToUpdate.setGroupCentreName(groupCentreName);
                    groupToUpdate.setLocationOfGroup(locationOfGroup);
                    groupToUpdate.setLocalUniqueID(groupLocalUniqueID);
                    mParseGroupHelper.updateGroupInParseDb(groupToUpdate);

                    startGroupsActivity();
                    Toast.makeText(EditGroupActivity.this, "Your group has been updated", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(EditGroupActivity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }

            }
        });

        groupDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Group groupToDelete = new Group();
                        groupToDelete.setLocalUniqueID(groupLocalUniqueID);
                        mParseGroupHelper.deleteAllGroupMembersFromParseDb(groupLocalUniqueID);
                        mParseGroupHelper.deleteGroupFromParseDb(groupToDelete);

                        startGroupsActivity();
                        Toast.makeText(EditGroupActivity.this, "Group deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting Group '" + nameOfGroup + "' Can not be undone. All Members in group will be deleted" +
                                " Are You Sure You want to delete this Group?")
                        .setTitle("Delete Group");

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void startGroupsActivity(){
        Intent homeActivityIntent = new Intent(EditGroupActivity.this, GroupsActivity.class);
        startActivity(homeActivityIntent);
        finish();
    }
}
