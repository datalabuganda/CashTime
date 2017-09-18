package com.example.eq62roket.CashTime.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.eq62roket.CashTime.models.Goal;
import com.example.eq62roket.CashTime.models.User;

import org.json.JSONException;
import org.json.JSONObject;

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

        String url = "http://165.227.67.248:8000/user/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("household", String.valueOf(lastInsertedUser.getHousehold()));
        params.put("sex", lastInsertedUser.getSex());
        params.put("age", String.valueOf(lastInsertedUser.getAge()));
        params.put("educationLevel", lastInsertedUser.getEducationlevel());
        params.put("nationality", lastInsertedUser.getNationality());
        params.put("points", String.valueOf(lastInsertedUser.getPoints()));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                       String userId = null;
                        try {
                            userId = String.valueOf(response.get("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //int userId = Integer.valueOf(response);
                        lastInsertedUser.setPhpId(String.valueOf(userId));
                        lastInsertedUser.setSyncStatus(1);
                        userCrud.updateUser(lastInsertedUser);
                        Log.d(TAG, "phpId: " +  lastInsertedUser.getPhpId());
                        Log.d(TAG, "Response UserId " + userId);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response user" + new String(error.networkResponse.data));

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    public void sendGoalData(){
        String url = "http://165.227.67.248:8000/goals/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("goalName", lastInsertedGoal.getName());
        params.put("goalAmount", String.valueOf(lastInsertedGoal.getAmount()));
        params.put("createDate",lastInsertedGoal.getStartDate());
        params.put("completionDate", lastInsertedGoal.getEndDate());
        params.put("user", lastInsertedUser.getPhpId());
        Log.d(TAG, "userID: " + lastInsertedGoal.getPhpId());
        params.put("actualCompletionDate", "placeholder");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        int goalId = 0;
                        try {
                            goalId = (int) response.get("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        lastInsertedGoal.setPhpId(String.valueOf(goalId));
                        lastInsertedGoal.setSyncStatus(1);
                        goalCrud.updateGoal(lastInsertedGoal);
                        Log.d(TAG, "Response Goal" + goalId);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response Goal" + error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    public void sendExpenditureData(){
        String url = "http://165.227.67.248:8000/expenditure/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("user", lastInsertedUser.getPhpId());
        params.put("goal", lastInsertedGoal.getPhpId());
        params.put("transport", String.valueOf(expenditureCrud.addAllHealth()));
        params.put("savings", String.valueOf(expenditureCrud.addAllSavings(null)));
        params.put("otherExpenditures", String.valueOf(expenditureCrud.addAllOthers()));
        params.put("homeneeds", String.valueOf(expenditureCrud.addAllHomeneeds()));
        params.put("medical", "3000");
        params.put("education", "300");
        params.put("created", "23000");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        int expenditureId = 0;
                        try {
                            expenditureId = (int) response.get("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        expenditureCrud.insertPhpId(expenditureId);
                        expenditureCrud.updateSyncExpenditure();
                        Log.d(TAG, "expenditurephpId: "+ expenditureCrud.getPhpID());
                        Log.d(TAG, "expendituresync status: "+ expenditureCrud.getSyncStatus());
                        Log.d(TAG, "Response Exp" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response expenditure" + error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    public void sendIncomeData(){
        String url = "http://165.227.67.248:8000/income/";

        Map<String, String> params = new HashMap<String, String>();
        params.put("user", lastInsertedUser.getPhpId());
        params.put("salary", String.valueOf(incomeCrud.addAllSalary()));
        params.put("otherIncomes", String.valueOf(incomeCrud.addAllOthers()));
        params.put("created", "time.now");
        params.put("investment", "investment");
        params.put("loan", "loan");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        int incomeId = 0;
                        try {
                            incomeId = (int) response.get("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "incomeId: " + incomeId);
                        incomeCrud.insertPhpId(incomeId);
                        incomeCrud.updateSyncIncome();
                        Log.d(TAG, "incomeIddb: " + incomeCrud.getPhpID());
                        Log.d(TAG, "Response Income" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response Income Error" + error.toString());
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

    public void updateUserData(String userID) {

        String url = String.format("http://165.227.67.248:8000/userlist/%s/update/", userID);

        Map<String, String> params = new HashMap<String, String>();
        params.put("household", String.valueOf(lastInsertedUser.getHousehold()));
        params.put("sex", lastInsertedUser.getSex());
        params.put("age", String.valueOf(lastInsertedUser.getAge()));
        params.put("educationLevel", lastInsertedUser.getEducationlevel());
        params.put("nationality", lastInsertedUser.getNationality());
        params.put("points", String.valueOf(lastInsertedUser.getPoints()));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                url,
                new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        lastInsertedUser.setSyncStatus(1);
                        userCrud.updateUser(lastInsertedUser);
                        Log.d(TAG, "Response update User" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d(TAG, "Error.Response update user" + error.toString());
                    }
                }
        )
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }


    public void updateGoalData(){
        String url = "http://165.227.67.248/cashTimePhpDatabase/goal_update.php";
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
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("goalName", lastInsertedGoal.getName());
                params.put("goalAmount", String.valueOf(lastInsertedGoal.getAmount()));
                params.put("goalStartDate",lastInsertedGoal.getStartDate());
                params.put("goalEndDate", lastInsertedGoal.getEndDate());

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        queue.add(postRequest);
    }

    public void updateExpenditureData(){
        String url = "http://165.227.67.248/cashTimePhpDatabase/expenditure_update.php";
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
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("totalTransportExpenditure", String.valueOf(expenditureCrud.addAllTransport()));
                params.put("totalEducationExpenditure", String.valueOf(expenditureCrud.addAllEducation()));
                params.put("totalMedicalExpenditure", String.valueOf(expenditureCrud.addAllHealth()));
                params.put("totalSavingsExpenditure", String.valueOf(expenditureCrud.addAllSavings(null)));
                params.put("totalOthersExpenditure", String.valueOf(expenditureCrud.addAllOthers()));
                params.put("totalHomeneedsExpenditure", String.valueOf(expenditureCrud.addAllHomeneeds()));

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        queue.add(postRequest);
    }

    public void updateIncomeData(){
        String url = "http://165.227.67.248/cashTimePhpDatabase/income_update.php";
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
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("totalLoanIncome", String.valueOf(incomeCrud.addAllLoan()));
                Log.d(TAG, "totalLoanIncome: " + String.valueOf(incomeCrud.addAllLoan()));
                params.put("totalSalaryIncome", String.valueOf(incomeCrud.addAllSalary()));
                params.put("totalOthersIncome", String.valueOf(incomeCrud.addAllOthers()));
                Log.d(TAG, "totalOthersIncome: " + String.valueOf(incomeCrud.addAllOthers()));
                params.put("totalInvestmentIncome", String.valueOf(incomeCrud.addAllInvestment()));
                Log.d(TAG, "totalInvestmentIncome: " + String.valueOf(incomeCrud.addAllInvestment()));

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };
        queue.add(postRequest);
    }


}