package com.example.dblocationmap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Giannis on 22/11/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper{
    //database name
    public static final String DATABASE_NAME = "Spot.db";
    //table name
    public static final String TABLE_NAME = "spot_table";

    //Columns
    public static final String col_1 = "ID";
    // old public static final String col_2 = "EDITTEXT";
    //new start
    public static final String col_2 = "LATITUDE";
    public static final String col_3 = "LONGITUDE";
    //new end

    //The constructor of the class
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //old start
    //@Override
    //public void onCreate(SQLiteDatabase db) {
        //whenever the oncreate is called i will create a table with a query
    //    db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,EDITTEXT TEXT)");
    //}
    //old end
    //new start
    @Override
    public void onCreate(SQLiteDatabase db){
        //if it exists the same database i will delete it and i will create a new one
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,LATITUDE TEXT,LONGITUDE TEXT)");
    }
    //new end

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //if it exists the same database i will delete it and i will create a new one
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    //old start
    /**
    public boolean insertData(String editText){
        //here i'm gonna create an instance of sqlitedatabase
        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,editText);
        //if the data doesn't inserted to the table then the insert method returns -1
        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == 1)
            return false;
        else
            return true;

    }*/
    //old end
    //new start
    public boolean insertData(String lat, String lon){
        //here i'm gonna create an instance of sqlitedatabase
        SQLiteDatabase db = this.getWritableDatabase();



        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,lat);
        contentValues.put(col_3,lon);
        //if the data doesn't inserted to the table then the insert method returns -1
        long result = db.insert(TABLE_NAME,null,contentValues);

        if (result == 1)
            return false;
        else
            return true;
    }
    //new end

    public Cursor getAllData(){
        //here i'm gonna create an instance of sqlitedatabase
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

}
