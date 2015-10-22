package com.example.qwerty.learn.Activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.qwerty.learn.R;
import com.example.qwerty.learn.Service.CalarmService;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;


public class MainActivity extends Activity implements OnClickListener {

    private static final int INTERVAL = 1000*60*60*24;

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
//        serviceIntent.setAction("android.qwerty.learn.secondcalarm");
//        PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
//        AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 11);
//        calendar.set(Calendar.MINUTE, 20);
//        calendar.set(Calendar.SECOND, 30);
//        calendar.set(Calendar.MILLISECOND, 0);
//
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), INTERVAL, pendingIntent);

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
                getName();
                csi_tv.startAnimation(animation);
                Intent intent = new Intent();
                intent.setAction("android.qwerty.learn.timecalarm");
                this.sendBroadcast(intent);
                break;
            default:break;
        }
    }

    public class FileNameSelector implements FilenameFilter {
        String extension = ".";
        public FileNameSelector(String fileExtensionNoDot) {
            extension += fileExtensionNoDot;
        }

        public boolean accept(File dir, String name) {
            return name.endsWith(extension);
        }
    }

    public void getName(){
        File file = Environment.getExternalStorageDirectory();

        if(file != null){
            File[] files = file.listFiles(new FileNameSelector("ver"));
            for(int i = 0; i < files.length; ++i){
                if(files[i].isDirectory()){
                    getName();
                }
                Log.i("alarm-name", files[i].getName());
                Log.i("alarm-path", files[i].getPath());
            }
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
