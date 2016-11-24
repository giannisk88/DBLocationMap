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
    public static final String col_2 = "LATITUDE";
    public static final String col_3 = "LONGITUDE";


    //The constructor of the class
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        //if it exists the same database i will delete it and i will create a new one
        db.execSQL("CREATE TABLE "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,LATITUDE TEXT,LONGITUDE TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //if it exists the same database i will delete it and i will create a new one
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

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


    public Cursor getAllData(){
        //here i'm gonna create an instance of sqlitedatabase
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public boolean updateData(String id, String lat, String lon){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(col_1,id);
        contentValues.put(col_2,lat);
        contentValues.put(col_3,lon);
        db.update(TABLE_NAME, contentValues, "ID=?",new String[]{id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID=?",new String[]{id});
    }

}
