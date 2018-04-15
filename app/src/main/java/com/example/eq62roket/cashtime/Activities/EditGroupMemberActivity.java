package com.example.eq62roket.cashtime.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnReturnedGroupMemberListener;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.R;

import java.util.List;

public class EditGroupMemberActivity extends AppCompatActivity {

    private static final String TAG = "EditGroupMemberActivity";

    private EditText groupMemberUsername, groupMemberLocation,
            groupMemberPhone, groupMemberBusiness,
            groupMemberNationality, groupMemberEducationLevel,
            groupMemberGender, groupMemberHousehold;
    private Button btnDelete, btnUpdate;

    private String groupMemberLocalUniqueID;
    private ParseGroupHelper mParseGroupHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group_member);

        mParseGroupHelper = new ParseGroupHelper(EditGroupMemberActivity.this);

        Intent editGroupMemberIntent = getIntent();
        groupMemberLocalUniqueID = editGroupMemberIntent.getStringExtra("groupMemberLocalUniqueID");
        final String memberGroupLocalUniqueId = editGroupMemberIntent.getStringExtra("memberGroupLocalUniqueId");
        final String usernameOfGroupUser = editGroupMemberIntent.getStringExtra("groupMemberName");
        final String groupMemberCount = editGroupMemberIntent.getStringExtra("groupMemberCount");

        groupMemberUsername = (EditText) findViewById(R.id.groupMemberUsername);
        groupMemberBusiness = (EditText) findViewById(R.id.groupMembersBusiness);
        groupMemberLocation = (EditText) findViewById(R.id.groupMemberLocation);
        groupMemberPhone = (EditText) findViewById(R.id.groupMembersPhoneNumber);
        groupMemberNationality = (EditText) findViewById(R.id.groupMembersNationality);
        groupMemberEducationLevel = (EditText) findViewById(R.id.groupMembersEducationLevel);
        groupMemberGender = (EditText) findViewById(R.id.groupMembersGender);
        groupMemberHousehold = (EditText) findViewById(R.id.groupMembersHousehold);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        prepopulateUIWithUserInfo();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        GroupMember groupMemberToDelete = new GroupMember();
                        groupMemberToDelete.setLocalUniqueID(groupMemberLocalUniqueID);
                        mParseGroupHelper.deleteGroupMemberFromParseDb(groupMemberToDelete);

                        Group groupToUpdate = new Group();
                        groupToUpdate.setGroupMemberCount(Integer.parseInt(groupMemberCount));
                        groupToUpdate.setLocalUniqueID(memberGroupLocalUniqueId);
                        mParseGroupHelper.decrementGroupMemberCount(groupToUpdate);

                        startGroupMembersActivity();
                        Toast.makeText(EditGroupMemberActivity.this, "Member deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Cancel
                    }
                });

                builder.setMessage(
                        "Deleting member '" + usernameOfGroupUser + "' Can not be undone." + "Are You Sure You want to delete this Member?").setTitle("Delete Group Member");


                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMemberUserInfo();
            }
        });

    }

    public void prepopulateUIWithUserInfo(){

        new ParseGroupHelper(EditGroupMemberActivity.this).getMemberUserFromParseDb(groupMemberLocalUniqueID, new OnReturnedGroupMemberListener() {
            @Override
            public void onResponse(List<GroupMember> groupMemberList) {
                groupMemberUsername.setText(groupMemberList.get(0).getMemberUsername());
                groupMemberPhone.setText(groupMemberList.get(0).getMemberPhoneNumber());
                groupMemberHousehold.setText(groupMemberList.get(0).getMemberHousehold());
                groupMemberBusiness.setText(groupMemberList.get(0).getMemberBusiness());
                groupMemberGender.setText(groupMemberList.get(0).getMemberGender());
                groupMemberEducationLevel.setText(groupMemberList.get(0).getMemberEducationLevel());
                groupMemberNationality.setText(groupMemberList.get(0).getMemberNationality());
                groupMemberLocation.setText(groupMemberList.get(0).getMemberLocation());

            }

            @Override
            public void onFailure(String error) {

            }
        });

    }

    public void updateMemberUserInfo(){
        if (!groupMemberUsername.getText().toString().equals("") &&
                !groupMemberPhone.getText().toString().equals("") &&
                !groupMemberHousehold.getText().toString().equals("") &&
                !groupMemberBusiness.getText().toString().equals("") &&
                !groupMemberGender.getText().toString().equals("") &&
                !groupMemberEducationLevel.getText().toString().equals("") &&
                !groupMemberNationality.getText().toString().equals("") &&
                !groupMemberLocation.getText().toString().equals("")){

            GroupMember groupMemberToUpdate = new GroupMember();
            groupMemberToUpdate.setMemberUsername(groupMemberUsername.getText().toString().trim());
            groupMemberToUpdate.setMemberPhoneNumber(groupMemberPhone.getText().toString().trim());
            groupMemberToUpdate.setMemberHousehold(groupMemberHousehold.getText().toString().trim());
            groupMemberToUpdate.setMemberBusiness(groupMemberBusiness.getText().toString().trim());
            groupMemberToUpdate.setMemberGender(groupMemberGender.getText().toString().trim());
            groupMemberToUpdate.setMemberEducationLevel(groupMemberEducationLevel.getText().toString().trim());
            groupMemberToUpdate.setMemberNationality(groupMemberNationality.getText().toString().trim());
            groupMemberToUpdate.setMemberLocation(groupMemberLocation.getText().toString().trim());
            groupMemberToUpdate.setLocalUniqueID(groupMemberLocalUniqueID);
            mParseGroupHelper.updateGroupMemberInParseDb(groupMemberToUpdate);

            startGroupMembersActivity();
            Toast.makeText(
                    EditGroupMemberActivity.this,
                    "Group Member Successfully Updated",
                    Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(EditGroupMemberActivity.this, "All Fields are required", Toast.LENGTH_SHORT).show();
        }
    }

    public void startGroupMembersActivity(){
        Intent intent = new Intent(EditGroupMemberActivity.this, GroupsActivity.class);
        startActivity(intent);
        finish();
    }
}
