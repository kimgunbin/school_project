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

import androidx.fragment.app.Fragment;

import com.example.pocket.R;


public class Fragment1 extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);

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
        String address = spf.getString("address","http://www.google.com");

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