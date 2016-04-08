package com.example.marko.viknitaksi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marko on 4/4/2016.
 */
public class DBHandler extends SQLiteOpenHelper{


    // Database Version
    private static final int DATABASE_VERSION = 3;
    // Database Name
    private static final String DATABASE_NAME = "taxi_db";
    // Contacts table name
    public static final String TABLE_INFO = "info";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_GRAD = "grad";
    private static final String KEY_IME = "ime";
    private static final String KEY_BROJ = "broj";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_INFO + "("
                + KEY_ID + " TEXT NOT NULL," + KEY_GRAD + " TEXT NOT NULL,"
                + KEY_IME + " TEXT NOT NULL," + KEY_BROJ + " TEXT NOT NULL);";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFO);
// Creating tables again
        onCreate(db);
    }

    // Adding new shop
    public void addInfo(Info info) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //info.setGrad("Прилеп");

        values.put(KEY_ID, info.getId());
        values.put(KEY_GRAD, info.getGrad());
       // info.setIme("Де-ни");
        values.put(KEY_IME, info.getIme());
       // info.setBroj("1595");
        values.put(KEY_BROJ, info.getBroj());
// Inserting Row
        db.insert(TABLE_INFO, null, values);
        db.close(); // Closing database connection
    }

    // Getting one shop
    public Info getInfo(String grad) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INFO, new String[] { KEY_ID,
                        KEY_GRAD, KEY_IME, KEY_BROJ }, KEY_IME + "=?",
                new String[] { String.valueOf(grad) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
       // String info = cursor.getString(3);
       Info info = new Info(cursor.getString(0),cursor.getString(1), cursor.getString(2),cursor.getString(3));
// return shop
        return info;
    }

    public ArrayList<String> getBrojoj(String grad) {
        ArrayList<String> br = new ArrayList<>();
    // Select All Query
        String selectQuery = "SELECT broj FROM " + TABLE_INFO + " WHERE grad='"+ grad +"';";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
    // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String broj = cursor.getString(cursor.getColumnIndex("broj"));
                br.add(broj);
            } while (cursor.moveToNext());
        }
    // return contact list
        return br;
    }
}
