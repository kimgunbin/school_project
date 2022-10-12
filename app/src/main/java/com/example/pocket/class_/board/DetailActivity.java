package com.example.pocket.class_.board;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    String total, result, boardSeq, id;


    String Title = "";


    String Content = "";
    String Date = "";
    String Code = "";
    String[] list;


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

        NodePostJSON np = new NodePostJSON();
        try {
            jsonArray = new JSONArray(np.execute("http://119.200.31.82:80/select", "SELECT  * FROM T_COMMENT WHERE BOARD_SEQ=" + boardSeq, "comment list").get().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
        *     private String  seq;
                private String id;
                private String content;
                private String date;
        *
        * */

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

        lv = findViewById(R.id.commm);

        CommentAdapter adapter = new CommentAdapter(
                getApplicationContext(),
                R.layout.activity_custom_comment,
                data);

        lv.setAdapter(adapter);


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