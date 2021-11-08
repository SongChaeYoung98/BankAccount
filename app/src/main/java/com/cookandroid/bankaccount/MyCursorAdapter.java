package com.cookandroid.bankaccount;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MyCursorAdapter extends CursorAdapter {

    private DecimalFormat df;       //화폐단위 적용하고싶다.





    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
    }

    //리스트뷰에 표시될 뷰 반환
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from( context );
        View v = inflater.inflate( R.layout.list_item, parent,false );  //list item - xml 이름
        return v;

    }

    //뷰의 속성 지정
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView item_context = (TextView) view.findViewById( R.id.item_context );
        TextView item_price = (TextView) view.findViewById( R.id.item_price );

        //getColumnindex(name) : name에 해당하는 필드의 인덱스 번호를 반환한다.
        //cursor.getString(index) : 해당 커서가 위치한 인덱스 위치의 값을 반환한다.

        df = new DecimalFormat("###,###");
        df.setDecimalSeparatorAlwaysShown(true);        //화폐 단위 넣고싶은데 어떻게 적용하나

        String contexts = cursor.getString( cursor.getColumnIndex( entry.KEY_CONTEXT ) );
        String price = cursor.getString( cursor.getColumnIndex( entry.KEY_PRICE ) );




        item_context.setText(contexts);
        item_price.setText(price);

    }









}

