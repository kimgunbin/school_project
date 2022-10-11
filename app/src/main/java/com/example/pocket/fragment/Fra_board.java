package com.example.pocket.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pocket.R;
import com.example.pocket.class_.board.adapter.BoardAdapter;
import com.example.pocket.class_.board.adapter.BoardVO;
import com.example.pocket.class_.cctv.CctvAdapter;
import com.example.pocket.class_.cctv.CctvVO;
import com.example.pocket.class_.database.DbHelper;
import com.example.pocket.class_.board.DetailActivity;
import com.example.pocket.class_.board.RegisterActivity;
import com.example.pocket.class_.database.NodePostJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

// 1) server에 database를 구축해놓고 volley로 통신
// : 어플을 삭제해도 데이터가 유지

// 2)firebase
// : 실시간으로 데이터를 전송/저장 --> 카톡

// 3)내장 database(sqlite)
// : 어플 삭제하면 데이터 사라짐

// 4)sharepreference
// : sqlite와비슷
// : 로그인정보 저장 , 어플 첫실행감지
// ***** fragment간의 정보 전달
// ----> key,value
// 어플을 종료해도 이전의 데이터를 유지하고있음


public class Fra_board extends Fragment {
    Button btnSend;
    // 로그에 사용할 TAG 변수
    final private String TAG = getClass().getSimpleName();

    // 사용할 컴포넌트 선언
    ListView lv;
    Button reg_button;

    String result;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String [] list;
    ArrayList<BoardVO> data = new ArrayList<>();
    // 리스트뷰에 사용할 제목 배열

    JSONArray jsonArray;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_board, container, false);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        DbHelper dbHelper = new DbHelper();

        NodePostJSON np = new NodePostJSON();

        try {
            jsonArray = new JSONArray(np.execute("http://119.200.31.82:80/select",
                    "SELECT * FROM T_BOARD ORDER BY ARTICLE_DATE DESC;" ,"board list").get().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            for(int i = 0; i<jsonArray.length(); i++){
               String res = jsonArray.getJSONObject(i).getString("ARTICLE_DATE").toString();
               String resa[] = res.split("T");
                data.add(new BoardVO(jsonArray.getJSONObject(i).getString("ARTICLE_TITLE").toString(),
                                jsonArray.getJSONObject(i).getString("ARTICLE_CONTENT").toString(),
                                resa[0].toString(),
                                jsonArray.getJSONObject(i).getString("BOARD_SEQ").toString(),
                                jsonArray.getJSONObject(i).getString("SC_CODE").toString()
                                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//
//        try {
//            for(int i = 0; i<jsonArray.length(); i++){
//                String res = jsonArray.getJSONObject(i).getString("ARTICLE_DATE").toString();
//                String resa[] = res.split("T");
//                data.add(new BoardVO(jsonArray.getJSONObject(i).getString("ARTICLE_TITLE").toString(),
//                        jsonArray.getJSONObject(i).getString("BOARD_SEQ").toString(),
//                        jsonArray.getJSONObject(i).getString("SC_CODE").toString(),
//                        resa[0].toString()
//                ));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        result = String.valueOf(pref.getString("content", "0"));
            list = result.split(",");

            Log.v("result", list[0]);
        lv = view.findViewById(R.id.lv);
//
//        for(int i = 0 ; i< list.length;i+=11) {
//            if(i+2<list.length) {
//                data.add(new BoardVO(list[i + 1].replace("'",""),
//                        list[i + 2].replace("'","").replace("\\n","")
//                        , list[i+5].replace("datetime.datetime(","")+"년"+list[i+6]+"월"+list[i+7]+"일",
//                        list[i].replace("(","").replace("[",""),
//                        list[i+4].replace("'","")));
//            }
//        }

        BoardAdapter adapter = new BoardAdapter(
                getContext().getApplicationContext(),
                R.layout.board_list,
                data);


        // 5 ListView에 Adapter적용 시키기
        lv.setAdapter(adapter);

// listView 를 클릭했을 때 이벤트 추가
//
 /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

// 어떤 값을 선택했는지 토스트를 뿌려줌
                Toast.makeText(getContext().getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext().getApplicationContext(), adapterView.getItemAtPosition(i) + " 클릭", Toast.LENGTH_SHORT).show();

// 게시물의 번호와 userid를 가지고 DetailActivity 로 이동
                Intent intent = new Intent(getContext().getApplicationContext(), DetailActivity.class);
                intent.putExtra("board_seq", seqList.get(i));
                intent.putExtra("userid", userid);
                startActivity(intent);

            }
        });*/

// 버튼 컴포넌트 초기화
        reg_button = view.findViewById(R.id.button15);

// 버튼 이벤트 추가
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

// userid 를 가지고 RegisterActivity 로 이동
                Intent intent = new Intent(getContext().getApplicationContext(), RegisterActivity.class);

                startActivity(intent);
            }
        });

        return view;
    }







}