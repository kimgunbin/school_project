package com.example.pocket.class_.board;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocket.R;
import com.example.pocket.class_.board.adapter.CommentAdapter;
import com.example.pocket.class_.board.adapter.CommentVO;
import com.example.pocket.class_.database.DbHelper;
import com.example.pocket.class_.database.NodePostJSON;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DetailActivity extends AppCompatActivity {


    // 사용할 컴포넌트 선언
    TextView title_tv, content_tv, date_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    ListView lv;
    ArrayList<CommentVO> data = new ArrayList<>();

    String total, result, boardSeq, id,comment;



    String Title = "";


    String Content = "";
    String Date = "";
    String Code = "";
    String[] list;
    Bitmap bitmap;
    ImageView img;


    DbHelper dbHelper = new DbHelper();

    JSONArray jsonArray;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();


// ListActivity 에서 넘긴 변수들을 받아줌

        Title = getIntent().getStringExtra("Title");
        Content = getIntent().getStringExtra("Con");
        Date = getIntent().getStringExtra("Date");
        boardSeq = getIntent().getStringExtra("Seq");
        comment = getIntent().getStringExtra("comment");

// 컴포넌트 초기화
        title_tv = findViewById(R.id.title_tv);
        content_tv = findViewById(R.id.content_tv);
        date_tv = findViewById(R.id.date_tv);

        title_tv.setText(Title);
        content_tv.setText(Content);
        date_tv.setText(Date);

//        comment_layout = findViewById(R.id.commm);
        comment_et = findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);

        Log.v("댓글", boardSeq);
        id = String.valueOf(pref.getString("id", "0"));
/*
        NodePostJSON np = new NodePostJSON();
        try {
            jsonArray = new JSONArray(np.execute("http://119.200.31.82:80/select", "SELECT  * FROM T_COMMENT WHERE BOARD_SEQ=" + boardSeq, "comment list").get().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        /*
        *     private String  seq;
                private String id;
                private String content;
                private String date;
        *
        * */
        Log.v("댓글",comment);
/*
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                String res = jsonArray.getJSONObject(i).getString("COMMENT_DATE").toString();
                String resa[] = res.split("T");
                data.add(new CommentVO(jsonArray.getJSONObject(i).getString("BOARD_SEQ").toString(),
                        jsonArray.getJSONObject(i).getString("MB_ID").toString(),
                        jsonArray.getJSONObject(i).getString("COMMENT_CONTENT").toString(),
                        resa[0].toString()
                ));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
*/
        lv = findViewById(R.id.commm);

        CommentAdapter adapter = new CommentAdapter(
                getApplicationContext(),
                R.layout.activity_custom_comment,
                data);

        lv.setAdapter(adapter);

        img = findViewById(R.id.imgg);

/*

        Thread uThread = new Thread() {
            @Override
            public void run() {
                try {
                    // 이미지 URL 경로
                    URL url = new URL("http://59.0.129.37:5000/static/img1.jpg");

                    // web에서 이미지를 가져와 ImageView에 저장할 Bitmap을 만든다.
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로부터 응답 수신
                    conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)

                    InputStream is = conn.getInputStream(); //inputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        uThread.start(); // 작업 Thread 실행

        try {
            //메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야 한다.
            //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리도록 한다.
            //join() 메서드는 InterruptedException을 발생시킨다.
            uThread.join();

            //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            //UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정
            img.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

*/
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total = boardSeq + "/" + id + "/" + comment_et.getText().toString();
                result = dbHelper.connectServer("http://210.183.87.95:5000/Comments", total);

                Log.d("r", result);


                Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                Toast.makeText(getApplicationContext(), "댓글 작성 성공", Toast.LENGTH_SHORT).show();

                startActivity(intent);
                finish();


            }
        });

    }
}