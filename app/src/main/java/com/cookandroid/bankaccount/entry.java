package com.cookandroid.bankaccount;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class entry extends AppCompatActivity {

    private TextView thedate;
    private TextView total;
    private Button ok;
    private ListView listView;


    MyDBHelper mHelper;
    SQLiteDatabase db;
    Cursor cursor;
    MyCursorAdapter myCursorAdapter;


    //나중에 MyDBHelper에 정보가 넘어감
    final static String KEY_ID = "_id";
    final static String KEY_CONTEXT = "context";
    final static String KEY_PRICE = "price";
    final static String TABLE_NAME = "MyAccountList";
    final static String KEY_DATE = "date";
    public static String viewDate = get_date();     //vidwDate = get_date() 이다. 매번 선언하기 힘들다.



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);    //xml 레이아웃 출력해라


        thedate = (TextView) findViewById(R.id.date);  //xml에서 날짜 textview 변수 설정
        total = (TextView) findViewById(R.id.total);    //xml에서 총합 textview 변수 설정
        ok = (Button) findViewById(R.id.ok);    //xml에서 확인 button 변수 설정
        listView = (ListView) findViewById(R.id.listView);  //xml에서 등록할 리스트 listview 변수 설정



        //변수 설정
        thedate = (TextView) findViewById(R.id.date);   //맨 위 날짜
        ok = (Button) findViewById(R.id.ok);            //확인 버튼
        ListView list = (ListView) findViewById(R.id.listView);     //리스트 뷰
        total = (TextView) findViewById(R.id.total);            //총합




        //첫 번째 - 선택한 날짜 두 번째(entry) 클래스 상단에 표시
        Intent intent = getIntent();        //intent 생성
        String date = intent.getStringExtra("date");    //저기 main에서 누른 달력 값을 date라고 명해놓은 거 갖고 옴

        if(!TextUtils.isEmpty(date)){

            viewDate = date;
            thedate.setText(date);
            }   else{

            thedate.setText(viewDate);
        }   //if 값 끝



        //두 번째 - 데이터 저장할 곳 생성
        //데이터베이스 불러오기
        mHelper = new MyDBHelper(this);
        db = mHelper.getWritableDatabase();


        //커서 어댑터 생성
        String querySelectAll = String.format( "SELECT * FROM %s WHERE date = '%s'", TABLE_NAME, viewDate);
        cursor = db.rawQuery( querySelectAll, null );
        myCursorAdapter = new MyCursorAdapter ( this, cursor );

        //리스트뷰 어댑터 설정
        list.setAdapter( myCursorAdapter );





        // 총합 가격 표시
        final String queryPriceSum = String.format(" SELECT SUM(price) FROM %s WHERE date = '%s'", TABLE_NAME, viewDate);
        cursor = db.rawQuery( queryPriceSum, null );
        cursor.moveToNext();
        String sum = String.valueOf(cursor.getInt(0));
        total.setText("Total : " + sum);


//
//        //리스트 삭제 전 db 불러올 거 미리 선언                    //30:42 MainActivity 시작 47:51
//        MyDBHelper dbHelper = MyDBHelper.getInstance(this);     //14:19 - MyDBHelper 가서
//        Cursor cursor = dbHelper.getReadableDatabase().query()  //32:48, 3:00







        //새로 추가함 34:06
        MyDBHelper dbHelper = MyDBHelper.getInstance(this);
        final Cursor[] cursor = {dbHelper.getReadableDatabase()
                .query(TABLE_NAME, null, null, null, null, null, null)};











        // 꾹 누를 시 해당 리스트가 삭제
        //https://youtu.be/lf6GOaTRCgA?t=3161    52:41
        //55:07
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            EditText eContext = (EditText) findViewById( R.id.name );
            EditText ePrice = (EditText) findViewById( R.id.price );




            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, final long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(entry.this);

                builder.setTitle("내역 삭제");
                builder.setMessage("내역을 삭제하시겠습니까?");
                builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        SQLiteDatabase db1 = MyDBHelper.getInstance(entry.this).getWritableDatabase();
                        db1.delete(TABLE_NAME,
                                KEY_ID + " = " + id, null);

                        Toast.makeText(entry.this, "내역이 삭제되었습니다", Toast.LENGTH_SHORT);



                        // 총합 가격 표시
                        String queryPriceSum = String.format( " SELECT SUM(price) FROM %s WHERE date = '%s'", TABLE_NAME, viewDate);
                        cursor[0] = db.rawQuery( queryPriceSum, null );
                        cursor[0].moveToNext();
                        String sum = String.valueOf(cursor[0].getInt(0));
                        total.setText("Total : " + sum);



                        //데이터 갱신해야함.

                        // 아래 메서드를 실행하면 리스트가 갱신된다.
                        String querySelectAll = String.format( "SELECT * FROM %s WHERE date = '%s'", TABLE_NAME, viewDate);
                        cursor[0] = db.rawQuery( querySelectAll, null );
                        myCursorAdapter.changeCursor(cursor[0]);


                        eContext.setText( "" );
                        ePrice.setText( "" );







                    }
                });
                builder.setNegativeButton("취소", null);
                builder.show();


                return true;
            }
        });





    }   //여기까지가 onCreate



    //날짜
    static public String get_date(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy. mm. dd", Locale.KOREA);    //날짜 기본 제공되는 형식 말고
                                                                                                        // ****. **. ** 이렇게 표시하고 싶어.
        Date currentTime = new Date();  //현재 시간을 currentTime에 저장한다.
        String Today_day = simpleDateFormat.format(currentTime);
        return Today_day;   //오늘 날짜 값을 반환한다.


    }






    public void Ok_Button( View v ) //listView 데이터 출력
    {
        EditText eContext = (EditText) findViewById( R.id.name );
        EditText ePrice = (EditText) findViewById( R.id.price );
        //변수 설정

        String contexts = eContext.getText().toString();    //econtext string 값으로 강제 전환
        int price = Integer.parseInt( ePrice.getText().toString() );    //eprice string 값으로 강제 전환


        //문자열은 ''로 감싸야 한다.
        String query = String.format(
                "INSERT INTO %s VALUES ( null, '%s', %d, '%s' );", TABLE_NAME, contexts, price, viewDate);
        db.execSQL( query );






        // 총합 가격 표시 - 저장된 데이터 기반으로 합체
        String queryPriceSum = String.format( " SELECT SUM(price) FROM %s WHERE date = '%s'", TABLE_NAME, viewDate);
        cursor = db.rawQuery( queryPriceSum, null );
        cursor.moveToNext();
        String sum = String.valueOf(cursor.getInt(0));
        total.setText("Total : " + sum);





        // 아래 메서드를 실행하면 리스트가 갱신된다.
        String querySelectAll = String.format( "SELECT * FROM %s WHERE date = '%s'", TABLE_NAME, viewDate);
        cursor = db.rawQuery( querySelectAll, null );
        myCursorAdapter.changeCursor( cursor );


        eContext.setText( "" );
        ePrice.setText( "" );



        // 저장 버튼 누른 후 키보드 안보이게 하기
        InputMethodManager imm =
                (InputMethodManager) getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.hideSoftInputFromWindow( ePrice.getWindowToken(), 0 );
    }








}
