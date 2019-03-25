package com.android.dmk78.guessthestar;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {

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