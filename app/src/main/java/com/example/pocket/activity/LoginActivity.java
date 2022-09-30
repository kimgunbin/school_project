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

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtId, edtPw;
    TextView tvJoin;
    String result = "";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String [] list = {};
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

                String postText = edtId.getText().toString() + "/" + edtPw.getText().toString();


                String Check = connectServer("http://210.183.87.95:5000/login", postText);
            Log.v("a",Check);

                if (Check.equals("0")) {

                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity_T.class);
                    startActivity(intent);
                }
                if (Check.equals("1")) {



                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity_S.class);
                    startActivity(intent);

                }


                }

        });



    }


    public String connectServer(String url, String postText) {


        //보낼 주소
        String postUrl = url;

        Log.v("s1", result);

        //보낼 값

        MediaType mediaType = MediaType.parse("text/plain");
        //MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody postBody = RequestBody.create(mediaType, postText);

     String check = postRequest(postUrl, postBody);

return  check;
    }

    String postRequest(String postUrl, RequestBody postBody) {



        Log.v("s2", result);
        OkHttpClient client = new OkHttpClient();
        final String[] Check = {""};
        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();
                result = "Failed to Connect to Server";

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {





                result = response.body().string();
                Log.v("s3", result);
                list = result.split(",");
                Check[0] = list[6].replace("'", "").replace(" ", "");

                if (result.equals("로그인실패")) {
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();

                } else if (list.length > 2) {

                    Log.v("list", list[6].replace("'", ""));


                    String id = list[1].replace("'", "").replace(" ", "");
                    String pw = list[2].replace("'", "").replace(" ", "");
                    String name = list[3].replace("'", "").replace(" ", "");
                    String scCode = list[4].replace("'", "").replace(" ", "");
                    String tel = list[5].replace("'", "").replace(" ", "");
                    String type = list[6].replace("'", "").replace(" ", "");

                    pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                    editor = pref.edit();

                    editor.clear();
                    editor.commit();

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

            }
        });


        return Check[0];
    }




}

