package com.example.eq62roket.cashtime.Helper;

import android.content.Context;
import android.util.Log;

import com.example.eq62roket.cashtime.Models.ExpenditureCategories;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eq62roket on 4/1/18.
 */

public class ParseExpenditureCategoryHelper {
    public interface OnReturnedCategoryListener{
        void onResponse(List<ExpenditureCategories> expenditureCategoriesList);
        void onFailure(String error);
    }

    private static String TAG = "ParseExpenditureCategoryHelper";
    private final List<ExpenditureCategories> categoriesList = new ArrayList<>();

    private Context mContext;
    private ParseExpenditureCategoryHelper.OnReturnedCategoryListener mOnReturnedCategoryListener;

    public ParseExpenditureCategoryHelper(Context context){
        mContext = context;
    }

    public void saveCategoriesToParseDb(ExpenditureCategories expenditureCategories){
        ExpenditureCategories newExpenditureCategory = new ExpenditureCategories();
        newExpenditureCategory.put("categoryName", expenditureCategories.getName());
        newExpenditureCategory.put("createdById", expenditureCategories.getUserId());
        newExpenditureCategory.saveInBackground();

    }


    public void getCategoriesFromParseDb(final ParseExpenditureCategoryHelper.OnReturnedCategoryListener onReturnedCategoryListener){
        ParseQuery<ExpenditureCategories> categoriesParseQuery = ParseQuery.getQuery("Categories");
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        categoriesParseQuery.addDescendingOrder("updatedAt");
        categoriesParseQuery.whereEqualTo("createdById", currentUserId);
        categoriesParseQuery.findInBackground(new FindCallback<ExpenditureCategories>() {
            @Override
            public void done(List<ExpenditureCategories> parseCategories, ParseException e) {
                if (e == null){
                    for (ExpenditureCategories retrievedCategories: parseCategories){
                        ExpenditureCategories newCategory = new ExpenditureCategories();
                        newCategory.setName(retrievedCategories.get("categoryName").toString());
                        newCategory.setParseId(retrievedCategories.getObjectId());

                        categoriesList.add(newCategory);
                    }
                    if (onReturnedCategoryListener != null){
                        onReturnedCategoryListener.onResponse(categoriesList);
                    }
                }else {
                    onReturnedCategoryListener.onFailure(e.getMessage());
                }
            }
        });

    }

    public void updateCategoryInParseDb(final ExpenditureCategories categoryToUpdate){
        ParseQuery<ExpenditureCategories> categoryQuery = ParseQuery.getQuery("Categories");
        categoryQuery.getInBackground(categoryToUpdate.getParseId(), new GetCallback<ExpenditureCategories>() {
            @Override
            public void done(ExpenditureCategories category, ParseException e) {
                if (e == null) {
                    category.put("categoryName", categoryToUpdate.getName());
                    category.saveInBackground();

                }else {
                    Log.d(TAG, "Error: " + e.getMessage());
                }
            }
        });
    }

    public void deleteCategoryFromParseDb(ExpenditureCategories categoryToDelete){
        ParseQuery<ExpenditureCategories> categoryQuery = ParseQuery.getQuery("Categories");
        categoryQuery.getInBackground(categoryToDelete.getParseId(), new GetCallback<ExpenditureCategories>() {
            @Override
            public void done(ExpenditureCategories category, ParseException e) {
                if (e == null) {
                    Log.d(TAG, "Should delete now: ");
                    category.deleteInBackground();
                }else {
                    Log.d(TAG, "Error Occured: " + e.getMessage());
                }
            }
        });
    }

    public void totalGroupExpenditureByCategory(){
        final ParseQuery categoriesQuery = ParseQuery.getQuery("GroupExpenditure");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Categories");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> objects, ParseException e) {
                for (ParseObject p: objects){
                    String categories = p.getString("categoryName");
                    String[] categoriesList = {categories};
                    Log.d("Categories", "Categories " + categoriesList);
                    categoriesQuery.whereContainedIn("groupExpenditureCategory", Arrays.asList(categoriesList));
                    categoriesQuery.findInBackground(new FindCallback<ParseObject>() {
                        int sum;
                        @Override
                        public void done(List<ParseObject> amounts, ParseException e) {
                            for (ParseObject p: amounts){
                                sum += Integer.valueOf(p.getString("groupExpenditureAmount"));
                                final String categories = p.getString("groupExpenditureCategory");
                                Log.d(TAG, "sum" + sum);
                                Log.d(TAG, "categories" + categories);

                                Map<String, Integer> mapCategories = new HashMap<String, Integer>();
                                mapCategories.put(categories,sum);
                                Log.d(TAG, "mapCategories: " + mapCategories);


                            }

                        }

                    });
                }
            }
        });

    }
}
