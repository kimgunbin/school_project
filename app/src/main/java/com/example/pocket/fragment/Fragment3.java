package com.example.pocket.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pocket.R;
import com.example.pocket.activity.LoginActivity;
import com.example.pocket.activity.SignInActivity;
import com.example.pocket.class_.database.DbHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    SharedPreferences pref ;
    SharedPreferences.Editor editor;
    EditText pw1,pw2,tel,sc;
    Button btnUpdate;
    String postText = "";
    String result = "check";
    String id ="";
    TextView logout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_2_2, container, false);
        DbHelper dbHelper = new DbHelper();


        pw1 = view.findViewById(R.id.pw1);
        pw2 = view.findViewById(R.id.pw2);

        logout=view.findViewById(R.id.logout);
        tel = view.findViewById(R.id.tel);
        logout.setText("로그아웃");
        sc = view.findViewById(R.id.scCode);

        btnUpdate = view.findViewById(R.id.btnUpdate);

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();



        id = String.valueOf(pref.getString("id","0"));
        sc.setText(String.valueOf(pref.getString("scCode","0")));

        tel.setText(String.valueOf(pref.getString("tel","0")));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext().getApplicationContext(),LoginActivity.class);




                if(pw1.getText().toString().equals(pw2.getText().toString())) {

                    postText = id+"/"+pw1.getText().toString()+"/"
                           +sc.getText().toString()
                            +"/"+tel.getText().toString()+"/";

                    result = dbHelper.connectServer("http://210.183.87.95:5000/Editmem", postText);

                    Toast.makeText(getContext(), "로그인을 다시 해주세요.", Toast.LENGTH_SHORT).show();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);


                }else{
                    Toast.makeText(getContext().getApplicationContext(), "회원정보수정 실패", Toast.LENGTH_SHORT).show();

                }

                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext().getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });



        // Inflate the layout for this fragment
        return view;

    }
}