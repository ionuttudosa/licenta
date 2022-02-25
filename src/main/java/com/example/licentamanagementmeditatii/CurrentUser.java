package com.example.licentamanagementmeditatii;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

public class CurrentUser {
    public static int id = 1;
    public static Date current_date;

    public static Date endMonth;

    public static void setCurrent_date(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH,1);
        current_date = new Date(c.getTimeInMillis());
        endMonth = getEndDate();
    }
    private static Date getEndDate() {
        Calendar cal = Calendar.getInstance();
        int res = cal.getActualMaximum(Calendar.DATE);
        LocalDate now = LocalDate.now();
        int dayOfMonth = now.getDayOfMonth();
        now = now.plusDays(res - dayOfMonth);
        return Date.valueOf(now);
    }


}
