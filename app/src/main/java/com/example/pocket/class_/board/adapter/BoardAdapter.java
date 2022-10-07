package com.example.pocket.class_.board.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pocket.R;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {

    // 필드
    private Context Context;
    private int layout;
    private ArrayList<BoardVO> data;
    private LayoutInflater inflater; // xml을 눈에 보이게하는 도구
    int cnt = 1;
    // 생성자 (화면정보,탬플릿,데이터)
    public BoardAdapter(Context Context, int layout, ArrayList<BoardVO> data) {
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





        TextView B_title,B_date;

        B_title = view.findViewById(R.id.B_title);
        B_date = view.findViewById(R.id.B_date);


        B_title.setText(data.get(i).getTitle());
        B_date.setText(data.get(i).getFile());


        






        return view;
    }
    // 가자
    // 디자인 + 데이터

}
