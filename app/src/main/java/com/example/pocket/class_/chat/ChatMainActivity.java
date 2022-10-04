package com.example.pocket.class_.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.pocket.R;
import com.example.pocket.databinding.ActivityChatMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChatMainActivity extends AppCompatActivity {

    private ActivityChatMainBinding binding;
    Button btn;
    EditText usernameEdit,roomEdit;
    String user,room;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        btn = findViewById(R.id.enterBtn);
        usernameEdit = findViewById(R.id.usernameEdit);
        roomEdit = findViewById(R.id.roomEdit);


        user = usernameEdit.getText().toString();
        room = roomEdit.getText().toString();

        binding = ActivityChatMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
        };
        int readPermission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

                initUI(user,room);

    }


    private void initUI(String user,String room) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding.enterBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
            intent.putExtra("username", user);
            intent.putExtra("roomNumber", room);
            startActivity(intent);
        });
    }
}