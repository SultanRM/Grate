package ru.hellishturtle.gratedemo;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

//*
public class FileParser extends AsyncTask<Void, Void, String> {

    public String LOG_TAG = "my_log";
    final DecimalFormat format = new DecimalFormat();

    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String resultJson = "";
    String currentDate = "";


    JSONObject dataJsonObj = null;
    String date = "";
    String sourceCurrency = "";
    String rubRate = "";
    String eurRate = "";
    String gbpRate = "";

    @Override
    protected String doInBackground(Void... params) {

        try {
            String day = String.valueOf(MainFragment.day);
            if (day.length() < 2) {
                day = "0" + day;
            }
            String month = String.valueOf(MainFragment.month);
            if (month.length() < 2) {
                month = "0" + month;
            }

            currentDate = String.valueOf(MainFragment.year) + "-"
                    + month + "-"
                    + day;

            String webUrl = "http://api.fixer.io/" + currentDate + "?base=RUB&symbols=USD,EUR,GBP";
            URL url = new URL(webUrl);
            Log.d(LOG_TAG, currentDate);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "currentDate fale " + currentDate) ;
        }
        return resultJson;
    }

    @Override
    public void onPostExecute(String strJson) {
        super.onPostExecute(strJson);

        Log.d(LOG_TAG, strJson + " " + MainFragment.year + " " + MainFragment.month + " " + MainFragment.day);

        try {
            format.setDecimalSeparatorAlwaysShown(false);

            dataJsonObj = new JSONObject(strJson);
            JSONObject rateInfo = dataJsonObj.getJSONObject("rates");

            date = dataJsonObj.getString("date");
            sourceCurrency = dataJsonObj.getString("base");
            rubRate = rateInfo.getString("USD");
            eurRate = rateInfo.getString("EUR");
            gbpRate = rateInfo.getString("GBP");

            Float usd = Float.valueOf(rubRate);
            Float eur = Float.valueOf(eurRate);
            Float gbp = Float.valueOf(gbpRate);

            Float rubUsd = 1/usd;
            Float rubEur = 1/eur;
            Float rubGbp = 1/gbp;

            MainFragment.rateText1.setText(date);
            MainFragment.rateText2.setText(sourceCurrency);
            MainFragment.rateText3.setText(format.format(rubUsd));
            MainFragment.rateText4.setText(format.format(rubEur));
            MainFragment.rateText5.setText(format.format(rubGbp));

        }
        catch (JSONException e) {
            Log.d(LOG_TAG, "Error1");
            e.printStackTrace();
        }

    }


}