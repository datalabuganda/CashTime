package com.example.eq62roket.cashtime.Helper;

import java.text.DecimalFormat;
import java.util.UUID;

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

    public String getUUID(){
        return UUID.randomUUID().toString();
    }
}
