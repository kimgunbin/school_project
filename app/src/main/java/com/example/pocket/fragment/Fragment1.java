package com.example.pocket.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.pocket.Adapter.PhoneAdapter;
import com.example.pocket.Adapter.PhoneVO;
import com.example.pocket.R;

import java.util.ArrayList;


public class Fragment1 extends Fragment {


    ArrayList<PhoneVO> data = new ArrayList<>();
    ListView lv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.example.pocket.R.layout.fragment_1, container, false);
        lv = view.findViewById(R.id.lv);
        data.add(new PhoneVO("교실 1","폭행","1"));
        data.add(new PhoneVO("교실 2","갈취","2"));
        data.add(new PhoneVO("운동장","살해","3"));
        data.add(new PhoneVO("체육관 뒤","사기","4"));
        PhoneAdapter adapter = new PhoneAdapter(
                getContext().getApplicationContext(),
                R.layout.cctv_list,
                data);


        // 5 ListView에 Adapter적용 시키기
        lv.setAdapter(adapter);
        return view;
    }
}