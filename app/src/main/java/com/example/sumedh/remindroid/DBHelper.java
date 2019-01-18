package com.example.sumedh.remindroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "sqlitedb";
    private static final int VERSION = 3;


    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql1 = "CREATE TABLE reminder(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, date TEXT, time TEXT)";
        String sql2 = "CREATE TABLE todo(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, date TEXT, time TEXT, checked INTEGER DEFAULT 0)";
        String sql3 = "CREATE TABLE bday(id INTEGER PRIMARY KEY AUTOINCREMENT, fname TEXT NOT NULL, lname TEXT, date TEXT)";
        sqLiteDatabase.execSQL(sql1);
        sqLiteDatabase.execSQL(sql2);
        sqLiteDatabase.execSQL(sql3);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE if exists reminder");
        sqLiteDatabase.execSQL("DROP TABLE if exists todo");
        sqLiteDatabase.execSQL("DROP TABLE if exists bday");
        onCreate(sqLiteDatabase);

    }

    public  void addReminder(String title, String date, String time)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",title);
        contentValues.put("time",time);
        contentValues.put("date",date);
        db.insert("reminder",null,contentValues);
        db.close();
    }

    public  void addTodo(String title, String date, String time,int checked)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",title);
        contentValues.put("time",time);
        contentValues.put("date",date);
        contentValues.put("checked",checked);
        db.insert("todo",null,contentValues);
        db.close();
    }

    public void addBday(String fname, String lname, String date) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fname",fname);
        contentValues.put("lname",lname);
        contentValues.put("date",date);
        db.insert("bday",null,contentValues);
        db.close();

    }

    public List<DataModel> getReminders() {

        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from reminder;",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext())
        {
            dataModel= new DataModel();
            String title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            dataModel.setRemTitle(title);
            dataModel.setRemDate(date);
            dataModel.setRemTime(time);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }

    public List<DataModel> getTodo() {
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from todo;",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext())
        {
            dataModel= new DataModel();
            String title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int checked = cursor.getInt(cursor.getColumnIndexOrThrow("checked"));
            dataModel.setTodoTitle(title);
            dataModel.setTodoDate(date);
            dataModel.setTodoTime(time);
            dataModel.setTodoChecked((checked == 1)? 1 : 0
            );
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }

    public List<DataModel> getBday() {

        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from bday;",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext())
        {
            dataModel= new DataModel();
            String fname = cursor.getString(cursor.getColumnIndexOrThrow("fname"));
            String lname = cursor.getString(cursor.getColumnIndexOrThrow("lname"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            dataModel.setBdayfname(fname);
            dataModel.setBdaylname(lname);
            dataModel.setBdayDate(date);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;

    }



    public List<DataModel> getRemindersToday(String formattedDate) {
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from reminder where date ='"+formattedDate+"';",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext())
        {
            dataModel= new DataModel();
            String title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            dataModel.setRemTitle(title);
            dataModel.setRemDate(date);
            dataModel.setRemTime(time);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;

    }

    public List<DataModel> getTodoToday(String formattedDate) {
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from todo where date = '"+formattedDate+"';",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext())
        {
            dataModel= new DataModel();
            String title = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            int checked = cursor.getInt(cursor.getColumnIndexOrThrow("checked"));
            dataModel.setTodoTitle(title);
            dataModel.setTodoDate(date);
            dataModel.setTodoTime(time);
            dataModel.setTodoChecked((checked == 1)? 1 : 0
            );
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }

    public List<DataModel> getBdayToday(String formattedDate) {
        List<DataModel> data=new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from bday where date = '"+formattedDate+"';",null);
        StringBuffer stringBuffer = new StringBuffer();
        DataModel dataModel = null;
        while (cursor.moveToNext())
        {
            dataModel= new DataModel();
            String fname = cursor.getString(cursor.getColumnIndexOrThrow("fname"));
            String lname = cursor.getString(cursor.getColumnIndexOrThrow("lname"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            dataModel.setBdayfname(fname);
            dataModel.setBdaylname(lname);
            dataModel.setBdayDate(date);
            stringBuffer.append(dataModel);
            data.add(dataModel);
        }
        return data;
    }
}
