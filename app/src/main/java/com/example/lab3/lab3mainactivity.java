package com.example.lab3;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class lab3mainactivity extends AppCompatActivity {
    EditText txt1,txt2;
    Button btn1;
    TextView tvKQ;
    String path="https://batdongsanabc.000webhostapp.com/mob403/demo2_api_get.php";
    String pathPOST="https://batdongsanabc.000webhostapp.com/mob403/demo2_api_post.php";
    String kq="";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab3mainactivity);
        txt1=findViewById(R.id.lab3Txt1);
        txt2=findViewById(R.id.lab3Txt2);
        btn1=findViewById(R.id.lab3Btn1);
        tvKQ=findViewById(R.id.lab3Tv1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // new GETAsyncTask().execute();
                new GETAsyncTask().execute();
            }
        });
    }
    class POSTAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url=new URL(pathPOST);
                String param="canh="+ URLEncoder.encode(txt1.getText().toString(),"utf-8");
                HttpURLConnection urlConnection
                        =(HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                urlConnection.setFixedLengthStreamingMode(param.getBytes().length);
                urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                PrintWriter printWriter=new PrintWriter(urlConnection.getOutputStream());
                printWriter.print(param);
                printWriter.close();
                String line="";
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder =new StringBuilder();
                while ((line=br.readLine())!=null){
                    stringBuilder.append(line);
                }
                kq=stringBuilder.toString();
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            tvKQ.setText(kq);
        }
    }
    class GETAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            path+="?name="+txt1.getText().toString()+"&mark="+txt2.getText().toString();
            try {
                URL url=new URL(path);
                BufferedReader br=new BufferedReader(new
                        InputStreamReader(url.openConnection().getInputStream()));
                String line="";
                StringBuilder stringBuilder=new StringBuilder();
                while ((line=br.readLine())!=null){
                    stringBuilder.append(line);
                }
                kq=stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            tvKQ.setText(kq);
        }
    }
}