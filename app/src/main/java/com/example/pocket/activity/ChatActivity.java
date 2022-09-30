package com.example.pocket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocket.R;
import com.example.pocket.class_.chat.ChatAdapter;
import com.example.pocket.class_.chat.MessageData;
import com.example.pocket.class_.chat.RoomData;
import com.google.gson.Gson;


import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import io.socket.client.IO;
import io.socket.client.Socket;


public class ChatActivity extends AppCompatActivity {

    private Socket mSocket;
    private String userName;
    private String roomNumber;
    EditText etChatMsg;
    Button btnSendMsg;
    ListView lv;
    ArrayList<MessageData> data;

    // 푸시 테스트
    // 푸시 테스트2

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmant_chatroom);

        btnSendMsg = findViewById(R.id.btnSendMsg);
        Intent getIntent = getIntent();
        userName = getIntent.getStringExtra("userName");
        roomNumber = getIntent.getStringExtra("roomNumber");
        data = new ArrayList<>();

        try {
            mSocket = IO.socket("http://59.0.129.222:3000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.connect();

        mSocket.on(Socket.EVENT_CONNECT, args -> {
            mSocket.emit("enter", gson.toJson(new RoomData(userName, roomNumber)));
        });

        btnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        etChatMsg = findViewById(R.id.etChatMsg);

        mSocket.emit("newMessage", gson.toJson(new MessageData(
                "MESSAGE",
                userName,
                roomNumber,
                etChatMsg.getText().toString(),
                System.currentTimeMillis()
        )));

        MessageData msgData = new MessageData("MESSAGE", "진주", "send",
                etChatMsg.getText().toString(), 456);
        lv = findViewById(R.id.chatSendView);

        data.add(new MessageData(msgData.getType(), msgData.getFrom(), msgData.getTo(),
                msgData.getContent(), msgData.getSendTime()));

        ChatAdapter adapter = new ChatAdapter(getApplicationContext(), R.layout.chat_sendtxt, data);

        etChatMsg.setText("");
        lv.setAdapter(adapter);

        mSocket.on("update", args -> {
            // data = gson.fromJson(args[0].toString(), MessageData.class);
            // addChat(data);
        });


    }

    //    private void addChat(MessageData data) {
    //        runOnUiThread(() -> {
    //            if (data.getType().equals("ENTER") || data.getType().equals("LEFT")) {
    //                adapter.addItem(new ChatItem(data.getFrom(), data.getContent(), toDate(data.getSendTime()), ChatType.CENTER_CONTENT));
    //                binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    //                binding.recyclerView
    //            } else {
    //                if (username.equals(data.getFrom())) {
    //                    adapter.addItem(new ChatItem(data.getFrom(), data.getContent(), toDate(data.getSendTime()), ChatType.RIGHT_CONTENT));
    //                    binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    //                } else {
    //                    adapter.addItem(new ChatItem(data.getFrom(), data.getContent(), toDate(data.getSendTime()), ChatType.LEFT_CONTENT));
    //                    binding.recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    //                }
    //            }
    //        });
    //    }

    public void addChat(MessageData msgData) {
        ArrayList<MessageData> data = new ArrayList<>();
        lv = findViewById(R.id.chatSendView);
        etChatMsg = findViewById(R.id.etChatMsg);

        data.add(new MessageData(msgData.getType(), msgData.getFrom(), msgData.getTo(),
                msgData.getContent(), msgData.getSendTime()));

        data.get(0).setContent(etChatMsg.getText().toString());
        ChatAdapter adapter = new ChatAdapter(getApplicationContext(), R.layout.chat_sendtxt, data);
        Log.d("확인", data.get(0).getContent());

        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    public String toData(long currentMillis) {
        return new SimpleDateFormat("hh:mm a").format(new Date(currentMillis));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSocket.emit("left", gson.toJson(new RoomData(userName, roomNumber)));
        mSocket.disconnect();
    }
}