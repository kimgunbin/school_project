package com.example.pocket.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.pocket.class_.board.adapter.BoardVO;
import com.example.pocket.class_.cctv.CctvAdapter;
import com.example.pocket.class_.cctv.CctvVO;
import com.example.pocket.R;
import com.example.pocket.class_.database.DbHelper;
import com.example.pocket.imgtest.ImgActivity;

import java.util.ArrayList;


public class Fra_cctv_T extends Fragment {


    ArrayList<CctvVO> data = new ArrayList<>();
    ListView lv;
    String result;
    String[] list = {};
    String[] list2 = {};
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    DbHelper dbHelper = new DbHelper();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.example.pocket.R.layout.fragment_cctv, container, false);
        pref = getContext().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();



        result = String.valueOf(pref.getString("contentCctv", "0"));

        Log.v("값",result);
        list = result.split(",");

        for (int i = 0; i < list.length; i += 9) {
            if (i+8< list.length) {
                data.add(new CctvVO(list[i].replace("'", "").replace("[(","").replace("(","").replace(" ",""),
                        list[i + 1].replace("'", "").replace(" ","")
                        , list[i + 2].replace("datetime.datetime(", "") + "년" + list[i + 3] + "월" + list[i + 4] + "일"+list[i+5]+"시"+list[i+6]+"분",
                        list[i+8].replace(" ", "").replace("'","").replace(")","")));
            }
        }
        Log.v("값1", list[0]);







//        for(int i = 0 ; i<list.length;i++){
//            list2 = list[i].split(",");
//            data.add(new CctvVO(list2[0],list2[2],list2[1]));
//        }
        lv = view.findViewById(R.id.lv);

        CctvAdapter adapter = new CctvAdapter(
                getContext().getApplicationContext(),
                R.layout.cctv_list,
                data);

        TextView tvCctv1, tvCctv2;
        Button btnBox;




     /*
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
                    dbHelper.connectServer("http://210.183.87.95:5000/CCTVlistW", String.valueOf(pref.getString("scCode","0")));
                    getActivity().getSupportFragmentManager().beginTransaction().replace(
                            R.id.fl, new Fra_cctv_T()).commit();

                }

            }
        });  */






        // 5 ListView에 Adapter적용 시키기
        lv.setAdapter(adapter);


        return view;
    }
}