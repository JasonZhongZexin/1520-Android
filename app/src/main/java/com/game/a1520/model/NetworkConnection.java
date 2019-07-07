package com.game.a1520.model;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class NetworkConnection {

    public NetworkConnection() {
    }

    public static void getOpponentFromServer(String api_url, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(api_url).build();
        client.newCall(request).enqueue(callback);
    }
}
