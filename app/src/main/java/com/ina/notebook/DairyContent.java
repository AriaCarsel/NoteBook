package com.ina.notebook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;


public class DairyContent extends AppCompatActivity {
    EditText DairyTitle;
    EditText DairyContent;
    TextView DairyTime;
    DBHelper myDBHelper;
    String dairy_id;
    String title,content,time;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // 隐藏标题栏
        setContentView(R.layout.dairy_content);
        Context mContext = DairyContent.this;
        myDBHelper = new DBHelper(mContext, "my.db", null, 1);

        DairyTitle = findViewById(R.id.dairy_title);
        DairyContent = findViewById(R.id.dairy_content);
        DairyTime = findViewById(R.id.dairy_time);

        Intent intent = getIntent();
        Bundle bdl = intent.getExtras();
        dairy_id = bdl.getString("id");
        select(dairy_id);

        DairyTitle.setText(title);
        DairyContent.setText(content);
        DairyTime.setText("上次创建时间："+time);


    }
    public void select(String id){
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM Dairy WHERE id=?", new String[]{id});
        if(cursor.moveToFirst()) {
            title = cursor.getString(cursor.getColumnIndex("title"));
            content = cursor.getString(cursor.getColumnIndex("content"));
            time = cursor.getString(cursor.getColumnIndex("time"));
            Log.i("TAG","查找的标题、内容、上次创建时间为："+title+"   "+content+"   "+time);
        }
        cursor.close();

    }
    public void update(String id)
    {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        title = DairyTitle.getText().toString();
        content = DairyContent.getText().toString();
        long currentTime = System.currentTimeMillis();
        String timeNow = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime);
        db.execSQL("UPDATE Dairy SET title = ?,content = ?,time = ? WHERE id = ?",
                new String[]{title,content,timeNow,id});
    }
    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下

            Intent intent = new Intent(DairyContent.this,DairyList.class);//打开新页面
            Log.i("TAG","详情页面开始返回");
            update(dairy_id);//记录
            Toast.makeText(this,"修改成功！",Toast.LENGTH_SHORT).show();
            Log.i("TAG","修改成功");
            startActivity(intent);//该方法不需要接收反馈数据
            this.finish();
        }
        return true;
    }
    }