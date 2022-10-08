package com.example.pocket.class_.board.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pocket.R;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<CommentVO> data;
    private LayoutInflater inflater;
    int cnt = 1;

    public CommentAdapter(Context context, int layout, ArrayList<CommentVO> data) {
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

        if(view == null){
            view = inflater.inflate(layout,viewGroup,false);
        }

        LinearLayout comment_layout = view.findViewById(R.id.comment_layout);




        return view;
    }



}
