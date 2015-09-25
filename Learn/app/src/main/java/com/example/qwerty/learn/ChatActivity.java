package com.example.qwerty.learn;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends Activity {

    private List<Message> messages = new ArrayList<Message>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        initMessage();
        for(Message message:messages){
            Log.i("chat",message.getMessageBody());
            Log.i("chat",message.getMessageType()+"");
        }
        final MessageAdapter adapter = new MessageAdapter(ChatActivity.this, R.layout.activity_chat_item, messages);
        final ListView listView = (ListView) findViewById(R.id.message_lv);
        listView.setAdapter(adapter);
        final EditText msg_et = (EditText) findViewById(R.id.message_et);
        Button send_msg = (Button) findViewById(R.id.message_send_btn);
        send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msg_et.getText().toString() == ""){
                    Toast.makeText(ChatActivity.this, "请输入您要发送的消息", Toast.LENGTH_SHORT).show();
                }
                else {
                    Message new_message = new Message(msg_et.getText().toString(), Message.SEND_MSG);
                    messages.add(new_message);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(messages.size());
                    msg_et.setText("");
                }
            }
        });
    }

    //消息适配器
    public class MessageAdapter extends ArrayAdapter<Message>{
        private int resourceId;

        public MessageAdapter(Context context, int textViewResourceId, List<Message> objects){
            super(context, textViewResourceId, objects);
            resourceId = textViewResourceId;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup group){
            Message message = getItem(position);
            View view = convertView;
            ViewHolder viewHolder;
            if (view == null){
                view = LayoutInflater.from(getContext()).inflate(resourceId,null);
                viewHolder = new ViewHolder();
                viewHolder.message_left_layout = (LinearLayout) view.findViewById(R.id.message_left_layout);
                viewHolder.message_left_tv = (TextView) view.findViewById(R.id.message_left_tv);
                viewHolder.message_right_layout = (LinearLayout) view.findViewById(R.id.message_right_layout);
                viewHolder.message_right_tv = (TextView) view.findViewById(R.id.message_right_tv);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) view.getTag();
            }
            if (message.getMessageType() == Message.RECEIVE_MSG){
                viewHolder.message_left_layout.setVisibility(View.VISIBLE);
                viewHolder.message_right_layout.setVisibility(View.GONE);
                viewHolder.message_left_tv.setText(message.getMessageBody());
                Log.i("chat",viewHolder.message_left_tv.getText().toString());
            }else if (message.getMessageType() == Message.SEND_MSG){
                viewHolder.message_left_layout.setVisibility(View.GONE);
                viewHolder.message_right_layout.setVisibility(View.VISIBLE);
                viewHolder.message_right_tv.setText(message.getMessageBody());
                Log.i("chat",viewHolder.message_right_tv.getText().toString());
            }
            return view;
        }
    }
    //消息容器类
    class ViewHolder{
        LinearLayout message_left_layout;
        LinearLayout message_right_layout;
        TextView message_left_tv;
        TextView message_right_tv;
    }

    //消息类
    class Message{
        private static final int RECEIVE_MSG = 0;
        private static final int SEND_MSG = 1;

        private String Message_Body;
        private int Message_Type;

        public Message(String message_Body, int type){
            this.Message_Body = message_Body;
            this.Message_Type = type;
        }
        public String getMessageBody(){
            return Message_Body;
        }
        public int getMessageType(){
            return Message_Type;
        }
    }

    private void initMessage() {
        Message msg1 = new Message("Hello guy.", Message.RECEIVE_MSG);
        messages.add(msg1);
        Message msg2 = new Message("Hello. Who is that?", Message.SEND_MSG);
        messages.add(msg2);
        Message msg3 = new Message("This is Tom. Nice talking to you. ", Message.RECEIVE_MSG);
        messages.add(msg3);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chat, menu);
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
