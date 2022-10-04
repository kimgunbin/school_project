package com.example.pocket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocket.R;
import com.example.pocket.class_.database.DbHelper;

import javax.net.ssl.HttpsURLConnection;


public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtId, edtPw;
    TextView tvJoin;
    String result = "";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String[] list = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DbHelper dbHelper = new DbHelper();
        btnLogin = findViewById(R.id.btnLogin);
        edtId = findViewById(R.id.edtId);
        edtPw = findViewById(R.id.edtPw);
        tvJoin = findViewById(R.id.tvJoin);




        tvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                result = "";
                if(list!=null){
                    for(int i = 0 ; i<list.length;i++){
                        list[i] = "";
                    }
                }

                String postText = edtId.getText().toString() + "/" + edtPw.getText().toString();


                result = dbHelper.connectServer("http://210.183.87.95:5000/login", postText);
                Log.v("r", result);


                list = result.split(",");

                if (result.equals("로그인실패")) {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                } else if(list.length>2) {

                    Log.v("list", list[6].replace("'", ""));
                    String Check = list[6].replace("'", "").replace(" ", "");
                    if (Check.equals("0")) {

                        saved(list);
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity_T.class);
                        startActivity(intent);
                        finish();
                    }
                    if (Check.equals("1")) {
                        saved(list);
                        Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity_S.class);
                        startActivity(intent);
                        finish();

                    }



                }

            }

            private void saved(@NonNull String[] list) {
                String id = list[1].replace("'","").replace(" ","");
                String pw = list[2].replace("'","").replace(" ","");
                String name = list[3].replace("'","").replace(" ","");
                String scCode = list[4].replace("'","").replace(" ","");
                String tel = list[5].replace("'","").replace(" ","");
                String type = list[6].replace("'","").replace(" ","");

                pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                editor = pref.edit();


                editor.putString("id", id);
                editor.apply();


                editor.putString("pw", pw);
                editor.apply();


                editor.putString("name", name);
                editor.apply();


                editor.putString("scCode", scCode);
                editor.apply();


                editor.putString("tel", tel);
                editor.apply();


                editor.putString("type", type);
                editor.apply();

            }


        });



    }





}