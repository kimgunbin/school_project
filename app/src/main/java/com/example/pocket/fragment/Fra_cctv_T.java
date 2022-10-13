package com.example.pocket.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pocket.class_.cctv.CctvAdapter;
import com.example.pocket.class_.cctv.CctvVO;
import com.example.pocket.R;
import com.example.pocket.class_.database.DbHelper;

import java.util.ArrayList;


public class Fra_cctv_T extends Fragment {


    ArrayList<CctvVO> data = new ArrayList<>();
    ListView lv;
    String result;
    String[] list = {};
    String[] list2 = {};
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.example.pocket.R.layout.fragment_cctv, container, false);

        pref = getContext().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        DbHelper dbHelper = new DbHelper();

        String content2 = null;

            for(int i = 0 ; i<40;i++){
                content2 = dbHelper.connectServer("http://210.183.87.95:5000/CCTVlist", String.valueOf(pref.getString("scCode", "0")));
            }

            editor.putString("content2", content2);
            editor.apply();

        result = String.valueOf(pref.getString("content2", "0"));
        /*
        list = result.split("/");*/

        lv = view.findViewById(R.id.lv);
        /*
        for(int i = 0 ; i<list.length;i++){
            list2 = list[i].split(",");
            data.add(new CctvVO(list2[0],list2[2],list2[1]));
        }*/

        CctvAdapter adapter = new CctvAdapter(
                getContext().getApplicationContext(),
                R.layout.cctv_list,
                data);

        TextView tvCctv1, tvCctv2;
        Button btnBox;


        tvCctv1 = view.findViewById(R.id.tvCctv1);
        tvCctv2 = view.findViewById(R.id.tvCctv2);
        btnBox = view.findViewById(R.id.btnBox);


        btnBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tvCctv1.getVisibility()==View.VISIBLE){
                    tvCctv1.setVisibility(View.INVISIBLE);
                    tvCctv2.setVisibility(View.VISIBLE);
                    btnBox.setText("조회");
                }else {
                    tvCctv1.setVisibility(View.VISIBLE);
                    tvCctv2.setVisibility(View.INVISIBLE);
                    btnBox.setText("보관함");

                }

            }
        });






        // 5 ListView에 Adapter적용 시키기
        lv.setAdapter(adapter);


        return view;
    }
}