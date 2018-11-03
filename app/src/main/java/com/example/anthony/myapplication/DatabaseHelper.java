package com.example.anthony.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users.db";
    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_UNAME = "uname";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASS = "pass";
    //SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table "+TABLE_NAME+"("+COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT not null, "+
            COLUMN_NAME+" text not null ,"+COLUMN_UNAME +" text not null ,"+COLUMN_PASS+" text not null)";

    private String DROP_USERTABLE= "DROP TABLE IF EXISTS "+ TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        //this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_USERTABLE);
        this.onCreate(db);
    }
    public void insertUser(User u){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, u.getName());
        values.put(COLUMN_UNAME, u.getUname());
        values.put(COLUMN_PASS, u.getPass());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
   /*search method for finding username and password
   * checks for matching username then checks next column
   * for password repeats for each column until a match is found
   * could probably improve this by pointing it directly at the second column somehow
   * */
    public String searchPass(String uname){

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select uname, pass from "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String a,pass;
        pass="not found";
        if(cursor.moveToFirst()){
            do {
                a = cursor.getString(0);

                if (a.equals(uname)){
                    pass = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }

        return pass;
    }


}
