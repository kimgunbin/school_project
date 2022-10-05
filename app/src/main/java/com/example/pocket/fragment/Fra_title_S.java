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

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Fra_title_S extends Fragment {

    ArrayList<TeacherVO> data = new ArrayList<>();
    ListView lv;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    byte[] teacherList = new byte[100];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_title_s, container, false);
        lv = view.findViewById(R.id.lv1);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        String sc = String.valueOf(pref.getString("scCode", "0"));

        DbHelper dbHelper = new DbHelper();
    while(teacherList==null) {
        teacherList = dbHelper.connectServerList("http://210.183.87.95:5000/TeacherList", sc);
    }
        Log.d("bu", sc);

        TeacherAdapter adapter = new TeacherAdapter(
                getContext().getApplicationContext(),
                R.layout.teacher_list,
                data);

        Log.d("byte", String.valueOf(teacherList[0]));

        lv.setAdapter(adapter);

        return view;
    }
}