package com.example.qwerty.learn;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {

    private Button open_camera;
    private Button open_my_lv;
    private Button open_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        open_camera = (Button) findViewById(R.id.open_camera_btn);
        open_my_lv = (Button) findViewById(R.id.open_my_lv_btn);
        open_chat = (Button) findViewById(R.id.open_chat_btn);
        open_camera.setOnClickListener(this);
        open_my_lv.setOnClickListener(this);
        open_chat.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.open_camera_btn:
                Intent startAPP = getPackageManager().getLaunchIntentForPackage("com.huawei.camera");
                startActivity(startAPP);
                break;
            case R.id.open_my_lv_btn:
                Intent open_lv_intent = new Intent(MainActivity.this, LvActivity.class);
                startActivity(open_lv_intent);
                break;
            case R.id.open_chat_btn:
                Intent open_chat_intent = new Intent(MainActivity.this, ChatActivity.class);
                startActivity(open_chat_intent);
                break;
            default:break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
