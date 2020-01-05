package com.example.entreprisemanager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TabledbHelper extends SQLiteOpenHelper {


    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db.db";
    public static final String TABLE_Name = "User_Table";
    public static final String COl_ID = "ID";
    public static final String COl_FullName = "FullName";
    public static final String COl_Token = "Token";
    public static final String COl_Email = "Email";
    public static final String COl_User_Type = "UserType";


    private static final String SQL_CREATE_ENTRIES = "create table " + TABLE_Name +" (ID TEXT PRIMARY KEY  ,FullName TEXT ,Email TEXT , Token TEXT , UserType TEXT  )" ;
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_Name ;


    public TabledbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }



    public   boolean insert(String id , String fullname  , String email ,String token , String user_type) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COl_ID , id); //0
        contentValues.put(COl_FullName,fullname);//1
        contentValues.put(COl_Email,email); //2
        contentValues.put(COl_Token,token);//3
        contentValues.put(COl_User_Type,user_type);//3


        long result = db.insert(TABLE_Name,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }

    public void deleteData () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete  from "+ TABLE_Name);
    }
    public boolean isEmpty(){

        SQLiteDatabase database = this.getReadableDatabase();
        int NoOfRows = (int) DatabaseUtils.queryNumEntries(database,TABLE_Name);
        if (NoOfRows == 0){
            return true;
        }else {
            return false;
        }
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_Name,null);
        return res;
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
