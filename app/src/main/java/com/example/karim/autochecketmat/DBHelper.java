package com.example.karim.autochecketmat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Karim on 30-12-16.
 */

public class DBHelper extends SQLiteOpenHelper {

    protected ArrayList<String> userss = new ArrayList<>();

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME= "users.db";
    private static final String TABLE_NAME= "users";
    private static final String COLUMN_ID= "id";
    private static final String COLUMN_ADMIN = "admin";
    private static final String COLUMN_USERNAME= "username";
    private static final String COLUMN_NAME="name";
    private static final String COLUMN_FRISTNAME = "fristname";
    private static final String COLUMN_PASSWORD= "password";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table users (id integer primary key not null ," +
            "username text not null , password text not null ,name text not null , fristname text not null)";

    public DBHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;

    }
    public void insertUser(Users u){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query ="select * from users";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put(COLUMN_ID,count);
        values.put(COLUMN_USERNAME,u.getUsername());
        values.put(COLUMN_FRISTNAME,u.getFristName());
        values.put(COLUMN_NAME,u.getName());
        values.put(COLUMN_PASSWORD,u.getPassword());

        db.insert(TABLE_NAME , null , values);
        db.close();
    }
    public String searchPass(String username){

        db = this.getReadableDatabase();
        String query = "select username, password from "+ TABLE_NAME;


        Cursor cursor = db.rawQuery(query,null);

        String a,b;
        b = "not found";
        if(cursor.moveToFirst()){
            do{

                a = cursor.getString(0);
                if(a.equals(username)) {
                    b = cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return b;
    }

    public ArrayList<String> findUsers(){



        db = this.getReadableDatabase();
        String query = "select username from "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);



        if (cursor.moveToFirst()){
            do{

              userss.add(cursor.getString(cursor.getColumnIndex("username")));

            }while (cursor.moveToNext());
        }

        return userss ;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS"+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
