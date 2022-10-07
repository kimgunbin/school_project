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
import com.example.pocket.class_.database.DbHelper;
import com.example.pocket.class_.board.DetailActivity;
import com.example.pocket.class_.board.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
    ListView listView;
    Button reg_button;
    String userid = "";
    String result;
    DbHelper dbHelper = new DbHelper();
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String [] list;
    // 리스트뷰에 사용할 제목 배열


    ArrayList<String> titleList = new ArrayList<>();

    // 클릭했을 때 어떤 게시물을 클릭했는지 게시물 번호를 담기 위한 배열
    ArrayList<String> seqList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_board, container, false);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        DbHelper dbHelper = new DbHelper();
// 컴포넌트 초기화
        listView = view.findViewById(R.id.listView);
        Log.v("i", String.valueOf(pref.getString("scCode", "0")));

        for(int i = 0 ; i<5 ;i++){

            result = dbHelper.connectServer("http://210.183.87.95:5000/list", "789");
        }
        list = result.split("/");
        Log.v("list",list[0]);
// listView 를 클릭했을 때 이벤트 추가
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
        });

// 버튼 컴포넌트 초기화
        reg_button = view.findViewById(R.id.reg_button);

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

/*
    // onResume() 은 해당 액티비티가 화면에 나타날 때 호출됨
    @Override
    protected void onResume() {
        super.onResume();
// 해당 액티비티가 활성화 될 때, 게시물 리스트를 불러오는 함수를 호출

        ShareActivity.GetBoard getBoard = new ShareActivity.GetBoard();
        getBoard.execute();
    }*/


    // 게시물 리스트를 읽어오는 함수
    class GetBoard extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.d(TAG, "onPreExecute");
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "onPostExecute, " + result);

// 배열들 초기화
            titleList.clear();
            seqList.clear();

            try {

// 결과물이 JSONArray 형태로 넘어오기 때문에 파싱


                for (int i = 0; i < list.length; i++) {


// t
                    titleList.add(list[i]);
                    seqList.add(list[i]);

                }

// ListView 에서 사용할 arrayAdapter를 생성하고, ListView 와 연결
                ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext().getApplicationContext(), android.R.layout.simple_list_item_1, titleList);
                listView.setAdapter(arrayAdapter);

// arrayAdapter의 데이터가 변경되었을때 새로고침
                arrayAdapter.notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


        @Override
        protected String doInBackground(String... params) {
//
// String userid = params[0];
// String passwd = params[1];

            String server_url = "http://15.164.252.136/load_board.php";


            URL url;
            String response = "";
            try {
                url = new URL(server_url);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userid", "");
// .appendQueryParameter("passwd", passwd);
                String query = builder.build().getEncodedQuery();

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();

                conn.connect();
                int responseCode = conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                } else {
                    response = "";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;


        }

    }


}