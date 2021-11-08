package com.cookandroid.bankaccount;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.cookandroid.bankaccount.entry.KEY_CONTEXT;    //entry 클래스에서 KEY_CONTEXT를 불러온다
import static com.cookandroid.bankaccount.entry.KEY_DATE;       //entry 클래스에서 KEY_DATE를 불러온다
import static com.cookandroid.bankaccount.entry.KEY_ID;         //entry 클래스에서 KEY_ID를 불러온다
import static com.cookandroid.bankaccount.entry.KEY_PRICE;      //entry 클래스에서 KEY_PRICE를 불러온다
import static com.cookandroid.bankaccount.entry.TABLE_NAME;     //entry 클래스에서 TABLE_NAME를 불러온다

//데이터 다루는 SQLite는
//책 참고함 p.450

//DB와 Table 을 오픈해줌
public class MyDBHelper extends SQLiteOpenHelper {
    public static MyDBHelper sInstance;     //데이터 삭제에 필요한 Instance 만들기용



    public MyDBHelper(Context context) {

        super(context, "BankAccount.db", null, 4);      //
    }


    //데이터 삭제용 getInstance - entry 전달용
    public static MyDBHelper getInstance(Context context){
        if(sInstance == null){
            sInstance = new MyDBHelper(context);
        }
        return sInstance;
    }



    public void onCreate(SQLiteDatabase db) {       //table을 준비하는 곳, 한 번밖에 호출이 안 된다.
        // AUTOINCREMENT 속성 사용 시 PRIMARY KEY로 지정한다.
        String query = String.format("CREATE TABLE %s ("        //table 이름 = %s(entry class에 선언되어있음), String
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT, "      //key id 자동 증가, String
                + "%s TEXT, "                                   //목록 - Text, String값, context?
                + "%s INTEGER, "                                //목록 - Integer, String, 가격정보
                + "%s TEXT);", TABLE_NAME, KEY_ID, KEY_CONTEXT, KEY_PRICE, KEY_DATE);
        db.execSQL(query);
    }




    //데이터 갱신
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {      //스키마 변경을 할 때마다 선언되는 곳
        String query = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);        //table에 새로운 데이터가 들어올 시
        db.execSQL(query);  //query를 작동시킨다.
        onCreate(db);   //한 번밖에 구동이 안 되는 onCreate를 강제적으로 다시 실행시키는 명령어
    }



}
