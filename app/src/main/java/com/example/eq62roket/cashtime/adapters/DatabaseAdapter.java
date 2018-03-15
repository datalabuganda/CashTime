package com.example.eq62roket.cashtime.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.eq62roket.cashtime.Helper.DatabaseHelper;

/**
 * Created by eq62roket on 3/12/18.
 */

public class DatabaseAdapter {
    Context c;
    SQLiteDatabase db;
    DatabaseHelper helper;

    public DatabaseAdapter(Context ctx) {
        this.c = ctx;
        helper=new DatabaseHelper(c);
    }

    //open database
    public DatabaseAdapter openDatabase(){
        try {
            db=helper.getWritableDatabase();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return this;
    }

    //close database
    public void close(){
        try {
            helper.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //inserting data into the database
    public long addNewGroupMember(String name, String phone){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.Name,name);
            contentValues.put(DatabaseHelper.Phone, phone);

            return db.insert(DatabaseHelper.TABLE_NAME, DatabaseHelper.ROW_ID,contentValues);
        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    //retrieve all group members
    public Cursor getAllGroupMembers(){
        String[] columns = {DatabaseHelper.ROW_ID,DatabaseHelper.Name,DatabaseHelper.Phone};
        return db.query(DatabaseHelper.TABLE_NAME,columns,null,null,null,null,null);
    }

    //Updating group members
    public long updateGroupMember(int id, String name, String phone){
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(helper.Name, name);
            contentValues.put(helper.Phone, phone);

            return db.update(helper.TABLE_NAME,contentValues,helper.ROW_ID+" =?", new String[]{String.valueOf(id)});
        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    //Deleting group members
    public long deleteGroupMember(int id){
        try {
            return db.delete(helper.TABLE_NAME,helper.ROW_ID+" =?", new String[]{String.valueOf(id)});
        }catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

}
