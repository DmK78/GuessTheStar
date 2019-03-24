package com.android.dmk78.guessthestar;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ArrayList<Star> arrayListStars;
    private String myUrl = "http://www.posh24.se/kandisar";
    private String resultUrl;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // скачиваем веб страницу целиком
        DownloadTask task = new DownloadTask();
        try {
            resultUrl = task.execute(myUrl).get();
            //Log.i("myURL",resultUrl);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // вытаскиваем имена и адреса картинок
        Pattern patternImg = Pattern.compile("img src=\"(.*?)\" alt=");
        Pattern patternName = Pattern.compile("alt=\"(.*?)\"");

        Matcher matcherImg = patternImg.matcher(resultUrl);
        Matcher matcherName = patternName.matcher(resultUrl);
        String url;
        String name;
        int indexJava; //нахождение подстроки в строке

        while (matcherImg.find()) {

            matcherName.find();
            name = matcherName.group(1);
            url = matcherImg.group(1);
            Log.i("Myname", name);
            indexJava = url.indexOf("http://cdn.posh24.se/images/:profile/");

            if (indexJava != -1) {
                Log.i("Myname", url);



            }


        }



      /*  count=0;
        while (matcherName.find()) {
            Log.i("Myname", matcherName.group(1));
            name = matcherName.group(1);
            count++;
        }

        Log.i("Myname", String.valueOf(count));*/

    }

    private static class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder resultUrl = new StringBuilder();
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    resultUrl.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return resultUrl.toString();


        }
    }
}
