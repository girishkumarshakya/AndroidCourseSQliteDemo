package in.lavit.sqlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    public static final String DB_NAME = "mydb";
    public static final int DB_VERSION = 1;

    public static final String COL_NAME = "emp_name";
    public static final String COL_DESIG = "emp_desig";
    public static final String TABLE_NAME = "employee";
    public static final String CREATE_TABLE = "create table "+TABLE_NAME+" ("+COL_NAME+" text,"+COL_DESIG+" text)";


    public MyDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
    }
}
