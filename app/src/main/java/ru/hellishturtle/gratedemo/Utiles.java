package ru.hellishturtle.gratedemo;

import java.util.Calendar;


public class Utiles {

    public void onCreateView() {
        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        int currentYear = calendar.get(java.util.Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
    }
}
