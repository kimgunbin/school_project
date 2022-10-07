package com.example.pocket.class_.cctv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pocket.R;

import java.util.ArrayList;

public class CctvAdapter extends BaseAdapter {

    // 필드
    private Context Context;
    private int layout;
    private ArrayList<CctvVO> data;
    private LayoutInflater inflater; // xml을 눈에 보이게하는 도구
    int cnt = 1;
    // 생성자 (화면정보,탬플릿,데이터)
    public CctvAdapter(Context Context, int layout, ArrayList<CctvVO> data) {
        this.Context =Context;
        this.layout =layout;
        this.data =data;
        this.inflater = (LayoutInflater) Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 다운캐스팅

    }

    @Override
    public int getCount() {
        // ListView의 항목 개수를 지정 (ArrayList의 양만큼)
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        // 몇 번째의 data를 반환할 것인지 (데이터의 position)
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        // position 번쨰의 data id를 반환
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        // 1. i(position) : 항목의 번호
        // 2. view : 이전에 만들어진 view
        // 3. viewGroup : 모든 뷰(항목)을 담고있는 ListView


        // xml파일이 눈에 보이는 View 객체로 존재하기 위해서는 inflater를 사용해서 inflate를해줘야함함
        // setContentView작업과 유사

        // phone_list
        // Adpater를 적용시킬 ListView
        // 속성 설정 : false
        if(view == null){
            // 이전에 만들어진 View가 없다면 == 첫번째 적재를 하고있다면
            // xml --> 눈에보이는 View객체로 바꾸자
            view = inflater.inflate(layout,viewGroup,false);
        }





        TextView tvBtn;
        Button btnYes, btnWait;
        tvBtn = view.findViewById(R.id.tvBtn);
        btnYes = view.findViewById(R.id.btnYes);
        btnWait = view.findViewById(R.id.btnWait);

        tvBtn.setText(data.get(i).getTitle());


        ImageView img = view.findViewById(R.id.imgCctv);
        ImageView icon = view.findViewById(R.id.imgIcon);

        LinearLayout lieb = view.findViewById(R.id.linB);

         img.setImageResource(R.drawable.al);
         icon.setImageResource(R.drawable.warning_icon);
        ConstraintLayout.LayoutParams p1 = new ConstraintLayout.LayoutParams(1,1);
        ConstraintLayout.LayoutParams p3 = new ConstraintLayout.LayoutParams( 500,1200);
        
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "확인", Toast.LENGTH_SHORT).show();

            }
        });
        
        btnWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "보류", Toast.LENGTH_SHORT).show();

            }
        });





        // inflate가 된 view를 리턴 -> ListView에 적재
        return view;
    }
    // 가자
    // 디자인 + 데이터

}
