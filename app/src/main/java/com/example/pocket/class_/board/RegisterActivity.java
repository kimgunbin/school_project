package com.example.pocket.class_.board;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocket.R;
import com.example.pocket.activity.MainActivity_S;
import com.example.pocket.activity.MainActivity_T;
import com.example.pocket.class_.database.DbHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText title_et, content_et;
    Button reg_button;
    DbHelper dbHelper = new DbHelper();
    String total,result,id,sc,type;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        title_et = findViewById(R.id.title_et);
        content_et = findViewById(R.id.content_et);
        reg_button = findViewById(R.id.reg_button);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();



        id = String.valueOf(pref.getString("id","0"));
        sc =  String.valueOf(pref.getString("scCode","0"));
        type = String.valueOf(pref.getString("type","0"));
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = title_et.getText().toString()+"/"+content_et.getText().toString()+"/"+id+"/"+sc;
                result = dbHelper.connectServer("http://210.183.87.95:5000/register",total);
                Log.v("r", result);
                Toast.makeText(getApplicationContext(), "게시글 생성 성공", Toast.LENGTH_SHORT).show();
                if(type.equals("0")){
                    Intent intent = new Intent(RegisterActivity.this, MainActivity_T.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(RegisterActivity.this, MainActivity_S.class);
                    startActivity(intent);
                }

            }
        });


    }

}