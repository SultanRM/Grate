package ru.hellishturtle.gratedemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class StatisticActivity extends ActionBarActivity {

    StatisticFragment statFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_layout);

        statFragment = new StatisticFragment();
    }


}
