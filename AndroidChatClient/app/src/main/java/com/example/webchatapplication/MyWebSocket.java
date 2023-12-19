package com.example.webchatapplication;

import android.util.Log;
import android.widget.Adapter;
import android.widget.ListView;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class MyWebSocket extends WebSocketAdapter {

    WebSocketAdapter Adapter = new WebSocketAdapter();
    String WSA = String.valueOf(Adapter);
    static boolean wsIsOpen = false;

    //ListView lv = lv_.findViewById();

    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        Log.d(WSA, "On Connect");
        Log.i("Connection","Connected" );
        wsIsOpen = true;
    }

    @Override
    public void onConnectError(WebSocket websocket, WebSocketException exception) throws Exception {
        Log.d(WSA, "Web Socket Error");
        //super.onConnectError(websocket, exception);
    }

    @Override
    public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame,
                               WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
        Log.d(WSA, "On Disconnect");
        wsIsOpen = false;
        //super.onDisconnected(websocket, serverCloseFrame, clientCloseFrame, closedByServer);
    }

    @Override
    public void onTextMessage(WebSocket websocket, String message) throws Exception {
        if (wsIsOpen) {
            Log.d(WSA, "on text message:" + message);

            String newMessage = null ;
            JSONObject json = new JSONObject(message);
            Log.d("Check", json.toString());
            String type = (String) json.get("type");

            if ("join".equals(type)) {
                newMessage = (String)json.get("user") + " has joined the " + (String)json.get("room");
            }

            if ("leave".equals(type)) {
                newMessage = (String)json.get("user") + " has left the " + (String)json.get("room");
            }

            if ("message".equals(type)) {
                newMessage = (String)json.get("user") + ": " + (String)json.get("message");
            }

            ChatRoomActivity.listOfMessages.add(newMessage) ;
            ChatRoomActivity.lv_.post(() -> {
                ChatRoomActivity.adapter.notifyDataSetChanged();
                ChatRoomActivity.lv_.smoothScrollToPosition(ChatRoomActivity.adapter.getCount());
            });

            //super.onTextMessage(websocket, text);
            //get message
            //add it to my list
            // post msg to UI thread to update the display of msg list
        }
    }
}
