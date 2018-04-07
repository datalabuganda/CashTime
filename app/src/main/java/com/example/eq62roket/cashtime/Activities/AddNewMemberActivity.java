package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eq62roket.cashtime.Helper.ParseGroupHelper;
import com.example.eq62roket.cashtime.Interfaces.OnSuccessfulRegistrationListener;
import com.example.eq62roket.cashtime.Models.Group;
import com.example.eq62roket.cashtime.Models.GroupMember;
import com.example.eq62roket.cashtime.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class AddNewMemberActivity extends AppCompatActivity {

    EditText groupMemberUsername, groupMemberLocation, groupMemberPhone, groupMemberBusiness,
            groupMemberNationality, groupMemberEducationLevel, groupMemberGender, groupMemberHousehold;
    CardView groupMemberRegister;

    private String groupParseId, groupName;
    private ParseGroupHelper mParseGroupHelper;

    public static String[] nationalityCategories = {"Ugandan", "Kenyan", "Rwandan", "Congolese", "Tanzanian",
            "South Sudanese"};

    public static String[] genderCategories = {"Male", "Female"};

    public static String[] levelOfEducationCategories = {"Primary", "O Level", "A Level", "University",
            "Institution"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_member);

        // TODO: 3/31/18 ===> make sure names are unique in a group
        // TODO: 4/7/18  ===> Adding a new member does not update immediately(needs refresh first.)

        mParseGroupHelper = new ParseGroupHelper(AddNewMemberActivity.this);

        Intent addNewMemberIntent = getIntent();
        groupParseId = addNewMemberIntent.getStringExtra("groupParseId");
        groupName = addNewMemberIntent.getStringExtra("groupName");

        groupMemberBusiness = (EditText) findViewById(R.id.groupMembersBusiness);
        groupMemberUsername = (EditText) findViewById(R.id.groupMemberUsername);
        groupMemberLocation = (EditText) findViewById(R.id.groupMemberLocation);
        groupMemberPhone = (EditText) findViewById(R.id.groupMembersPhoneNumber);
        groupMemberNationality = (EditText) findViewById(R.id.groupMembersNationality);
        groupMemberEducationLevel = (EditText) findViewById(R.id.groupMembersEducationLevel);
        groupMemberGender = (EditText) findViewById(R.id.groupMembersGender);
        groupMemberHousehold = (EditText) findViewById(R.id.groupMembersHousehold);

        groupMemberRegister = (CardView)findViewById(R.id.groupMemberRegister);

        groupMemberRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!groupMemberUsername.getText().toString().equals("") &&
                        !groupMemberPhone.getText().toString().equals("") &&
                        !groupMemberHousehold.getText().toString().equals("") &&
                        !groupMemberBusiness.getText().toString().equals("") &&
                        !groupMemberGender.getText().toString().equals("") &&
                        !groupMemberEducationLevel.getText().toString().equals("") &&
                        !groupMemberNationality.getText().toString().equals("") &&
                        !groupMemberLocation.getText().toString().equals("")){


//                    mParseGroupHelper.getParticularGroupFromParseDb(groupParseId, new OnReturnedGroupsListener() {
//                        @Override
//                        public void onResponse(List<Group> groupsList) {
//                            groupName = groupsList.get(0).getGroupName();
//                            Log.d("GroupName", "onResponse: " + groupName);
//                        }
//
//                        @Override
//                        public void onFailure(String error) {
//
//                        }
//                    });

                    String mUsername = groupMemberUsername.getText().toString().trim();
                    String mUserPhone = groupMemberPhone.getText().toString().trim();
                    String mUserHousehold = groupMemberHousehold.getText().toString().trim();
                    String mUserBusiness = groupMemberBusiness.getText().toString().trim();
                    String mUserGender = groupMemberGender.getText().toString().trim();
                    String mUserEducationLevel = groupMemberEducationLevel.getText().toString().trim();
                    String mUserNationality = groupMemberNationality.getText().toString().trim();
                    String mUserLocation = groupMemberLocation.getText().toString().trim();

                    GroupMember newGroupMember = new GroupMember();
                    newGroupMember.setMemberUsername(mUsername);
                    newGroupMember.setMemberPhoneNumber(mUserPhone);
                    newGroupMember.setMemberHousehold(mUserHousehold);
                    newGroupMember.setMemberBusiness(mUserBusiness);
                    newGroupMember.setMemberGender(mUserGender);
                    newGroupMember.setMemberEducationLevel(mUserEducationLevel);
                    newGroupMember.setMemberNationality(mUserNationality);
                    newGroupMember.setMemberLocation(mUserLocation);
                    newGroupMember.setMemberGroupId(groupParseId);
//                    newGroupMember.setGroupName(groupName);
                    newGroupMember.setMemberPoints(3);

                    mParseGroupHelper.saveGroupMemberUserToParseDb(newGroupMember, new OnSuccessfulRegistrationListener() {
                        @Override
                        public void onResponse(String success) {

                            Group groupToUpdate = new Group();
                            groupToUpdate.setGroupParseId(groupParseId);
                            mParseGroupHelper.incrementGroupMemberCount(groupToUpdate);

                            Intent intent = new Intent(AddNewMemberActivity.this, GroupsActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(AddNewMemberActivity.this, "Group Member Added", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(AddNewMemberActivity.this, "Member not Added " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(AddNewMemberActivity.this, "All Fields Are Required", Toast.LENGTH_SHORT).show();
                }

            }
        });

        nationalityCategory();
        genderCategory();
        levelOfEducationCategory();

    }

    public void nationalityCategory(){
        ArrayAdapter<String> nationalityAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                nationalityCategories
        );

        MaterialBetterSpinner materialNationalitySpinner = (MaterialBetterSpinner) findViewById(R.id.groupMembersNationality);
        materialNationalitySpinner.setAdapter(nationalityAdapter);
    }

    public void genderCategory(){
        ArrayAdapter<String> genderAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                genderCategories
        );

        MaterialBetterSpinner materialGenderSpinner = (MaterialBetterSpinner) findViewById(R.id.groupMembersGender);
        materialGenderSpinner.setAdapter(genderAdapter);
    }

    public void levelOfEducationCategory(){
        ArrayAdapter<String> levelOfEducationAdapter = new ArrayAdapter<String>(
                this,
                R.layout.support_simple_spinner_dropdown_item,
                levelOfEducationCategories
        );

        MaterialBetterSpinner materialLevelOfEducationSpinner = (MaterialBetterSpinner) findViewById(R.id.groupMembersEducationLevel);
        materialLevelOfEducationSpinner.setAdapter(levelOfEducationAdapter);
    }

}
