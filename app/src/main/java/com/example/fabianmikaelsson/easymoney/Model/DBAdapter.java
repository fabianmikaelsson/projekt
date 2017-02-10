package com.example.fabianmikaelsson.easymoney.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Public class to store, edit & delete entries.
 */
public class DBAdapter {

    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    public static final String KEY_ACCOUNT = "account";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_SUM = "totalSum";
    public static final String KEY_INCOME = "isIncome";
    public static final String KEY_DATE = "date";
    public static final String KEY_DESCRIPTION = "description";

    public static final int COL_ACCOUNT = 1;
    public static final int COL_CATEGORY = 2;
    public static final int COL_SUM = 3;
    public static final int COL_INCOME = 4;
    public static final int COL_DATE = 5;
    public static final int COL_DESCRIPTION = 6;


    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_ACCOUNT, KEY_CATEGORY, KEY_SUM, KEY_INCOME, KEY_DATE, KEY_DESCRIPTION};

    // DB info
    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "mainTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 5;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

                    + KEY_ACCOUNT + " text not null, "
                    + KEY_CATEGORY + " text not null, "
                    + KEY_SUM + " integer not null, "
                    + KEY_INCOME + " integer not null, "
                    + KEY_DATE + " text not null,"
                    + KEY_DESCRIPTION + " text not null"

                    + ");";

    // Context of application who uses this class.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(String account, String category, int totalSum, int isIncome, String date, String description) {
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ACCOUNT, account);
        initialValues.put(KEY_CATEGORY, category);
        initialValues.put(KEY_SUM, totalSum);
        initialValues.put(KEY_INCOME, isIncome);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_DESCRIPTION, description);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Delete a row from the database, by rowId
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    /**
     * Private class which handles database creation and upgrading.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}

