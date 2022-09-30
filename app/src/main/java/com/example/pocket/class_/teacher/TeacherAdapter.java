package com.example.pocket.class_.teacher;

import android.content.Context;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class TeacherAdapter {

    private Context context;
    private  int layout;
    private ArrayList<TeacherVO> data;
    private LayoutInflater inflater;
    int cnt = 1;

    public TeacherAdapter(Context context, int layout, ArrayList<TeacherVO> data) {
        this.context = context;
        this.layout = layout;
        this.data = data;

    }


}
