package com.example.eq62roket.cashtime.Helper;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by etwin on 4/3/18.
 */

public class ProgressDialogHelper {
    private Context mContext;
    private ProgressDialog mProgressDialog;

    public ProgressDialogHelper(Context context){
        mContext = context;
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setCancelable(false);
    }

    public void setProgreDialogTitle(String title){
        mProgressDialog.setTitle(title);
    }

    public void setProgressDialogMessage(String message){
        mProgressDialog.setMessage(message);
    }

    public void showProgressDialog(){
        mProgressDialog.show();
    }

    public void dismissProgressDialog(){
        mProgressDialog.dismiss();
    }
}
