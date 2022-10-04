package com.example.pocket.class_.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.pocket.class_.chat.builder.ChatAdapter;
import com.example.pocket.class_.chat.builder.ChatItem;
import com.example.pocket.class_.chat.builder.ChatType;
import com.example.pocket.databinding.ActivityChatBinding;
import com.example.pocket.class_.chat.model.MessageData;
import com.example.pocket.class_.chat.model.RoomData;
import com.example.pocket.class_.chat.retrofit.RetrofitClient;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;

import com.example.pocket.R;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private Socket mSocket;
    private RetrofitClient retrofitClient;
    private String username;
    private String roomNumber;
    private ChatAdapter adapter;
    private Gson gson = new Gson();
    private final int SELECT_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.pocket.R.layout.activity_chat);
/*
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();*/
    }

    private void init() {
        try {
            mSocket = IO.socket("http:  //59.0.129.222:3000");
            Log.d("SOCKET", "Connection success : " + mSocket.id());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        retrofitClient = RetrofitClient.getInstance();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        roomNumber = intent.getStringExtra("roomNumber");
        adapter = new ChatAdapter(getApplicationContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.recyclerView.setAdapter(adapter);

        binding.sendBtn.setOnClickListener(v -> sendMessage());
        binding.imageBtn.setOnClickListener(v -> {
            Intent imageIntent = new Intent(Intent.ACTION_PICK);

            imageIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            startActivityForResult(imageIntent, SELECT_IMAGE);
        });

        mSocket.connect();

        mSocket.on(Socket.EVENT_CONNECT, args -> {
            mSocket.emit("enter", gson.toJson(new RoomData(username, roomNumber)));
        });

        mSocket.on("update", args -> {
            MessageData data = gson.fromJson(args[0].toString(), MessageData.class);
            addChat(data);
        });
    }

    private void addChat(MessageData data) {
        runOnUiThread(() -> {
            if (data.getType().equals("ENTER") || data.getType().equals("LEFT")) {
                adapter.addItem(new ChatItem(data.getFrom(), data.getContent(),
                        toDate(data.getSendTime()), ChatType.CENTER_MESSAGE));
                binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            } else if (data.getType().equals("IMAGE")) {
                adapter.addItem(new ChatItem(data.getFrom(), data.getContent(),
                        toDate(data.getSendTime()), ChatType.LEFT_IMAGE));
                binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            } else {
                adapter.addItem(new ChatItem(data.getFrom(), data.getContent(),
                        toDate(data.getSendTime()), ChatType.LEFT_MESSAGE));
                binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    private void sendMessage() {
        mSocket.emit("newMessage", gson.toJson(new MessageData("MESSAGE",
                username, roomNumber, binding.contentEdit.getText().toString(),
                System.currentTimeMillis())));
        Log.d("Message", new MessageData("MESSAGE", username, roomNumber,
                binding.contentEdit.getText().toString(), System.currentTimeMillis()).toString());

        adapter.addItem(new ChatItem(username, binding.contentEdit.getText().toString(),
                toDate(System.currentTimeMillis()), ChatType.RIGHT_MESSAGE));

        binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        binding.contentEdit.setText("");
    }

    private String toDate(long currentMiliis) {
        return new SimpleDateFormat("hh:mm a").format(new Date(currentMiliis));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.emit("left", gson.toJson(new RoomData(username, roomNumber)));
        mSocket.disconnect();
    }
}