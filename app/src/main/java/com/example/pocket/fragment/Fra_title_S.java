package com.example.pocket.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.pocket.R;
import com.example.pocket.activity.LoginActivity;
import com.example.pocket.class_.database.DbHelper;
import com.example.pocket.class_.teacher.TeacherAdapter;
import com.example.pocket.class_.teacher.TeacherVO;

import java.util.ArrayList;

public class Fra_title_S extends Fragment {

    ArrayList<TeacherVO> data = new ArrayList<>();
    ListView lv;
    SharedPreferences pref ;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title_s, container, false);
        lv = view.findViewById(R.id.lv1);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        String sc = String.valueOf(pref.getString("scCode","0"));

        DbHelper dbHelper = new DbHelper();
        byte[] bt = dbHelper.connectServerList("http://210.183.87.95:5000/TeacherList", sc);

        TeacherAdapter adapter = new TeacherAdapter(
                getContext().getApplicationContext(),
                R.layout.teacher_list,
                data);

        lv.setAdapter(adapter);

        return  view;
    }
}