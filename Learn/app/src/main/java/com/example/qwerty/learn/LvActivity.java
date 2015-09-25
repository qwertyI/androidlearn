package com.example.qwerty.learn;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
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

    private List<Contact> contactList = new ArrayList<>();
    private static final String[] PHONES_PROJECT = {Phone.DISPLAY_NAME, Phone.NUMBER};

    private static final int PHONE_DISPLAY_NAME_INDEX = 0;
    private static final int PHONE_NUM_INDEX = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lv);
        getContacts();
        FruitAdapter adapter = new FruitAdapter(this, R.layout.activity_lv_item, contactList);
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

    public class FruitAdapter extends ArrayAdapter<Contact>{
        private int resourceId;

        public FruitAdapter(Context context, int textViewResourceId, List<Contact> objects){
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group){
            Contact contact = getItem(position);
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
            viewHolder.fruitId.setText(contact.getContactName());
            viewHolder.fruitName.setText(contact.getContactPhone());
            return view;
        }
    }

    class ViewHolder{
        TextView fruitId;
        TextView fruitName;
    }

    public void getContacts(){
        ContentResolver contentResolver = getContentResolver();
        Cursor PhoneCursor = contentResolver.query(Phone.CONTENT_URI, PHONES_PROJECT, null, null,null);
        if (PhoneCursor != null){
            while (PhoneCursor.moveToNext()){
                String phoneNum = PhoneCursor.getString(PHONE_DISPLAY_NAME_INDEX);
                String phoneName = PhoneCursor.getString(PHONE_NUM_INDEX);
                InitContacts(phoneNum, phoneName);
            }
        }
    }


    public class Contact{
        private String ContactPhone;
        private String ContactName;

        public Contact(String ContactPhone, String ContactName){
            this.ContactPhone = ContactPhone;
            this.ContactName = ContactName;
        }
        public String getContactPhone(){
            return ContactPhone;
        }
        public String getContactName(){
            return ContactName;
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

    public void InitContacts(String phone, String name){
        Contact contact = new Contact(phone, name);
        contactList.add(contact);
    }

}
