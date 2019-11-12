package com.ina.notebook;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class DairyList extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    ListView dairy_list;
    ArrayList<HashMap<String, String>> Dairy_list = new ArrayList<>();
    DBHelper myDBHelper;
    MyAdapter myAdapter;
    private  boolean isExit ;
    String TAG="TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_list);
        dairy_list = findViewById(R.id.dairy_list);
        Context mContext = DairyList.this;
        myDBHelper = new DBHelper(mContext, "my.db", null, 1);
        selectALL();

        Log.i(TAG,"输出列表________________________________________________________");
        for(int i=0;i<Dairy_list.size();i++){
            Log.i(TAG ,"第"+i+"条是：="+Dairy_list.get(i));
        }


         myAdapter = new MyAdapter(this, R.layout.list_item, Dairy_list);
        dairy_list.setAdapter(myAdapter);
        Log.i(TAG,"配置成功");



        dairy_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {//点击打开详情页面
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object itemAtPosition = dairy_list.getItemAtPosition(position);
                HashMap<String,String> map = (HashMap<String, String>) itemAtPosition;
                String news_detail = Dairy_list.get(position).get("id");//获取id，用于下个页面查询新闻内容

                Log.i(TAG,"onItemClick: 日记的id是：="+news_detail);


                Intent config = new Intent(DairyList.this, DairyContent.class);//本页面News传递给下一个页面Detail
                Bundle bdl = new Bundle();
                bdl.putString("id",news_detail);//存入id，用于下一个页面查询
                config.putExtras(bdl);//传递

//                startActivityForResult(config,20);
                startActivity(config);//该方法不需要接收反馈数据
                finish();
            }
        });//详情页面结束
        dairy_list.setOnItemLongClickListener(this);
    }


    public void selectALL(){
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM Dairy", new String[]{});
        //存在数据才返回true
        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));//编号
            String title = cursor.getString(cursor.getColumnIndex("title"));//标题
            String content = cursor.getString(cursor.getColumnIndex("content"));//内容
            String time = cursor.getString(cursor.getColumnIndex("time"));//时间
            HashMap<String,String> map  = new HashMap<>();
            Log.i(TAG,"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            Log.i(TAG,id);
            Log.i(TAG,title);
            map.put("id",id);
            map.put("title",title);
            map.put("content",content);
            map.put("time",time);
            Dairy_list.add(map);
        }
        cursor.close();
    }


    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").
                setMessage("请确认是否删除当前数据").
                setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG,"对话框事件处理--------");
                        String id = Dairy_list.get(position).get("id");//获取日记标号
                        myAdapter.remove(Dairy_list.get(position));
                        delete(id);//删除



                    }
                }).setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
    public void delete(String id){

        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        db.execSQL("DELETE FROM Dairy WHERE id = ?",
                new String[]{id});
    }

    public void Add(View view){
        Intent intent = new Intent(DairyList.this,AddDairy.class);//打开新页面

//         startActivityForResult(intent,2);
        startActivity(intent);//该方法不需要接收反馈数据
        finish();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event){//点击两次退出程序

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(isExit){
                this.finish();
            }
            else{
                Toast.makeText(this,"再按一次退出",Toast.LENGTH_SHORT).show();
                isExit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExit = false;
                    }
                },2000);
            }
        }
        return true;
    }//点击两次退出结束效果
}
