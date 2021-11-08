//2019-11-27 제작 시작
//송채영 cdcd362@gmail.com

package com.cookandroid.bankaccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {

    private CalendarView calendarView;
    //CalendarView 이름은 calendarView라 명한다

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); // xml에서 레이아웃 꾸민 거 출력함



        calendarView = (CalendarView) findViewById(R.id.calendar_Layout);
        // xml 아이디 끌어오고, 위에서 말한 CalendarView=calendarView를 CalendarView라고 정체성 부여





        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int day) {
            //내가 달력 누르면 날짜 값 받아오는 뭐시기 명령어, 이것밖에 못 씀 자세히는 잘 모르겠는데 어쨌든 필요함




                Intent intent = new Intent(MainActivity.this, entry.class); //Main에서 entry class로 넘어가게 해줌

                String date = year + "." + (month+1) + "." + day;   //entry 클래스의 id:date의 표시방식
                                                                    //그냥 month만 하니까 한 달 더 느리게 나옴 그래서 (month + 1)로 지정
                                                                    //ex) 12월 2일 누름 -> entry xml에선 11월 2일로 출력 됨

                intent.putExtra("date", date);      //내가 누른 달력 값(날짜)인 위에서 겁나게 구구절절 말 한 date
                                                          //이름은 date라고 하고, 값을 entry 클래스에 전달해준다.

                startActivity(intent);  //intent 실행 시작


            }
        });





    }
}
