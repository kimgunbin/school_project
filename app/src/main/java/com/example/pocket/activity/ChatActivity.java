package com.example.pocket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pocket.R;
import com.example.pocket.class_.chat.MessageData;
import com.example.pocket.class_.chat.RoomData;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;

public class ChatActivity extends AppCompatActivity {

    private Socket mSocket;
    private String userName;
    private String roomNumber;
    EditText etChatMsg;
    Button btnSendMsg;

    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnSendMsg = findViewById(R.id.btnSendMsg);

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

        etChatMsg.setText("");

        mSocket.on("update", args -> {
            MessageData data = gson.fromJson(args[0].toString(), MessageData.class);
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