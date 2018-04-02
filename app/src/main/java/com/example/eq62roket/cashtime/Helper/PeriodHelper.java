package com.example.eq62roket.cashtime.Helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by etwin on 3/21/18.
 */

public class PeriodHelper {
    String daily, weekly, monthly;
    Calendar mCalendar = Calendar.getInstance();
    Date today = new Date();
    SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public String getDailyDate(){
        // returns date one day from today
        mCalendar.setTime(today);
        mCalendar.add(Calendar.DATE, 1);
        String dateTomorrow = mSimpleDateFormat.format(mCalendar.getTime());
        return dateTomorrow;
    }

    public String getWeeklyDate(){
        // returns date one week from today
        mCalendar.setTime(today);
        mCalendar.add(Calendar.DATE, 7);
        String dateNextWeek = mSimpleDateFormat.format(mCalendar.getTime());
        return dateNextWeek;
    }

    public String getMonthlyDate(){
        // return date one month from today
        mCalendar.setTime(today);
        mCalendar.add(Calendar.DATE, 30);
        String dateNextMonth = mSimpleDateFormat.format(mCalendar.getTime());
        return dateNextMonth;
    }

    public String getDateToday(){
        Date today = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        String dateToday = simpleDateFormat.format(today);
        return dateToday;
    }


}
