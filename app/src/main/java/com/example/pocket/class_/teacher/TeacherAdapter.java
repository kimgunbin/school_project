package com.example.pocket.class_.teacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pocket.R;

import java.util.ArrayList;

public class TeacherAdapter extends BaseAdapter {

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


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null){
            view = inflater.inflate(layout,viewGroup,false);
        }

        ConstraintLayout cl = view.findViewById(R.id.clTea);
        Button tvBtn1 = view.findViewById(R.id.tvBtn1);
        tvBtn1.setText("   "+data.get(i).getName());

        return null;
    }
}
