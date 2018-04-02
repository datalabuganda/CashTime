package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Models.IncomeSources;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eq62roket on 4/1/18.
 */

public class ParseIncomeSourceHelper {
    public interface OnReturnedSourcesListener{
        void onResponse(List<IncomeSources> incomeSources);
        void onFailure(String error);
    }


    private static final String TAG = "ParseIncomeSourceHelper";
    private final List<IncomeSources> sourcesList = new ArrayList<>();


    private Context mContext;
    private ParseIncomeSourceHelper.OnReturnedSourcesListener mOnReturnedSourcesListener;


    public ParseIncomeSourceHelper(Context context){
        mContext = context;
    }

    public void saveSourcesToParseDb(IncomeSources sources){
        IncomeSources newIncomeSource = new IncomeSources();
        newIncomeSource.put("incomeSource", sources.getName());
        newIncomeSource.saveInBackground();

    }

    public void getArrayOfSourceFromParseDb(){
        ParseQuery<IncomeSources> sourcesParseQuery = ParseQuery.getQuery("Sources");
        sourcesParseQuery.addDescendingOrder("updatedAt");
        String currentUser = ParseUser.getCurrentUser().getObjectId();
        sourcesParseQuery.whereEqualTo("objectId", currentUser);
        sourcesParseQuery.findInBackground(new FindCallback<IncomeSources>() {
            @Override
            public void done(List<IncomeSources> objects, ParseException e) {
                
            }
        });
    }

    public void getSourcesFromParseDb(final ParseIncomeSourceHelper.OnReturnedSourcesListener onReturnedSourcesListener){
        ParseQuery<IncomeSources> incomeSourcesParseQuery = ParseQuery.getQuery("Sources");
        incomeSourcesParseQuery.addDescendingOrder("updatedAt");
        incomeSourcesParseQuery.findInBackground(new FindCallback<IncomeSources>() {
            @Override
            public void done(List<IncomeSources> parseIncomeSources, ParseException e) {
                if (e == null){
                    for (IncomeSources retrievedSources: parseIncomeSources){
                        IncomeSources newIncomeSources = new IncomeSources();
                        newIncomeSources.setName(retrievedSources.get("incomeSource").toString());
                        newIncomeSources.setParseId(retrievedSources.getObjectId());

                        sourcesList.add(newIncomeSources);
                    }
                    if (onReturnedSourcesListener != null){
                        onReturnedSourcesListener.onResponse(sourcesList);
                    }
                }else {
                    onReturnedSourcesListener.onFailure(e.getMessage());
                }
            }

        });

    }

    public void updateIncomeInParseDb(final IncomeSources incomeSourceToUpdate){
        ParseQuery<IncomeSources> incomeSourcesParseQuery = ParseQuery.getQuery("Sources");
        incomeSourcesParseQuery.getInBackground(incomeSourceToUpdate.getParseId(), new GetCallback<IncomeSources>() {
            @Override
            public void done(IncomeSources incomeSource, ParseException e) {
                if (e == null) {
                    incomeSource.put("incomeSource", incomeSourceToUpdate.getName());
                    incomeSource.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteSourceFromParseDb(IncomeSources incomeSourceToDelete){
        ParseQuery<IncomeSources> incomeSourcesParseQuery = ParseQuery.getQuery("Sources");
        incomeSourcesParseQuery.getInBackground(incomeSourceToDelete.getParseId(), new GetCallback<IncomeSources>() {
            @Override
            public void done(IncomeSources incomeSources, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Should delete now: ");
                    incomeSources.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }
}
