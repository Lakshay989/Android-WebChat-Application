package com.example.webchatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.neovisionaries.ws.client.WebSocket;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    static ArrayList<String> listOfMessages = new ArrayList<String>() ;

    public static ArrayAdapter<String> adapter ;

    static ListView lv_ ; //= findViewById(R.id.chatLV);

    String joinmessage ;
    String toServer ;
    String userName ;

    Bundle extras ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        TextView roomName = findViewById(R.id.ChatApp)   ;

        extras = getIntent().getExtras();

        if (extras != null) {
// The key argument below must match that used in the other activity }
            roomName.setText(extras.getString("my key"));
        }
        lv_ = findViewById(R.id.chatLV);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfMessages);
        lv_.setAdapter(adapter);
    }

    public void handleClickMsg(View view)
    {
        TextView message = findViewById(R.id.messageText) ;

        String messageText = message.getText().toString() ;

//        if(MyWebSocket.wsIsOpen)
//        {
            MainActivity.ws_.sendText(extras.get("my key1").toString() + " " + messageText) ;
     //   }
    }


}