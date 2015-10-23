package com.example.qwerty.learn.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.example.qwerty.learn.R;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MusciActivity extends Activity{

    private List<MusicName> Musics = new ArrayList<>();
    private File file;

    private ListView MyMusics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_musci);
        file = Environment.getExternalStorageDirectory();
        getName(file);
        MyMusics = (ListView) findViewById(R.id.music_lv);
        MusicAdapter adapter = new MusicAdapter(this, R.layout.activity_musci, Musics);
        MyMusics.setAdapter(adapter);
    }

    public class MusicAdapter extends ArrayAdapter<MusicName>{
        int resourceId;

        public MusicAdapter(Context context, int textViewResourceId, List<MusicName> objects){
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group){
            MusicName musicName = getItem(position);
            View view = convertView;
            ViewHolder viewHolder;
            if(view == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
                viewHolder = new ViewHolder();
                viewHolder.MusicName = (TextView) view.findViewById(R.id.music_name_tv);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.MusicName.setText(musicName.getName());
            return view;
        }
    }

    class ViewHolder{
        TextView MusicName;
    }

    public class MusicName{
        String MusicName;

        public MusicName(String mn){
            this.MusicName = mn;
        }

        public String getName(){
            return MusicName;
        }
    }

    public void getName(File file){
        File[] files = file.listFiles();
        if(files.length > 0){
            for(int i = 0; i < files.length; ++i){
                if (files[i].isDirectory()){
                    getName(files[i]);
                }else if (files[i].isFile()){
                    if (files[i].getName().endsWith(".mp3")){
                        Log.i("alarm-name", files[i].getName());
                        Log.i("alarm-name", files[i].getPath());
                        Musics.add(new MusicName(files[i].getName()));
                    }
                }
            }
        }else {
            return;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_musci, menu);
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
