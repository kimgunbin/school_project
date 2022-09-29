package com.example.pocket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.pocket.R;

import java.util.ArrayList;

public class PhoneAdapter extends BaseAdapter {

    // 필드
    private Context Context;
    private int layout;
    private ArrayList<PhoneVO> data;
    private LayoutInflater inflater; // xml을 눈에 보이게하는 도구
    int cnt = 1;
    // 생성자 (화면정보,탬플릿,데이터)
    public PhoneAdapter(Context Context, int layout, ArrayList<PhoneVO> data) {
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

        TextView textlist = view.findViewById(R.id.textlist);
        ConstraintLayout c1 = view.findViewById(R.id.C1);

        Button btn = view.findViewById(R.id.button);
        textlist.setText(data.get(i).getTitle());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConstraintLayout.LayoutParams p1 = new ConstraintLayout.LayoutParams(1,1);
                ConstraintLayout.LayoutParams p3 = new ConstraintLayout.LayoutParams(378,300);

                if(cnt%2==1) {
                    c1.setLayoutParams(p3);
                    cnt+=1;
                }else{
                    c1.setLayoutParams(p1);
                    cnt+=1;
                }
            }
        });


        // inflate가 된 view를 리턴 -> ListView에 적재
        return view;
    }

    // 디자인 + 데이터

}
