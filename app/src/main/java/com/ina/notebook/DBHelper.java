package com.ina.notebook;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "my.db", null, 1);
    }
    @Override
    //数据库第一次创建时被调用
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Dairy(" +
                "id VARCHAR(20) PRIMARY KEY  ," +   //id为当前日期加创建时间
                "title VARCHAR(100)," +       //标题
                "content VARCHAR(200)," +         //详细内容
                "time VARCHARF(50))");        //创建时间
        Log.i("TAG","database is finished!");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}