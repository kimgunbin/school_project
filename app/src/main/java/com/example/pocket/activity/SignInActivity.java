package com.example.pocket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.pocket.R;

public class SignInActivity extends AppCompatActivity {

    EditText etId,  etPw1, etPw2, etName, etSchool, etTel;
    RadioButton rbTeacher, rbStudent;
    Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etId = findViewById(R.id.etId);
        etPw1 = findViewById(R.id.etPw1);
        etPw2 = findViewById(R.id.etPw2);
        etName = findViewById(R.id.etName);
        etSchool = findViewById(R.id.etSchool);
        etTel = findViewById(R.id.etTel);

        rbTeacher = findViewById(R.id.rbTeacher);
        rbStudent = findViewById(R.id.rbStudent);

        btnSign = findViewById(R.id.btnSign);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}