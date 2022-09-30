package com.example.pocket.fragment;




import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;

import com.example.pocket.R;
import com.example.pocket.activity.LoginActivity;
import com.example.pocket.activity.MainActivity_T;
import com.example.pocket.class_.chat.ChatActivity;


public class Fra_chat_S extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_chat_main, container, false);
        Button btn = view.findViewById(R.id.enterBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), ChatActivity.class);
                startActivity(intent);
            }
        });
// ㅎㅎ
        return view;
    }
}