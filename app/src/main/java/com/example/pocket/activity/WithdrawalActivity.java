package com.example.pocket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pocket.R;
import com.example.pocket.class_.database.DbHelper;

public class WithdrawalActivity extends AppCompatActivity {

    TextView out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        DbHelper dbHelper = new DbHelper();

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }
}