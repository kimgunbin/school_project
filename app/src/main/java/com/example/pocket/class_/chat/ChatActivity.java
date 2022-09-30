package com.example.pocket.class_.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pocket.R;
import com.example.pocket.class_.chat.builder.ChatAdapter;
import com.example.pocket.class_.chat.model.MessageData;
import com.example.pocket.class_.chat.model.RoomData;
import com.google.gson.Gson;


import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import io.socket.client.IO;
import io.socket.client.Socket;


public class ChatActivity extends AppCompatActivity {

}