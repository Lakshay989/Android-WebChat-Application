package com.example.webchatapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketFactory;

import java.io.IOException;
import java.security.Key;

public class MainActivity extends AppCompatActivity {

//    private boolean switchActivities = false ;
    static WebSocket ws_ = null ;
    String toServer ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton enterBtn = (MaterialButton) findViewById(R.id.enterBtn) ;

        try {
            ws_ = new WebSocketFactory().createSocket("ws://10.0.2.2:8080/endpoint",1000) ;
        } catch (IOException e) {
            Log.e("Debug", "WebSocket Error") ;
        }

        ws_.addListener(new MyWebSocket()) ;
        ws_.connectAsynchronously() ;
    }

    public void handleClick(View view) {
        Log.i("Debug", "Click");
        EditText roomName = findViewById(R.id.roomname);
        EditText username = findViewById(R.id.username);


        Intent intent = new Intent(this, ChatRoomActivity.class);
        intent.putExtra("my key", roomName.getText().toString());
        intent.putExtra("my key1", username.getText().toString());
        startActivity(intent);

        //Log.d("CheckCheck", username)

//
        toServer = "join "+ username.getText().toString()+" "+roomName.getText().toString();
        ws_.sendText(toServer) ;
//        try {
//            ws_ = new WebSocketFactory().createSocket("ws://10.0.2.2:8080/endpoint",1000) ;
//        } catch (IOException e) {
//            Log.e("Debug", "WebSocket Error") ;
//        }
//
//        ws_.addListener(new MyWebSocket()) ;
//        ws_.connectAsynchronously() ;
//        Log.d("Debug","WebSocket Asynchronous connection called") ;

        }
    }


