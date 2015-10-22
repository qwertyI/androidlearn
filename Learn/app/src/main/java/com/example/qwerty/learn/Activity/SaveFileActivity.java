package com.example.qwerty.learn.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.qwerty.learn.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;


public class SaveFileActivity extends Activity implements OnClickListener {

    private static final String MYFILE_NAME = "myfile.txt";

    private EditText SaveFileEdit;
    private Button SaveFileBtn;
    private String FileName;
    private String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_save_file);
        SaveFileEdit = (EditText) findViewById(R.id.save_file_et);
        SaveFileBtn = (Button) findViewById(R.id.save_file_btn);
        SaveFileBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.save_file_btn:
                FileName = MYFILE_NAME;
                data = SaveFileEdit.getText().toString();
                Save(FileName, data);
                Toast.makeText(SaveFileActivity.this, "保存文件", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

//    @Override
//    protected void onDestroy(){
//        super.onDestroy();
//        String data = SaveFileEdit.getText().toString();
//        Save("data", data);
//    }

    public void Save(String FileName, String data){
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput(FileName, Context.MODE_APPEND);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                if (writer != null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save_file, menu);
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
