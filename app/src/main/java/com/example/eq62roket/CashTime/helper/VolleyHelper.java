package com.example.eq62roket.CashTime.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by probuse on 9/4/17.
 */

public class VolleyHelper {

    private static final String TAG = "VolleyHelper";

    Context context;
    UserCrud userCrud;
    GoalCrud goalCrud;

    ExpenditureCrud expenditureCrud;
    IncomeCrud incomeCrud;

    Goal lastInsertedGoal;
    User lastInsertedUser;
    RequestQueue queue;

    public VolleyHelper(Context context) {
        this.context = context;
        userCrud = new UserCrud(context);
        goalCrud = new GoalCrud(context);
        this.lastInsertedGoal = goalCrud.getLastInsertedGoal();
        this.lastInsertedUser = userCrud.getLastUserInserted();

        expenditureCrud = new ExpenditureCrud(context);
        incomeCrud = new IncomeCrud(context);
        queue = Volley.newRequestQueue(context);
    }

    public void sendUserData() {

        String url = "http://165.227.67.248/cashTimePhpDatabase/user.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        String userId = response;
                        //int userId = Integer.valueOf(response);
                        lastInsertedUser.setPhpId(String.valueOf(userId));
                        lastInsertedUser.setSyncStatus(1);
                        userCrud.updateUser(lastInsertedUser);
                        Log.d(TAG, "phpId: " +  goalCrud.getLastInsertedGoal().getPhpId());
                        Log.d(TAG, "phpId: " +  goalCrud.getLastInsertedGoal().getPhpId());
                        Log.d("Response User", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("household", String.valueOf(lastInsertedUser.getHousehold()));
                params.put("sex", lastInsertedUser.getSex());
                params.put("age", String.valueOf(lastInsertedUser.getAge()));
                params.put("educationLevel", lastInsertedUser.getEducationlevel());
                params.put("nationality", lastInsertedUser.getNationality());
                params.put("points", String.valueOf(lastInsertedUser.getPoints()));

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "text/html; charset=utf-8");
                return headers;
            }
        };
        queue.add(postRequest);
    }

    public void sendGoalData(){
        String url = "http://192.168.1.201/goal.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        int goalId = Integer.valueOf(response);
                        lastInsertedGoal.setPhpId(String.valueOf(goalId));
                        lastInsertedGoal.setSyncStatus(1);
                        goalCrud.updateGoal(lastInsertedGoal);
                        Log.d("Response Goal", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("goalName", lastInsertedGoal.getName());
                params.put("goalAmount", String.valueOf(lastInsertedGoal.getAmount()));
                params.put("goalStartDate",lastInsertedGoal.getStartDate());
                params.put("goalEndDate", lastInsertedGoal.getEndDate());

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void sendExpenditureData(){
        String url = "http://192.168.1.201/expenditure.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        int expenditureId = Integer.valueOf(response);
                        expenditureCrud.insertPhpId(expenditureId);
                        expenditureCrud.updateSyncExpenditure();
                        Log.d(TAG, "expenditurephpId: "+ expenditureCrud.getPhpID());
                        Log.d(TAG, "expendituresync status: "+ expenditureCrud.getSyncStatus());
                        Log.d("Response Exp", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("totalTransportExpenditure", String.valueOf(expenditureCrud.addAllTransport()));
                params.put("totalEducationExpenditure", String.valueOf(expenditureCrud.addAllEducation()));
                params.put("totalHealthExpenditure", String.valueOf(expenditureCrud.addAllHealth()));
                params.put("totalSavingsExpenditure", String.valueOf(expenditureCrud.addAllSavings(null)));
                params.put("totalOthersExpenditure", String.valueOf(expenditureCrud.addAllOthers()));
                params.put("totalHomeNeedsExpenditure", String.valueOf(expenditureCrud.addAllHomeneeds()));
                params.put("totalExpenditureExpenditure", String.valueOf(expenditureCrud.addAllCategories()));

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void sendIncomeData(){
        String url = "http://192.168.1.201/income.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        int incomeId = Integer.valueOf(response);
                        Log.d(TAG, "incomeId: " + incomeId);
                        incomeCrud.insertPhpId(incomeId);
                        incomeCrud.updateSyncIncome();
                        Log.d(TAG, "incomeIddb: " + incomeCrud.getPhpID());
                        Log.d("Response Income", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("totalLoanIncome", String.valueOf(incomeCrud.addAllLoan()));
                params.put("totalSalaryIncome", String.valueOf(incomeCrud.addAllSalary()));
                params.put("totalOthersIncome", String.valueOf(incomeCrud.addAllOthers()));
                params.put("totalIncome", String.valueOf(incomeCrud.addAllIncome()));

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void updateUserData() {

        String url = "http://165.227.67.248/cashTimePhpDatabase/user_update.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        lastInsertedUser.setSyncStatus(1);
                        userCrud.updateUser(lastInsertedUser);
                        Log.d("Response update User", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("household", String.valueOf(lastInsertedUser.getHousehold()));
                params.put("sex", lastInsertedUser.getSex());
                params.put("age", String.valueOf(lastInsertedUser.getAge()));
                params.put("educationLevel", lastInsertedUser.getEducationlevel());
                params.put("nationality", lastInsertedUser.getNationality());
                params.put("points", String.valueOf(lastInsertedUser.getPoints()));
                params.put("id", lastInsertedUser.getPhpId());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "text/html; charset=utf-8");
                return headers;
            }
        };
        queue.add(postRequest);
    }


    public void updateGoalData(){
        String url = "http://192.168.1.201/goal_update.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        lastInsertedGoal.setSyncStatus(1);
                        goalCrud.updateGoal(lastInsertedGoal);
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("goalName", lastInsertedGoal.getName());
                params.put("goalAmount", String.valueOf(lastInsertedGoal.getAmount()));
                params.put("goalStartDate",lastInsertedGoal.getStartDate());
                params.put("goalEndDate", lastInsertedGoal.getEndDate());

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void updateExpenditureData(){
        String url = "http://192.168.1.201/expenditure_update.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        expenditureCrud.updateSyncExpenditure();

                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("totalTransportExpenditure", String.valueOf(expenditureCrud.addAllTransport()));
                params.put("totalEducationExpenditure", String.valueOf(expenditureCrud.addAllEducation()));
                params.put("totalHealthExpenditure", String.valueOf(expenditureCrud.addAllHealth()));
                params.put("totalSavingsExpenditure", String.valueOf(expenditureCrud.addAllSavings(null)));
                params.put("totalOthersExpenditure", String.valueOf(expenditureCrud.addAllOthers()));
                params.put("totalHomeNeedsExpenditure", String.valueOf(expenditureCrud.addAllHomeneeds()));
                params.put("totalExpenditureExpenditure", String.valueOf(expenditureCrud.addAllCategories()));

                return params;
            }
        };
        queue.add(postRequest);
    }

    public void updateIncomeData(){
        String url = "http://192.168.1.201/income_update.php";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        incomeCrud.updateSyncIncome();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("totalLoanIncome", String.valueOf(incomeCrud.addAllLoan()));
                params.put("totalSalaryIncome", String.valueOf(incomeCrud.addAllSalary()));
                params.put("totalOthersIncome", String.valueOf(incomeCrud.addAllOthers()));
                params.put("totalIncome", String.valueOf(incomeCrud.addAllIncome()));

                return params;
            }
        };
        queue.add(postRequest);
    }


}
