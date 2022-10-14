package com.example.pocket.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocket.R;
import com.example.pocket.class_.chat.ChatActivity;
import com.example.pocket.class_.chat.ChatMainActivity;
import com.example.pocket.class_.chat.builder.ChatAdapter;
import com.example.pocket.class_.chat.builder.ChatItem;
import com.example.pocket.class_.chat.builder.ChatType;
import com.example.pocket.class_.chat.model.MessageData;
import com.example.pocket.class_.chat.model.RoomData;
import com.example.pocket.class_.chat.retrofit.RetrofitClient;
import com.example.pocket.databinding.ActivityChatBinding;
import com.example.pocket.databinding.ActivityChatMainBinding;
import com.google.gson.Gson;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.socket.client.IO;
import io.socket.client.Socket;


public class Fra_chat_T extends Fragment {

    String user, room;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private ActivityChatBinding binding;
    private Socket mSocket;
    private RetrofitClient retrofitClient;
    private String username;
    private String roomNumber;
    private ChatAdapter adapter;
    private Gson gson = new Gson();
    private final int SELECT_IMAGE = 100;
    private RecyclerView recyclerView;
    ViewGroup rootView;
    View view;
    Button sendBtn;
    EditText contentEdit;
    ArrayList<ChatItem> item;
    int cnt = 0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        rootView = (ViewGroup) inflater.inflate(R.layout.activity_chat, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        sendBtn = rootView.findViewById(R.id.send_btn);
        contentEdit = rootView.findViewById(R.id.content_edit);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();
        room = String.valueOf(pref.getString("name", "0")) + " 선생님";
        user = String.valueOf(pref.getString("name", "0"));


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


//        Intent intent = new Intent(getActivity(), ChatActivity.class);
//        intent.putExtra("username", user);
//        intent.putExtra("roomNumber", room);
//        startActivity(intent);

        init();

        return rootView;
    }

    private void init() {
        try {
            mSocket = IO.socket("http://119.200.31.82:3000");
            Log.d("SOCKET", "Connection success : " + mSocket.id());



        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        retrofitClient = RetrofitClient.getInstance();

        if (adapter == null) {
            adapter = new ChatAdapter(rootView.getContext().getApplicationContext());
            LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext().getApplicationContext());


            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }


        sendBtn.setOnClickListener(v -> sendMessage());
//        binding.imageBtn.setOnClickListener(v -> {
//            Intent imageIntent = new Intent(Intent.ACTION_PICK);
//
//            imageIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                    "image/*");
//            startActivityForResult(imageIntent, SELECT_IMAGE);
//        });

        mSocket.connect();


        mSocket.on(Socket.EVENT_CONNECT, args -> {
            mSocket.emit("enter", gson.toJson(new RoomData(user, room)));
        });

        mSocket.on("update", args -> {
            MessageData data = gson.fromJson(args[0].toString(), MessageData.class);
            addChat(data);
        });
    }

    private void addChat(MessageData data) {
        getActivity().runOnUiThread(() -> {
            if (data.getType().equals("ENTER") || data.getType().equals("LEFT")) {
//                adapter.addItem(new ChatItem(data.getFrom(), data.getContent(),
//                        toDate(data.getSendTime()), ChatType.CENTER_MESSAGE));
//                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            } else if (data.getType().equals("IMAGE")) {
                adapter.addItem(new ChatItem(data.getFrom(), data.getContent(),
                        toDate(data.getSendTime()), ChatType.LEFT_IMAGE));
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            } else {
//                Notification notifity = new Notification();
//                notifity.createNotification("채팅",data.getContent());

//                item.add(new ChatItem(data.getFrom(), data.getContent(),
//                        toDate(data.getSendTime()), ChatType.LEFT_MESSAGE));

                adapter.addItem(new ChatItem(data.getFrom(), data.getContent(),
                        toDate(data.getSendTime()), ChatType.LEFT_MESSAGE));
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    private void sendMessage() {
        mSocket.emit("newMessage", gson.toJson(new MessageData("MESSAGE",
                user, room, contentEdit.getText().toString(),
                System.currentTimeMillis())));
        Log.d("Message", new MessageData("MESSAGE", user, room,
                contentEdit.getText().toString(), System.currentTimeMillis()).toString());

//        item.add(new ChatItem(user, contentEdit.getText().toString(),
//                toDate(System.currentTimeMillis()), ChatType.RIGHT_MESSAGE));

        adapter.addItem(new ChatItem(user, contentEdit.getText().toString(),
                toDate(System.currentTimeMillis()), ChatType.RIGHT_MESSAGE));

        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        contentEdit.setText("");
    }

    private String toDate(long currentMiliis) {
        return new SimpleDateFormat("hh:mm a").format(new Date(currentMiliis));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.emit("left", gson.toJson(new RoomData(user, room)));
        mSocket.disconnect();
    }




}