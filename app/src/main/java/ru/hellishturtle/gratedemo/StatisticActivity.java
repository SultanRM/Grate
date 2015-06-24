package ru.hellishturtle.gratedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;


public class StatisticActivity extends ActionBarActivity {

    StatisticFragment statFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistic_layout);

        statFragment = new StatisticFragment();
    }

    public void onClick(View view) {
        Intent intent = new Intent(StatisticActivity.this, MainActivity.class);
        startActivity(intent);

    }

}
