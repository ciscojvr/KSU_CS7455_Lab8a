/*

Name: Francisco Ozuna Diaz
Assignment: CS 7455 Lab 8
Lab Date: Due July 19, 2020 at 11:59 PM

 */

package com.example.lab8a;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

public class StudentListDBAdapter {

    private static final String TAG = StudentListDBAdapter.class.getSimpleName();
    public static final String DATABASE_NAME = "Student.db";
    public static final int DB_VERSION = 2;

    public static final String TABLE_NAME = "student_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "GRADE";

    public static String CREATE_TABLE_STUDENTS = "CREATE TABLE "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY, "+COL_2+" TEXT NOT NULL, "+
    COL_3+ " TEXT )";

    private Context context;
    private SQLiteDatabase sqLiteDatabase;
    private static StudentListDBAdapter studentListDBAdapterInstance;

    private StudentListDBAdapter(Context context) {
        this.context = context;
        sqLiteDatabase = new StudentListDBHelper(this.context, DATABASE_NAME, null, DB_VERSION).getWritableDatabase();
    }

    public static StudentListDBAdapter getStudentListDBAdapterInstance(Context context) {
        if(studentListDBAdapterInstance == null) {
            studentListDBAdapterInstance = new StudentListDBAdapter(context);
        }
        return studentListDBAdapterInstance;
    }

    //Will be used in the content provider
    public Cursor getCursorsForAllToDos(){
        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,new String[]{COL_1,COL_2, COL_3},null,null,null,null,null,null);
        return cursor;
    }

    //Will be used in the content provider
    public Cursor getCount(){
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT COUNT(*) FROM "+TABLE_NAME,null);
        return cursor;
    }

    //insert method
    public boolean insert(String studentName, String studentGrade){
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,studentName);
        contentValues.put(COL_3,studentGrade);

        return sqLiteDatabase.insert(TABLE_NAME,null,contentValues)>0;
    }

    //Will be used in the content provider
    public long insert(ContentValues contentValues){
        return sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
    }

    public String getAllStudents(){
        StringBuffer buffer = new StringBuffer();

        Cursor cursor=sqLiteDatabase.query(TABLE_NAME,new String[]{COL_1,COL_2, COL_3},null,null,null,null,null,null);

        if(cursor!=null &cursor.getCount()>0){
            while(cursor.moveToNext()){
                buffer.append("Id : " + cursor.getString(0) + ", ");
                buffer.append("Name: " + cursor.getString(1) + ", ");
                buffer.append("Grade: " + cursor.getString(2) + "\n\n");
            }
        }
        cursor.close();
        return buffer.toString();
    }

    private static class StudentListDBHelper extends SQLiteOpenHelper {

        public StudentListDBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int dbVersion){
            super(context,databaseName,factory,dbVersion);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
            Log.i(TAG, "Inside onConfigure");
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(CREATE_TABLE_STUDENTS);
            Log.i(TAG, "Inside onCreate.");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
            Log.i(TAG, "Inside onUpgrade");
        }
    }
}
