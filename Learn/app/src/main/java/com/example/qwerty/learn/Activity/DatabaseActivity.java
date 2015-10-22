package com.example.qwerty.learn.Activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.qwerty.learn.MyDatabaseHelper;
import com.example.qwerty.learn.R;
import java.io.DataOutputStream;
import java.io.IOException;


public class DatabaseActivity extends Activity implements OnClickListener{

    private static final int CURSOR_NO_EXIST = 0;
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_BOOK = "book";
    private static final String TABLE_BOOK_NAME = "name";
    private static final String TABLE_BOOK_AUTHOR = "author";
    private static final String TABLE_BOOK_PRICE = "price";
    private static final String TABLE_BOOK_PAGE = "page";

    private MyDatabaseHelper dbHelper;
    private Cursor cursor;
    private SQLiteDatabase db;
    private int Cursor_Position = 0;

    private Button insert_book_btn;
    private Button show_data_btn;
    private Button delete_data_btn;
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
        db = dbHelper.getWritableDatabase();
        insert_book_btn = (Button) findViewById(R.id.insert_book_btn);
        show_data_btn = (Button) findViewById(R.id.show_data_btn);
        delete_data_btn = (Button) findViewById(R.id.delete_data_btn);

        book_name_et = (EditText) findViewById(R.id.book_name_et);
        book_author_et = (EditText) findViewById(R.id.book_author_et);
        book_price_et = (EditText) findViewById(R.id.book_price_et);
        book_page_et = (EditText) findViewById(R.id.book_page_et);

        book_price_et.setInputType(EditorInfo.TYPE_CLASS_PHONE);
        book_page_et.setInputType(EditorInfo.TYPE_CLASS_PHONE);

        insert_book_btn.setOnClickListener(this);
        show_data_btn.setOnClickListener(this);
        delete_data_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.insert_book_btn:
                if (Check_Empty()){
                    name = book_name_et.getText().toString();
                    author = book_author_et.getText().toString();
                    price = Double.parseDouble(book_price_et.getText().toString());
                    page = Integer.parseInt(book_page_et.getText().toString());
                    if (true){
                        ContentValues values = new ContentValues();
                        Insert_Book(values, name, author, price, page);
                        Toast.makeText(this, "Insert Success", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Data in the book", Toast.LENGTH_SHORT).show();
                    }
                }
//                    Cursor_Position = cursor.getPosition();
//                    cursor = null;
                break;
            case R.id.show_data_btn:
                if (cursor == null){
                    cursor = db.query("book",null,null,null,null,null,null);
                }
                if (Cursor_Position != CURSOR_NO_EXIST){
                    cursor.moveToPosition(Cursor_Position);
                    Cursor_Position = CURSOR_NO_EXIST;
                }
                if (cursor.moveToNext()){
                    Show_Book(cursor);
                }else {
                    Toast.makeText(this, "No Nextdata", Toast.LENGTH_SHORT).show();
                    cursor.close();
                    cursor = null;
                }
                break;
            case R.id.delete_data_btn:
                if(cursor != null){
                    db.delete("book", "id = ?", new String[]{cursor.getString(cursor.getColumnIndex("id"))});
                    if(cursor.moveToNext()){
                        Show_Book(cursor);
                    }else {
                        book_name_et.setText("");
                        book_author_et.setText("");
                        book_price_et.setText("");
                        book_page_et.setText("");
                        cursor = null;
                    }

                }else {
                    Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }



    private Boolean Check_In_Book(String name){
        cursor = db.rawQuery("select * from book where name = ?", new String[]{name});

//        cursor = db.query("book", null, "name = ?", new String[]{name}, null, null, null);
        Log.i("database", cursor.getPosition()+"");
        if (cursor.getString(cursor.getColumnIndex("name")) == name){
            cursor.close();
            cursor = null;
            return true;
        }
        cursor.close();
        cursor = null;
        return false;
    }

    private Boolean Check_Empty(){
        if (!TextUtils.isEmpty(book_name_et.getText())){
            if (!TextUtils.isEmpty(book_author_et.getText())){
                if(!TextUtils.isEmpty(book_price_et.getText())){
                    if (!TextUtils.isEmpty(book_page_et.getText())){
                        return true;
                    }else {
                        Toast.makeText(this, "Please Check !", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Please Check !", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Please Check !", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please Check !", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void Show_Book(Cursor cursor){
        name = cursor.getString(cursor.getColumnIndex(TABLE_BOOK_NAME));
        author = cursor.getString(cursor.getColumnIndex(TABLE_BOOK_AUTHOR));
        String sPrice = cursor.getString(cursor.getColumnIndex(TABLE_BOOK_PRICE));
        String sPage = cursor.getString(cursor.getColumnIndex(TABLE_BOOK_PAGE));
        book_name_et.setText(name);
        book_author_et.setText(author);
        book_price_et.setText(sPrice);
        book_page_et.setText(sPage);
    }

    private void Insert_Book(ContentValues values, String name, String author, Double price, int page){
        values.put("name", name);
        values.put("author", author);
        values.put("price", price);
        values.put("page", page);
        db.insert("book", null, values);
    }

    public static void suShell(String cmd) {
        Process ps = null;
        DataOutputStream os;

        try {
            ps = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(ps.getOutputStream());

            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
