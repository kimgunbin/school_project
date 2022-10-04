package com.example.pocket.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.pocket.R;
import com.example.pocket.class_.chat.ChatActivity;
import com.example.pocket.class_.chat.ChatMainActivity;
import com.example.pocket.databinding.ActivityChatMainBinding;



public class Fra_chat_T extends Fragment {

  Button btn;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragmant_chatroom, container, false);

        btn = view.findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(), ChatMainActivity.class);

                startActivity(intent);
            }
        });


        return view;
    }



}