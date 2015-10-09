package com.example.qwerty.learn;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


public class DatabaseActivity extends Activity implements OnClickListener{

    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_BOOK = "book";

    private MyDatabaseHelper dbHelper;

    private Button insert_book_btn;
    private EditText book_name_et;
    private EditText book_author_et;
    private EditText book_price_et;
    private EditText book_page_et;

    private String name;
    private String author;
    private double price;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_database);
        dbHelper = new MyDatabaseHelper(this, TABLE_BOOK, null, DATABASE_VERSION);
        insert_book_btn = (Button) findViewById(R.id.insert_book_btn);
        book_name_et = (EditText) findViewById(R.id.book_name_et);
        book_author_et = (EditText) findViewById(R.id.book_author_et);
        book_price_et = (EditText) findViewById(R.id.book_price_et);
        book_page_et = (EditText) findViewById(R.id.book_page_et);
        insert_book_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.insert_book_btn:
                name = book_name_et.getText().toString();
                author = book_author_et.getText().toString();
                price = Double.parseDouble(book_price_et.getText().toString());
                page = Integer.parseInt(book_page_et.getText().toString());
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("author", author);
                values.put("price", price);
                values.put("page", page);
                db.insert("book", null, values);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
