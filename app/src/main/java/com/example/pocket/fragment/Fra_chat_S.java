package com.example.pocket.fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.fragment.app.Fragment;

import com.example.pocket.R;
import com.example.pocket.class_.cctv.CctvAdapter;

import com.example.pocket.class_.teacher.TeacherAdapter;
import com.example.pocket.class_.teacher.TeacherVO;

import java.util.ArrayList;


public class Fra_chat_S extends Fragment {

    ArrayList<TeacherVO> data = new ArrayList<>();
    ListView lv;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title_s, container, false);
        lv = view.findViewById(R.id.lv1);
        data.add(new TeacherVO("선생님1","선생님"));
        data.add(new TeacherVO("선생님2","선생님"));
        data.add(new TeacherVO("선생님3","선생님"));
        data.add(new TeacherVO("선생님4","선생님"));


        TeacherAdapter adapter = new TeacherAdapter(
                getContext().getApplicationContext(),
                R.layout.teacher_list,
                data);


        // 5 ListView에 Adapter적용 시키기
        lv.setAdapter(adapter);

        return  view;

    }
}