package com.example.pocket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.pocket.R;
import com.example.pocket.class_.share.ShareActivity;

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


public class Fra_share extends Fragment {
    Button btnSend;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_share, container, false);

      btnSend = view.findViewById(R.id.button3);

      btnSend.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(getContext().getApplicationContext(), ShareActivity.class);
              startActivity(intent);
          }
      });

        return view;
    }
}