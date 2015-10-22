package com.example.qwerty.learn.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qwerty.learn.R;


public class GetPerferencesActivity extends Activity implements OnClickListener{

    private SharedPreferences.Editor editor;
    private SharedPreferences gpf;

    private Button write_gpf;
    private Button get_gpf;
    private EditText get_data_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_get_perferences);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        gpf = getSharedPreferences("data", MODE_PRIVATE);
        write_gpf = (Button) findViewById(R.id.write_gpf_btn);
        get_gpf = (Button) findViewById(R.id.get_gpf_btn);
        get_data_name = (EditText) findViewById(R.id.user_name_et);
        write_gpf.setOnClickListener(this);
        get_gpf.setOnClickListener(this);
        String name = gpf.getString("name", "");
        Log.i("gpf", name);
        if (name != ""){
            get_data_name.setText(name);
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.write_gpf_btn:
                editor.putString("phone", "15814477659");
                editor.putString("name", "qwerty");
                editor.putInt("age", 23);
                editor.commit();
                Toast.makeText(this, "写入数据", Toast.LENGTH_SHORT).show();
                break;
            case R.id.get_gpf_btn:
                Log.i("gpf", gpf.getString("phone", ""));
                Log.i("gpf", gpf.getString("name", ""));
                Log.i("gpf", gpf.getInt("age", 18)+"");
                Log.i("gpf", gpf.getString("no_phone", "123456"));
                break;
            default:break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_perferences, menu);
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
