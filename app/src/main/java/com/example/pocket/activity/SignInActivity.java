package com.example.pocket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.pocket.R;
import com.example.pocket.class_.database.DbHelper;

public class SignInActivity extends AppCompatActivity {

    EditText etId,  etPw1, etPw2, etName, etSchool, etTel;
    RadioButton rbTeacher, rbStudent,rdoButton;
    RadioGroup radio_group;
    Button btnSign;
    String type = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        DbHelper dbHelper = new DbHelper();
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
                if(etPw1.getText().toString().equals(etPw2.getText().toString())) {
                    if(rbTeacher.isChecked()){
                        type = "0";
                    }else if(rbStudent.isChecked()){
                        type = "1";
                    }


                    String postText = etId.getText().toString()+"/"+etPw1.getText().toString()+"/"
                            +etName.getText().toString()+"/"+etSchool.getText().toString()
                            +"/"+etTel.getText().toString()+"/"+type;

                    String result = dbHelper.connectServer("http://210.183.87.95:5000/SignUp",postText);
                    Log.v("타이머", result);

                        Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                        startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}