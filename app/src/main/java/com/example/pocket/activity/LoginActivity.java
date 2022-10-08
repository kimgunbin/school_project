package com.example.pocket.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocket.R;
import com.example.pocket.class_.database.DbHelper;
import com.example.pocket.class_.database.NodePostJSON;
import com.example.pocket.class_.teacher.TeacherVO;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtId, edtPw;
    TextView tvJoin;
    String result = "";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String[] list = {};
    JSONArray jsonArray;
    String id,pw,name,scCode,tel,type;
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



                try {
                    NodePostJSON np = new NodePostJSON();
                    jsonArray = new JSONArray(np.execute("http://119.200.31.82:80/select",
                            "SELECT * FROM T_MEMBER WHERE MB_ID = '"+edtId.getText().toString()+"' AND MB_PW = '"+edtPw.getText().toString()+"'",
                            "login list").get().toString());

                        id = jsonArray.getJSONObject(0).getString("MB_ID").toString();
                        pw = jsonArray.getJSONObject(0).getString("MB_PW").toString();
                        name = jsonArray.getJSONObject(0).getString("MB_NAME").toString();
                        scCode = jsonArray.getJSONObject(0).getString("SC_CODE").toString();
                        tel = jsonArray.getJSONObject(0).getString("MB_PHONE").toString();
                        type = jsonArray.getJSONObject(0).getString("MB_USERTYPE").toString();

                        if (type.equals("0")) {

                            saved(id,pw,name,scCode,tel,type);
                            Intent intent = new Intent(LoginActivity.this, MainActivity_T.class);
                            startActivity(intent);
                            finish();

                        }
                        if (type.equals("1")) {
                            saved(id,pw,name,scCode,tel,type);
                            Intent intent = new Intent(LoginActivity.this, MainActivity_S.class);
                            startActivity(intent);
                            finish();


                        }



                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }








            }

            private void saved(String id ,String pw ,String name
                    ,String scCode ,String tel ,String type  ) {


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