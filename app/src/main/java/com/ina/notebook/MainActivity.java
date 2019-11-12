package com.ina.notebook;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


    }
    public void  onClick(View view){
        EditText username= findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        String user = username.getText().toString();
        String pwd = password.getText().toString();
        if(user.equals("username")&&pwd.equals("123456")){
            Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
            //跳转
            Intent intent;
            intent = new Intent(MainActivity.this,DairyList.class);//打开新页面

            startActivity(intent);//该方法不需要接收反馈数据
            this.finish();
        }
        else {
            Toast.makeText(this,"账号或密码错误",Toast.LENGTH_SHORT).show();
        }
    }
}
