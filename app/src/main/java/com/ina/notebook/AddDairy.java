package com.ina.notebook;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

public class AddDairy extends AppCompatActivity {
    EditText NewTitle;
    EditText NewContent;
    TextView NewTime;
    String TAG="TAG";
    String createTime;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // 隐藏标题栏
        setContentView(R.layout.add_dairy);
        NewTitle = findViewById(R.id.new_title);
        NewContent = findViewById(R.id.new_content);
        NewTime = findViewById(R.id.new_time);

        long currentTime = System.currentTimeMillis();
        createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentTime);//20191102184736以当前时间为id
        NewTime.setText(createTime);


    }

    public void insert_new(){
        Context mContext = AddDairy.this;
        DBHelper myDBHelper = new DBHelper(mContext, "ina.db", null, 1);
        SQLiteDatabase db = myDBHelper.getWritableDatabase();

        String new_id = String.valueOf((int)((Math.random()*9+1)*1000));//随机四位数
        String new_title = String.valueOf(NewTitle.getText()).trim();
        String new_content = String.valueOf(NewContent.getText()).trim();
        String new_time = createTime.trim();//20191102184736以当前时间为id
        if(new_title.equals("")||new_title.length()==0){
            new_title="无标题";
        }
        Log.i(TAG,"new_id="+new_id);
        Log.i(TAG,"new_title="+new_title);
        Log.i(TAG,"new_content="+new_content);
        Log.i(TAG,"new_time="+new_time);
        db.execSQL("INSERT INTO Dairy(id,title,content,time) values(?,?,?,?)", new String[]{new_id,new_title,new_content,new_time});
        Log.i(TAG,"存入成功");
    }

    //重写onKeyDown方法,对按键(不一定是返回按键)监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下

            Intent intent = new Intent(AddDairy.this,DairyList.class);//打开新页面
            Log.i(TAG,"准备返回");
            insert_new();//记录
            Toast.makeText(this,"自动保存！",Toast.LENGTH_SHORT).show();
            Log.i(TAG,"自动保存");
            startActivity(intent);//该方法不需要接收反馈数据
            this.finish();
        }
        return true;
    }

}
