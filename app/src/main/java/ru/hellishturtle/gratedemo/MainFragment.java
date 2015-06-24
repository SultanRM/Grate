package ru.hellishturtle.gratedemo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.Calendar;

import ru.hellishturtle.gratedemo.DateBase.DBHelper;


public class MainFragment extends Fragment{

    public MainFragment() {
    }

    final String LOG_TAG = "myLogs";

    public static int year;
    public static int month;
    public static int day;

    static TextView rateText1;
    static TextView rateText2;
    static TextView rateText3;
    static TextView rateText4;
    static TextView rateText5;

    Button btnAdd, btnClear, button;

    DBHelper dbHelper;
    MainActivity main;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        java.util.Calendar calendar = java.util.Calendar.getInstance(java.util.TimeZone.getDefault(), java.util.Locale.getDefault());
        calendar.setTime(new java.util.Date());
        int currentYear = calendar.get(java.util.Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        year = currentYear;
        month = currentMonth;
        day = currentDay;


        dbHelper = new DBHelper(getActivity());

        new FileParser().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        btnAdd = (Button) view.findViewById(R.id.btnAdd);
       // btnRead = (Button) view.findViewById(R.id.btnRead);
        btnClear = (Button) view.findViewById(R.id.btnClear);
        button = (Button) view.findViewById(R.id.button);

        rateText1 = (TextView) view.findViewById(R.id.rate_text1);
        rateText2 = (TextView) view.findViewById(R.id.rate_text2);
        rateText3 = (TextView) view.findViewById(R.id.rate_text3);
        rateText4 = (TextView) view.findViewById(R.id.rate_text4);
        rateText5 = (TextView) view.findViewById(R.id.rate_text5);

      /** btnRead.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), StatisticActivity.class);
                startActivity(intent);

            }
        });*/

        btnAdd.setOnClickListener(new View.OnClickListener()
        {   ContentValues cv = new ContentValues();



            @Override
            public void onClick(View v)
            {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                main = new MainActivity();

                String date = rateText1.getText().toString();
                String sour = rateText2.getText().toString();
                String usd = rateText3.getText().toString();
                String eur = rateText4.getText().toString();
                String gbp = rateText5.getText().toString();

                Log.d(LOG_TAG, "--- Insert in mytable: ---");
                // подготовим данные для вставки в виде пар: наименование столбца - значение

                cv.put("date", date);
                cv.put("sour", sour);
                cv.put("usd", usd);
                cv.put("eur", eur);
                cv.put("gbp", gbp);
                // вставляем запись и получаем ее ID
                long rowID = db.insert("mytable", null, cv);
                Log.d(LOG_TAG, "row inserted, ID = " + rowID);

                new FileParser().execute();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            SQLiteDatabase db = dbHelper.getWritableDatabase();


            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "--- Clear mytable: ---");
                // удаляем все записи
                int clearCount = db.delete("mytable", null, null);

                Log.d(LOG_TAG, "deleted rows count = " + clearCount);

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment picker = new DatePickerFragment();
                picker.show(getFragmentManager(), "datePicker");


            }
        });

        return view;
    }

    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        final String LOG_TAG = "myLogs";


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
// Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int myYear = c.get(Calendar.YEAR);
            int myMonth = c.get(Calendar.MONTH) + 1;
            int myDay = c.get(Calendar.DAY_OF_MONTH);

            Log.d(LOG_TAG, "date" + myYear + " " + myMonth + " " + myDay);

// Create a new instance of DatePickerDialog and return it

            return new DatePickerDialog(getActivity(), this, myYear, myMonth, myDay);


        }

        @Override
        public void onDateSet(DatePicker view, int myYear, int myMonth, int myDay) {

            MainFragment.year = myYear;
            MainFragment.month = myMonth;
            MainFragment.day = myDay;
            new FileParser().execute();
        }
    }



}
