package com.example.pocket.class_.database;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.pocket.activity.LoginActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class LoginCheck {
     private void loginCheck(final String userName, final String passWord, final Context context) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loadingDialog = ProgressDialog.show(context, "Please wait", "Loading...");
            }
 
            @Override
            protected String doInBackground(String... params) {
 
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", params[0]));
                nameValuePairs.add(new BasicNameValuePair("password", params[1]));
         
                String result = null;
 
                try {
                    HttpClient httpClient = SessionControl.getHttpclient();
                    HttpPost httpPost = new HttpPost("http://서버에게 로그인 인증할 주소.co.kr");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();
 
                    is = entity.getContent();
 
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();
 
 
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }
 
            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                loadingDialog.dismiss();
                if (s.equalsIgnoreCase("OK")) {
                    Toast.makeText(context.getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                    SessionControl.cookies = SessionControl.httpclient.getCookieStore().getCookies();
                    if (!SessionControl.cookies.isEmpty()) {
                        Intent i = new Intent(context.getApplicationContext(), LoginActivity.class);
                        i.putExtra("myurl", "http://로그인할 주소.co.kr");
                        context.startActivity(i);
                    }
 
                } else {
                    Toast.makeText(context.getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }
        }
 
        new LoginAsync().execute(userName, passWord);
 
    }

}
