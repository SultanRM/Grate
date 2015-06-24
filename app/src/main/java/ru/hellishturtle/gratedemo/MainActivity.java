package ru.hellishturtle.gratedemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import ru.hellishturtle.gratedemo.DateBase.Contract;
import ru.hellishturtle.gratedemo.DateBase.DBHelper;


public class MainActivity extends ActionBarActivity {


    MainFragment mainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
    }

    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, StatisticActivity.class);
        startActivity(intent);
    }

}
