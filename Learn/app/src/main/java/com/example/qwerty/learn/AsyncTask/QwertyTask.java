package com.example.qwerty.learn.AsyncTask;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by wei on 2015/11/24.
 */
public class QwertyTask extends AsyncTask<Void ,Integer ,Boolean >{

    private static final String TAG = "ASYNC_TASK";
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private Context mContext;
    private int i = 0;

    public QwertyTask(Context context){
        progressDialog = new ProgressDialog(context, 0);
        progressBar = new ProgressBar(context);
        mContext = context;
    }

    public int doDownload(){
        return i++;
    }

    //onPreExecute方法用于在执行后台任务前做一些UI操作
    @Override
    protected void onPreExecute() {
        Log.i(TAG, "onPreExecute() called");
        progressDialog.show();
    }

    //doInBackground方法内部执行后台任务,不可在此方法内修改UI
    @Override
    protected Boolean doInBackground(Void... params) {
        Log.i(TAG, "doInBackground(Params... params) called");
        try {
            while (true){
                int downloadPercent = doDownload();
                Thread.sleep(1000);
                publishProgress(downloadPercent);
                if (downloadPercent >= 100){
                    break;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return false;
        }
        return true;
    }

    //onProgressUpdate方法用于更新进度信息
    @Override
    protected void onProgressUpdate(Integer... progresses) {
        Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
        progressDialog.setMessage("Download " + progresses[0] + "%");
    }

    //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
    @Override
    protected void onPostExecute(Boolean result) {
        Log.i(TAG, "onPostExecute(Result result) called");
        progressDialog.dismiss();
        if (result){
            Toast.makeText(mContext, "Download Succeessed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Download failed", Toast.LENGTH_SHORT).show();
        }
    }

//    //onCancelled方法用于在取消执行中的任务时更改UI
//    @Override
//    protected void onCancelled() {
//        Log.i(TAG, "onCancelled() called");
//        textView.setText("cancelled");
//        progressBar.setProgress(0);
//
//        execute.setEnabled(true);
//        cancel.setEnabled(false);
//    }
}
