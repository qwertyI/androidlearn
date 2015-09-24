package com.example.qwerty.learn;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class LvActivity extends Activity {

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lv);
        InitFruit();
        FruitAdapter adapter = new FruitAdapter(this, R.layout.activity_lv_item, fruitList);
        ListView fruit_lv = (ListView) findViewById(R.id.fruit_lv);
        fruit_lv.setAdapter(adapter);
        fruit_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position = position + 1;
                Toast.makeText(LvActivity.this, "Click"+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class FruitAdapter extends ArrayAdapter<Fruit>{
        private int resourceId;

        public FruitAdapter(Context context, int textViewResourceId, List<Fruit> objects){
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group){
            Fruit fruit = getItem(position);
            View view = convertView;
            ViewHolder viewHolder;
            if(view == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
                viewHolder = new ViewHolder();
                viewHolder.fruitId = (TextView) view.findViewById(R.id.fruit_id);
                viewHolder.fruitName = (TextView) view.findViewById(R.id.fruit_name);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.fruitId.setText(fruit.getFruitId());
            viewHolder.fruitName.setText(fruit.getFruitName());
            return view;
        }
    }

    class ViewHolder{
        TextView fruitId;
        TextView fruitName;
    }


    public class Fruit{
        private String fruitId;
        private String fruitName;

        public Fruit(String id, String name){
            this.fruitId = id;
            this.fruitName = name;
        }
        public String getFruitId(){
            return fruitId;
        }
        public String getFruitName(){
            return fruitName;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lv, menu);
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

    public void InitFruit(){
        Fruit apple = new Fruit(1111+"", "apple");
        fruitList.add(apple);
        Fruit apple1 = new Fruit(1111+"", "apple");
        fruitList.add(apple1);
        Fruit apple2 = new Fruit(1111+"", "apple");
        fruitList.add(apple2);
        Fruit apple3 = new Fruit(1111+"", "apple");
        fruitList.add(apple3);
        Fruit apple4 = new Fruit(1111+"", "apple");
        fruitList.add(apple4);
        Fruit apple5 = new Fruit(1111+"", "apple");
        fruitList.add(apple5);
        Fruit apple6 = new Fruit(1111+"", "apple");
        fruitList.add(apple6);
        Fruit apple7 = new Fruit(1111+"", "apple");
        fruitList.add(apple7);
        Fruit apple8 = new Fruit(1111+"", "apple");
        fruitList.add(apple8);
        Fruit apple9 = new Fruit(1111+"", "apple");
        fruitList.add(apple9);
        Fruit apple10 = new Fruit(1111+"", "apple");
        fruitList.add(apple10);
        Fruit apple11 = new Fruit(1111+"", "apple");
        fruitList.add(apple11);
        Fruit apple12 = new Fruit(1111+"", "apple");
        fruitList.add(apple12);
        Fruit apple13 = new Fruit(1111+"", "apple");
        fruitList.add(apple13);
        Fruit apple14 = new Fruit(1111+"", "apple");
        fruitList.add(apple14);
        Fruit apple15 = new Fruit(1111+"", "apple");
        fruitList.add(apple15);
        Fruit apple16 = new Fruit(1111+"", "apple");
        fruitList.add(apple16);
        Fruit apple17 = new Fruit(1111+"", "apple");
        fruitList.add(apple17);
        Fruit apple18 = new Fruit(1111+"", "apple");
        fruitList.add(apple18);
        Fruit apple19 = new Fruit(1111+"", "apple");
        fruitList.add(apple19);
    }
}
