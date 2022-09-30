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
import com.example.pocket.databinding.ActivityChatMainBinding;


public class Fra_chat_T extends Fragment {
    Button btn;
    EditText usernameEdit, roomEdit;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_chat_main, container, false);

        btn = view.findViewById(R.id.enterBtn);
        usernameEdit = view.findViewById(R.id.usernameEdit);
        roomEdit = view.findViewById(R.id.roomEdit);

        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        int readPermission = ActivityCompat.checkSelfPermission(getContext().getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE);

        initUI();

        return view;
    }

    private void initUI() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        btn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext().getApplicationContext(), ChatActivity.class);
            intent.putExtra("username", usernameEdit.getText().toString());
            intent.putExtra("roomNumber", roomEdit.getText().toString());
            startActivity(intent);
        });
    }
}