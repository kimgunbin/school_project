package com.example.pocket.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText edtId, edtPw;
    TextView tvJoin;
    String result = "";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String[] list = {};
    JSONArray jsonArray,jsonArray2;
    String title,content,file,content2;

    String id,pw,name,scCode,tel,type;
    private Socket client;
    private DataOutputStream dataOutput;
    private DataInputStream dataInput;
    private static String SERVER_IP = "";
    private static String CONNECT_MSG = "connect";
    private static String STOP_MSG = "stop";

    private static int BUF_SIZE = 100;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DbHelper dbHelper = new DbHelper();
        btnLogin = findViewById(R.id.btnLogin);
        edtId = findViewById(R.id.edtId);
        edtPw = findViewById(R.id.edtPw);
        tvJoin = findViewById(R.id.tvJoin);
        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();







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
                            "SELECT * FROM T_MEMBER WHERE MB_ID = '"+edtId.getText().toString()
                                    + "'AND MB_PW = '"+edtPw.getText().toString()+"'",
                            "login list").get().toString());

                        id = jsonArray.getJSONObject(0).getString("MB_ID").toString();
                        pw = jsonArray.getJSONObject(0).getString("MB_PW").toString();
                        name = jsonArray.getJSONObject(0).getString("MB_NAME").toString();
                        scCode = jsonArray.getJSONObject(0).getString("SC_CODE").toString();
                        tel = jsonArray.getJSONObject(0).getString("MB_PHONE").toString();
                        type = jsonArray.getJSONObject(0).getString("MB_USERTYPE").toString();


                    np = new NodePostJSON();
                    jsonArray = new JSONArray(np.execute("http://119.200.31.82:80/select",
                            "SELECT a.CC_NAME , b.CC_PATH , b.CCTV_DATE  FROM T_CCTV a LEFT OUTER JOIN T_CCTV_CONTENT b ON a.CC_SEQ = b.CC_SEQ where a.sc_code = '"+scCode+"' AND STATE_YNW = 'N' OR STATE_YNW = 'B'",
                            "cctv list").get().toString());
                    title = jsonArray.getJSONObject(0).getString("CC_NAME").toString();
                    content = jsonArray.getJSONObject(0).getString("CC_PATH").toString();
                    file = jsonArray.getJSONObject(0).getString("CCTV_DATE").toString();
                    content2 = title+","+content+","+file;
                    if(jsonArray.length()>1) {
                        for (int i = 1; i < jsonArray.length(); i++) {
                            title = jsonArray.getJSONObject(i).getString("CC_NAME").toString();
                            content = jsonArray.getJSONObject(i).getString("CC_PATH").toString();
                            file = jsonArray.getJSONObject(i).getString("CCTV_DATE").toString();
                            Log.v("title", title);
                            content2 += "/" + title + "," + content + "," + file;
                        }
                    }

                        editor.putString("content2", content2);
                        editor.apply();




                        if (type.equals("0")) {
                           Connect connect = new Connect();
                            connect.execute(CONNECT_MSG);



                            saved(id,pw,name,scCode,tel,type);
                            Intent intent = new Intent(LoginActivity.this, MainActivity_T.class);
                            startActivity(intent);


                        }
                        if (type.equals("1")) {

                            saved(id,pw,name,scCode,tel,type);
                            Intent intent = new Intent(LoginActivity.this, MainActivity_S.class);
                            startActivity(intent);



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


    private class Connect extends AsyncTask< String , String,Void > {
        private String output_message;
        private String input_message;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                client = new Socket("210.183.87.95", 7000);
                dataOutput = new DataOutputStream(client.getOutputStream());
                dataInput = new DataInputStream(client.getInputStream());
                output_message = strings[0];
                dataOutput.writeUTF(output_message);

            } catch (UnknownHostException e) {
                String str = e.getMessage().toString();
                Log.w("discnt", str + " 1");
            } catch (IOException e) {
                String str = e.getMessage().toString();
                Log.w("discnt", str + " 2");
            }

            while (true){
                try {
                    byte[] buf = new byte[BUF_SIZE];
                    int read_Byte  = dataInput.read(buf);
                    input_message = new String(buf, 0, read_Byte);
                    if (!input_message.equals(STOP_MSG)){
                        publishProgress(input_message);
                    }
                    else{
                        break;
                    }
                    Thread.sleep(2);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... params){


            createNotification(params[0]);

        }
    }
    private void createNotification(String context) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default");

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("알림");
        builder.setContentText(context);



        // 알림 표시
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("default", "SensorData", NotificationManager.IMPORTANCE_HIGH));
        }

        // id값은
        // 정의해야하는 각 알림의 고유한 int값
        notificationManager.notify(1, builder.build());
    }


}