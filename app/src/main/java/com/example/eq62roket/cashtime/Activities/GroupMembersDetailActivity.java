package com.example.eq62roket.cashtime.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eq62roket.cashtime.R;
import com.example.eq62roket.cashtime.adapters.DatabaseAdapter;

public class GroupMembersDetailActivity extends AppCompatActivity {
    EditText updateMemberName, updateMemberPhone;
    TextView updateText, deleteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members_detail);
        updateMemberName = (EditText)findViewById(R.id.editGroupMemberName);
        updateMemberPhone = (EditText)findViewById(R.id.editGroupMemberPhone);

        updateText = (TextView)findViewById(R.id.updateGroupMember);
        deleteText = (TextView)findViewById(R.id.deleteGroupMember);

        //Receive member from main activity
        Intent intent = getIntent();

        final String name = intent.getExtras().getString("Name");
        final String phone = intent.getExtras().getString("Phone");
        final int id = intent.getExtras().getInt("Id");

        updateMemberName.setText(name);
        updateMemberPhone.setText(phone);

        updateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(id,updateMemberName.getText().toString(),updateMemberPhone.getText().toString());
            }
        });

        deleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(id);
            }
        });
    }

    private void update(int id, String newName, String newPhone){
        DatabaseAdapter db = new DatabaseAdapter(this);
        db.openDatabase();
        long result = db.updateGroupMember(id,newName,newPhone);

        if(result>0){
            updateMemberName.setText(newName);
            updateMemberPhone.setText(newPhone);
            Toast.makeText(GroupMembersDetailActivity.this,"Updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(GroupMembersDetailActivity.this, "Updating failed", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }

    //deleting a group member
    private void delete(int id){
        DatabaseAdapter db = new DatabaseAdapter(this);
        db.openDatabase();
        long result = db.deleteGroupMember(id);

        if (result>0){
            this.finish();
        }else {
            Toast.makeText(GroupMembersDetailActivity.this, "Failed to delete member", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
