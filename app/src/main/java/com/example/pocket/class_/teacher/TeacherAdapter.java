package com.example.pocket.class_.teacher;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pocket.R;
import com.example.pocket.activity.MainActivity_S;

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
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        ImageView imgTeacher = view.findViewById(R.id.imgT);

        ConstraintLayout cl = view.findViewById(R.id.clTea);
        TextView tvBtn1 = view.findViewById(R.id.tvBtn1);
        tvBtn1.setText(data.get(i).getName());
        imgTeacher.setImageResource(data.get(i).getTeacherIcon());

        cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity_S.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("teacher_id", data.get(i).getName());
                intent.putExtra("caseNum", "1");
                view.getContext().startActivity(intent);

            }
        });

        return view;
    }
}
