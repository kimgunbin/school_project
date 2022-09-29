package com.example.pocket.class_.database;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DbHelper {
    String result ="";
    byte [] result2 = {};
    public String connectServer(String url, String postText) {


        //보낼 주소
        String postUrl = url;

        Log.v("s1", result);

        //보낼 값

        MediaType mediaType = MediaType.parse("text/plain");
        //MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody postBody = RequestBody.create(mediaType, postText);

           result = postRequest(postUrl, postBody);

        return result;
    }

    public byte[] connectServerList(String url, String postText) {



        //보낼 주소
        String postUrl = url;

        //보낼 값

        MediaType mediaType = MediaType.parse("text/plain");
        //MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
        RequestBody postBody = RequestBody.create(mediaType, postText);

      result2 = postRequest2(postUrl, postBody);
        return result2;
    }




    String postRequest(String postUrl, RequestBody postBody) {



        Log.v("s2", result);
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();
                result = "Failed to Connect to Server";

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Log.v("s3", result);
                                result = response.body().string();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }).start();
            }
        });



        return result;
    }

    byte[] postRequest2(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();


                String fal = "Failed to Connect to Server";
                result2 = fal.getBytes(StandardCharsets.UTF_8);

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            result2 = response.body().bytes();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }).start();


            }
        });

        return result2;
    }
}
