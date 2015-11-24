package com.example.qwerty.learn.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.example.qwerty.learn.AsyncTask.QwertyTask;
import com.example.qwerty.learn.R;

public class ShowDownloadActivity extends Activity {

    private Button show_download_btn;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_download);

        mContext = ShowDownloadActivity.this;

        show_download_btn = (Button) findViewById(R.id.show_download_btn);
        show_download_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new QwertyTask(mContext).execute();
            }
        });
    }

}
