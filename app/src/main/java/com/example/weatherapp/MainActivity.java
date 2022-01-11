package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    TextView lon;
    TextView lat;
    TextView place1,place2,place3,temp1,temp2,temp3;
    TextView top;
    Button b;
    ImageView imageView1,imageView2,imageView3;
    TextView time1,time2,time3,date1,date2,date3,c1,c2,c3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lon = findViewById(R.id.edit_long);
        lat = findViewById(R.id.edit_lat);
        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        place3 = findViewById(R.id.place3);
        temp1 = findViewById(R.id.temp1);
        temp2 = findViewById(R.id.temp2);
        temp3 = findViewById(R.id.temp3);
        b= findViewById(R.id.button);
        top = findViewById(R.id.del);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        time1 = findViewById(R.id.time1);
        time2 = findViewById(R.id.time2);
        time3 = findViewById(R.id.time3);
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date3 = findViewById(R.id.date3);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);
        time1.setTextColor(Color.RED);
        time2.setTextColor(Color.RED);
        time3.setTextColor(Color.RED);
        date1.setTextColor(Color.BLUE);
        date2.setTextColor(Color.BLUE);
        date3.setTextColor(Color.BLUE);
        c1.setTextColor(Color.GREEN);
        c2.setTextColor(Color.GREEN);
        c3.setTextColor(Color.GREEN);
        place1.setTextColor(Color.MAGENTA);
        place2.setTextColor(Color.MAGENTA);
        place3.setTextColor(Color.MAGENTA);
        temp1.setTextColor(Color.CYAN);
        temp2.setTextColor(Color.CYAN);
        temp3.setTextColor(Color.CYAN);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadFilesTask().execute();
                Log.d("TAG2",lat.getText().toString());
            }
        });

    }
   private class DownloadFilesTask extends AsyncTask<URL, Void, JSONObject> {
        protected JSONObject doInBackground(URL...URL) {
            URL url = null;
            try {
                url = new URL("http://api.openweathermap.org/data/2.5/find?lat="+lat.getText()+"&lon="+lon.getText()+"&cnt=3&appid=fd9f01af31aa7878391b5b773b102017");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("TAG2","URL");
            }
            URLConnection urlConnection = null;
            try {
                urlConnection = url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG2","urlConnection");
            }
            InputStream inputStream = null;
            try {
                inputStream = urlConnection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("TAG2","inputStream");
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(line);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

       @Override
       protected void onPostExecute(JSONObject jsonObject) {
           try {
               JSONArray array = jsonObject.getJSONArray("list");
               for(int i =0;i<array.length();i++){
                   JSONObject object = array.getJSONObject(i);
                   if(i==0){
                       place1.setText(object.getString("name"));
                       Double co = Double.valueOf(object.getJSONObject("main").getString("temp"));
                       co = (co-273.15) * 9/5 + 32;
                       DecimalFormat twoDForm = new DecimalFormat("##.##");
                       co = Double.valueOf(twoDForm.format(co));
                       temp1.setText(co.toString()+ " F");
                       long epoch = object.getLong("dt");
                       Date date = new Date(epoch*1000L);
                       SimpleDateFormat ct = new SimpleDateFormat("MM/dd/yyyy");
                       String format = ct.format(date);
                       ct.setTimeZone(TimeZone.getTimeZone("EST"));
                       date1.setText(format);
                       SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a z");
                       String time = currentTime.format(date);
                       time1.setText(time);
                       c1.setText(object.getJSONArray("weather").getJSONObject(i).getString("description"));
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("cloud"))
                           imageView1.setImageResource(R.drawable.cloudy);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                           imageView1.setImageResource(R.drawable.rain);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("drizzle"))
                           imageView1.setImageResource(R.drawable.rain);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                           imageView1.setImageResource(R.drawable.snow);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                           imageView1.setImageResource(R.drawable.sunny);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("thunder"))
                           imageView1.setImageResource(R.drawable.thunder);

                   }
                   if(i==1){
                       place2.setText(object.getString("name"));
                       Double co = Double.valueOf(object.getJSONObject("main").getString("temp"));
                       co = (co-273.15) * 9/5 + 32;
                       DecimalFormat twoDForm = new DecimalFormat("##.##");
                       co = Double.valueOf(twoDForm.format(co));
                       temp2.setText(co.toString()+ " F");
                       long epoch = object.getLong("dt");
                       Date date = new Date(epoch*1000L);
                       SimpleDateFormat ct = new SimpleDateFormat("MM/dd/yyyy");
                       String format = ct.format(date);
                       ct.setTimeZone(TimeZone.getTimeZone("EST"));
                       date2.setText(format);
                       SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a z");
                       String time = currentTime.format(date);
                       time2.setText(time);
                       c2.setText(object.getJSONArray("weather").getJSONObject(0).getString("description"));
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("cloud"))
                           imageView2.setImageResource(R.drawable.cloudy);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                           imageView2.setImageResource(R.drawable.rain);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("drizzle"))
                           imageView2.setImageResource(R.drawable.rain);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                           imageView2.setImageResource(R.drawable.snow);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                           imageView2.setImageResource(R.drawable.sunny);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("thunder"))
                           imageView2.setImageResource(R.drawable.thunder);
                   }
                   if(i==2){
                       place3.setText(object.getString("name"));
                       Double co = Double.valueOf(object.getJSONObject("main").getString("temp"));
                       co = (co-273.15) * 9/5 + 32;
                       DecimalFormat twoDForm = new DecimalFormat("##.##");
                       co = Double.valueOf(twoDForm.format(co));
                       temp3.setText(co.toString() + " F");
                       long epoch = object.getLong("dt");
                       Date date = new Date(epoch*1000L);
                       SimpleDateFormat ct = new SimpleDateFormat("MM/dd/yyyy");
                       String format = ct.format(date);
                       ct.setTimeZone(TimeZone.getTimeZone("EST"));
                       date3.setText(format);
                       SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a z");
                       String time = currentTime.format(date);
                       time3.setText(time);
                       c3.setText(object.getJSONArray("weather").getJSONObject(0).getString("description"));
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("cloud"))
                           imageView3.setImageResource(R.drawable.cloudy);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("rain"))
                           imageView3.setImageResource(R.drawable.rain);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("drizzle"))
                           imageView3.setImageResource(R.drawable.rain);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("snow"))
                           imageView3.setImageResource(R.drawable.snow);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("clear"))
                           imageView3.setImageResource(R.drawable.sunny);
                       if(object.getJSONArray("weather").getJSONObject(0).getString("main").toLowerCase().contains("thunder"))
                           imageView3.setImageResource(R.drawable.thunder);

                   }

               }

           } catch (JSONException e) {
               e.printStackTrace();
           }


       }
   }


}