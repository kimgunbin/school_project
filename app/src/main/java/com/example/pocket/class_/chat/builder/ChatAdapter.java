package com.example.pocket.class_.chat.builder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pocket.R;
import com.example.pocket.class_.chat.model.MessageData;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {
//ㅎㅇ
    private Context context;
    private int layout;
    private ArrayList<MessageData> data;
    private LayoutInflater inflater;

    public ChatAdapter(Context context, int layout, ArrayList<MessageData> data) {
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
        if(view == null) {
            view = inflater.inflate(layout, viewGroup, false);
        }


        return view;
    }
}
