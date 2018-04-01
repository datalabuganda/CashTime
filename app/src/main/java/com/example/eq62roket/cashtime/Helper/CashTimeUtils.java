package com.example.eq62roket.cashtime.Helper;

import java.text.DecimalFormat;

/**
 * Created by etwin on 4/1/18.
 */

public class CashTimeUtils {

    public CashTimeUtils(){
    }

    public String currencyFormatter(String amountToFormat){
        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");
        return decimalFormat.format(Integer.parseInt(amountToFormat));
    }
}
