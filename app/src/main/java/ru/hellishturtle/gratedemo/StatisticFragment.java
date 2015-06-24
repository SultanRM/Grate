package ru.hellishturtle.gratedemo;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import ru.hellishturtle.gratedemo.DateBase.DBHelper;


public class StatisticFragment extends Fragment {

    ArrayList<String> rateList = new ArrayList<String>();
    DBHelper dbHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);

        ListView list = (ListView) view.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, rateList);
        list.setAdapter(adapter);

        PutToList();

            return view;
    }

    public void PutToList () {

        dbHelper = new DBHelper(getActivity());

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // делаем запрос всех данных из таблицы mytable, получаем Cursor


        Cursor c = db.query("mytable", null, null, null, null, null, "date DESC");



        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int dateColIndex = c.getColumnIndex("date");
            int usdColIndex = c.getColumnIndex("usd");
            int eurColIndex = c.getColumnIndex("eur");
            int gbpColIndex = c.getColumnIndex("gbp");

            do {
                // получаем значения по номерам столбцов и пишем все в лог

                String rate = "Date: " + c.getString(dateColIndex) +
                        "  usd: " + c.getString(usdColIndex) +
                        "  eur: " + c.getString(eurColIndex) +
                        "  gbp: " + c.getString(gbpColIndex);

                rateList.add(rate);

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());

        }
        c.close();

        // закрываем подключение к БД
        dbHelper.close();

    }

}
