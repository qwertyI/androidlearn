package com.example.qwerty.learn;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity implements OnClickListener {

    private NotificationManager mManager;

    private Button open_camera;
    private Button open_my_lv;
    private Button open_chat;
    private Button open_save_file;
    private Button open_database;
    private Button open_gpf;
    private Button cant_see_it;
    private TextView csi_tv;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Intent serviceIntent = new Intent(this, CalarmService.class);
        startService(serviceIntent);
        animation = AnimationUtils.loadAnimation(this, R.anim.gradually);
        open_camera = (Button) findViewById(R.id.open_camera_btn);
        open_my_lv = (Button) findViewById(R.id.open_my_lv_btn);
        open_chat = (Button) findViewById(R.id.open_chat_btn);
        open_save_file = (Button) findViewById(R.id.open_save_file_btn);
        open_database = (Button) findViewById(R.id.open_database_btn);
        open_gpf = (Button) findViewById(R.id.open_gpf_btn);
        cant_see_it = (Button) findViewById(R.id.cant_see_it_btn);
        csi_tv = (TextView) findViewById(R.id.cant_see_it_tv);
        open_camera.setOnClickListener(this);
        open_my_lv.setOnClickListener(this);
        open_chat.setOnClickListener(this);
        open_save_file.setOnClickListener(this);
        open_database.setOnClickListener(this);
        open_gpf.setOnClickListener(this);
        cant_see_it.setOnClickListener(this);
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
            case R.id.open_save_file_btn:
                Intent open_save_file_intent = new Intent(MainActivity.this, SaveFileActivity.class);
                startActivity(open_save_file_intent);
                break;
            case R.id.open_database_btn:
                Intent open_database_intent = new Intent(MainActivity.this, DatabaseActivity.class);
                startActivity(open_database_intent);
                break;
            case R.id.open_gpf_btn:
                Intent open_gpf_intent = new Intent(MainActivity.this, GetPerferencesActivity.class);
                startActivity(open_gpf_intent);
                break;
            case R.id.cant_see_it_btn:
                csi_tv.startAnimation(animation);
                Intent intent = new Intent();
                intent.setAction("android.qwerty.learn.timecalarm");
                this.sendBroadcast(intent);
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
