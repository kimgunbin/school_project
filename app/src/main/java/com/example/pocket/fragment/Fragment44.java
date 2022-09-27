package com.example.pocket.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        // findViewById 와 같은 뷰에 접근하는 메소드는 Activity에서만가능
        // xml (코드) -----inflate -----> 눈에보이는 View객체
        // fragment4.xml ----> inflate ----> 눈에보이는 View객체
        View view = inflater.inflate(R.layout.fragment_4_4, container, false);
        btnSend =view.findViewById(R.id.btnSend);
        eturl = view.findViewById(R.id.eturl);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = eturl.getText().toString();

                // 1.getActivity().getApplicationContext()
                // fragment가 얹어져있는 activity의 정보를 가져와서 화면정보를 얻는다.
                // 2.getContext()
                // 화면의 정보를 바로 가져오는 메소드

                //Toast.makeText(getActivity().getApplicationContext(),url,Toast.LENGTH_SHORT).show();
                Toast.makeText(getContext(),url,Toast.LENGTH_SHORT).show();

                // sharedpreference에 url값에 저장

                // 1. sharedpreference 가져오기
                // 1) 이름지정
                // 2) 모드 : context.mode_private
                // -----> mySPF가 없으면 새로생성, 있으면 있는거 가져옴
                SharedPreferences spf = getActivity().getSharedPreferences(
                        "mySPF",
                        Context.MODE_PRIVATE
                );

                // 2. spf에 데이터를 저장할 수 있는 editor 가져오기
                SharedPreferences.Editor editor = spf.edit();

                // 3. editor를 통해서 데이터를 저장하기 (commit 꼭 하기)
                editor.putString("address",url).commit();
            }
        });

        return view;
    }
}