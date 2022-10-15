package com.example.pocket.class_.cctv;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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
import com.example.pocket.activity.MainActivity_T;
import com.example.pocket.class_.board.DetailActivity;
import com.example.pocket.class_.board.RegisterActivity;
import com.example.pocket.class_.board.adapter.BoardVO;
import com.example.pocket.class_.database.DbHelper;
import com.example.pocket.fragment.Fra_cctv_T;
import com.example.pocket.imgtest.ImgActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CctvAdapter extends BaseAdapter {

    // 필드
    private Context Context;
    private int layout;
    private ArrayList<CctvVO> data;
    private LayoutInflater inflater; // xml을 눈에 보이게하는 도구
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    int cnt = 1;

    private Bitmap bitmap;

    DbHelper dbHelper = new DbHelper();
    URL url;


    // 생성자 (화면정보,탬플릿,데이터)
    public CctvAdapter(Context Context, int layout, ArrayList<CctvVO> data) {
        this.Context = Context;
        this.layout = layout;
        this.data = data;
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
        if (view == null) {
            // 이전에 만들어진 View가 없다면 == 첫번째 적재를 하고있다면
            // xml --> 눈에보이는 View객체로 바꾸자
            view = inflater.inflate(layout, viewGroup, false);
        }


        TextView tvBtn, tvCctvText, tvDay;
        Button btnYes, btnWait;
        tvBtn = view.findViewById(R.id.tvBtn);
        btnYes = view.findViewById(R.id.btnYes);
        btnWait = view.findViewById(R.id.btnWait);


        tvBtn.setText(data.get(i).getTitle());


        ImageView img = view.findViewById(R.id.imgCctv);
        ImageView icon = view.findViewById(R.id.imgIcon);

        tvCctvText = view.findViewById(R.id.tvCctvText);
        tvDay = view.findViewById(R.id.tvDay);
        String type = data.get(i).getType();


        if (type.equals("N")) {
            tvCctvText.setText("학교 폭력이 탐지 되었습니다. 즉시 확인 바랍니다!!");
            icon.setImageResource(R.drawable.warning_icon);
        } else if (type.equals("B")) {
            tvCctvText.setText("CCTV에 이상상황이 발생했습니다. CCTV를 확인해주세요");
            icon.setImageResource(R.drawable.wa);
        }


        tvDay.setText(data.get(i).getDate());


        Thread uThread = new Thread() {
            @Override
            public void run() {
                try {

                    if (type.equals("N")){
                        url = new URL("http://210.183.87.95:5000/static/violence/" + data.get(i).getFile());
                    }else if (type.equals("B")){
                        url = new URL("http://210.183.87.95:5000/static/blind/" + data.get(i).getFile());
                    }
                     else if(type.equals("Y")){
                        url = new URL("http://210.183.87.95:5000/static/123.png");
                    }else{
                        url = new URL("http://210.183.87.95:5000/static/123.png");
                    }

                     Log.v("주소", String.valueOf(url));

                    // 이미지 URL 경로

                        // web에서 이미지를 가져와 ImageView에 저장할 Bitmap을 만든다.
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true); // 서버로부터 응답 수신
                        conn.connect(); //연결된 곳에 접속할 때 (connect() 호출해야 실제 통신 가능함)

                        InputStream is = conn.getInputStream(); //inputStream 값 가져오기
                        bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };

        uThread.start(); // 작업 Thread 실행

        try {
            //메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야 한다.
            //join() 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리도록 한다.
            //join() 메서드는 InterruptedException을 발생시킨다.
            uThread.join();

            //작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            //UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지 지정

                img.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        LinearLayout lieb = view.findViewById(R.id.linB);

        ConstraintLayout.LayoutParams p1 = new ConstraintLayout.LayoutParams(1, 1);
        ConstraintLayout.LayoutParams p3 = new ConstraintLayout.LayoutParams(500, 1200);


        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "확인", Toast.LENGTH_SHORT).show();


         dbHelper.connectServer("http://210.183.87.95:5000/UpdateY", data.get(i).getFile());
           Intent intent = new Intent(view.getContext(), MainActivity_T.class);
          view.getContext().startActivity(intent);




            }
        });

        btnWait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "보류", Toast.LENGTH_SHORT).show();

             dbHelper.connectServer("http://210.183.87.95:5000/UpdateW", data.get(i).getFile());

            }
        });


        // inflate가 된 view를 리턴 -> ListView에 적재
        return view;
    }
    // 가자
    // 디자인 + 데이터

}
