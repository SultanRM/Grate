package ru.hellishturtle.gratedemo.DateBase;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


public class Contract {

    public static final String CONTENT_AUTHORITY = "ru.hellishturtle.provider.Grate";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RATES = "rates";

    public static final class RatesTable implements BaseColumns {

        public static Uri buildRatesUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RATES).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +CONTENT_AUTHORITY + "/" + PATH_RATES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RATES;

        public static final String TABLE_NAME = "rates";
        public static final String DATE = "date";
        public static final String SOUR = "sour";
        public static final String USD = "author";
        public static final String EUR = "eur";
        public static final String GBP = "gbp";
    }

    public static int getRateIdFromUri(Uri uri){
        return Integer.parseInt(uri.getPathSegments().get(1));
    }
}
