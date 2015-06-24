package ru.hellishturtle.gratedemo.DateBase;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


public class RatesContentProvider extends ContentProvider {

    static final int URI_RATES = 1;
    static final int URI_RATES_ID = 2;

    private static final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
    private static final String selectionQuery = Contract.RatesTable.TABLE_NAME + "." + Contract.RatesTable._ID + " = ?";

    private Cursor getRateById(Uri uri,String[] projection, String sortOrder){
        int rateId = Contract.getRateIdFromUri(uri);
        queryBuilder.setTables(Contract.RatesTable.TABLE_NAME);

        return queryBuilder.query(mDatabaseHelper.getReadableDatabase(),
                projection,
                selectionQuery,
                new String[]{Integer.toString(rateId)},
                null,
                null,
                sortOrder
        );
    }

    static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Contract.CONTENT_AUTHORITY;

        matcher.addURI(authority, Contract.PATH_RATES, URI_RATES);
        matcher.addURI(authority, Contract.PATH_RATES +"/#", URI_RATES_ID);
        return matcher;
    }

    private static final UriMatcher uriMatcher = buildUriMatcher();
    private DBHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DBHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor returnedCursor;
        switch (uriMatcher.match(uri)) {

            case URI_RATES:
                returnedCursor = mDatabaseHelper.getReadableDatabase().query(
                        Contract.RatesTable.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs, null,
                        null, sortOrder
                );
                break;

            case URI_RATES_ID:
                returnedCursor = getRateById(uri, projection, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        returnedCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return returnedCursor;
    }

    @Override
    public String getType(Uri uri) {
        final int match = uriMatcher.match(uri);

        switch (match){
            case URI_RATES:
                return Contract.RatesTable.CONTENT_TYPE;
            case URI_RATES_ID:
                return Contract.RatesTable.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri resultUri;

        switch (match){
            case URI_RATES:
                long id = db.insert(Contract.RatesTable.TABLE_NAME,null,values);
                if (id > 0) {
                    resultUri = Contract.RatesTable.buildRatesUri(id);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            default:
                throw new android.database.SQLException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return resultUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        int rowDeleted;

        switch (match){
            case URI_RATES_ID:
                String id = uri.getLastPathSegment();
                selection = Contract.RatesTable._ID + " = " + id;
                rowDeleted = db.delete(Contract.RatesTable.TABLE_NAME,selection,selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        final int match =uriMatcher.match(uri);
        int rowsUpdated;
        switch (match){
            case URI_RATES_ID:
                String id = uri.getLastPathSegment();
                selection = Contract.RatesTable._ID + " = " + id;
                rowsUpdated = db.update(Contract.RatesTable.TABLE_NAME, values,selection,selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
}
