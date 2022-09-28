package com.example.pocket.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pocket.R;

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


public class Fragment44 extends Fragment {
    Button btnSend;
    EditText eturl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_4_4, container, false);

        WebView wv = view.findViewById(R.id.wv);

        SharedPreferences spf = getActivity().getSharedPreferences(
                "mySPF",
                Context.MODE_PRIVATE
        ); // Fragment에서 만든 mySPF를 가져온다 : 있는거 가져옴

        // WebView에 원하는 페이지 띄우기

        // 1. 주소 지정
        // spf에서 getString할 때 필요한 매개변수
        // 1) key값
        // 2) default value값 : address(키)에 아무것도 없을 때(value가 없을때)
        String address = spf.getString("address","https://www.law.go.kr/%EB%B2%95%EB%A0%B9/%ED%95%99%EA%B5%90%ED%8F%AD%EB%A0%A5%EC%98%88%EB%B0%A9%EB%B0%8F%EB%8C%80%EC%B1%85%EC%97%90%EA%B4%80%ED%95%9C%EB%B2%95%EB%A5%A0");

        // 2. 설정 변경 (javascript사용가능)
        WebSettings ws = wv.getSettings();
        ws.setJavaScriptEnabled(true);

        // 3. WebView에 Client 설정
        wv.setWebViewClient(new WebViewClient());

        // 4. 주소 설정
        wv.loadUrl(address);

        return view;
    }
}