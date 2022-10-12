package com.example.pocket.class_.board.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pocket.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<CommentVO> data;
    private LayoutInflater inflater;
    int cnt = 1;
    JSONArray jsonArray;
    ListView lv;

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

        lv = view.findViewById(R.id.commm);

        TextView userId, date, content;

        userId = view.findViewById(R.id.cmt_userid_tv);
        date = view.findViewById(R.id.cmt_date_tv);
        content = view.findViewById(R.id.cmt_content_tv);

        userId.setText(data.get(i).getId());
        date.setText(data.get(i).getDate());
        content.setText(data.get(i).getContent());

        return view;
    }



}
