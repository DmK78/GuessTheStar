package com.android.dmk78.guessthestar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.service.autofill.FieldClassification;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
    private ImageView imageView;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    String guess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        arrayListStars = new ArrayList<>();
        button1 = findViewById(R.id.buttonStar1);
        button2 = findViewById(R.id.buttonStar2);
        button3 = findViewById(R.id.buttonStar3);
        button4 = findViewById(R.id.buttonStar4);


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
        Bitmap img;
        int indexJava; //нахождение подстроки в строке

        while (matcherImg.find()) {

            matcherName.find();
            name = matcherName.group(1);
            url = matcherImg.group(1);
            //Log.i("Myname", name);
            // проверяем, чтобы в найденной строке было это совпадение
            indexJava = url.indexOf("http://cdn.posh24.se/images/:profile/");
            if (indexJava != -1) {
                //  Log.i("Myname", url);

                DownloadImageTask taskImage = new DownloadImageTask();
                img = null;

                try {

                    img = taskImage.execute(url).get();
                    //добавляем обьекты в массив
                    arrayListStars.add(new Star(name, img));


                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //Log.i("Myname", arrayListStars.toString());
        //Log.i("Myname", String.valueOf(arrayListStars.size()));

randomStar();






    }

    void randomStar(){

        int randomStarindex1 = (int) (Math.random() * arrayListStars.size());
        Bitmap randomTargetImg1 = arrayListStars.get(randomStarindex1).getImg();
        String randomTargetName1 = arrayListStars.get(randomStarindex1).getName();
        button1.setText(randomTargetName1);

        int randomStarindex2 = (int) (Math.random() * arrayListStars.size());
        Bitmap randomTargetImg2 = arrayListStars.get(randomStarindex2).getImg();
        String randomTargetName2 = arrayListStars.get(randomStarindex2).getName();
        button2.setText(randomTargetName2);

        int randomStarindex3 = (int) (Math.random() * arrayListStars.size());
        Bitmap randomTargetImg3 = arrayListStars.get(randomStarindex3).getImg();
        String randomTargetName3 = arrayListStars.get(randomStarindex3).getName();
        button3.setText(randomTargetName3);

        int randomStarindex4 = (int) (Math.random() * arrayListStars.size());
        Bitmap randomTargetImg4 = arrayListStars.get(randomStarindex4).getImg();
        String randomTargetName4 = arrayListStars.get(randomStarindex4).getName();
        button4.setText(randomTargetName4);


        int randomImgFromButtons = (int)((Math.random()*4)+1);

        switch (randomImgFromButtons){
            case 1:
                imageView.setImageBitmap(randomTargetImg1);
                guess=randomTargetName1;

                break;
            case 2:
                imageView.setImageBitmap(randomTargetImg2);
                guess=randomTargetName2;
                break;
            case 3:
                imageView.setImageBitmap(randomTargetImg3);
                guess=randomTargetName3;
                break;
            case 4:
                imageView.setImageBitmap(randomTargetImg4);
                guess=randomTargetName4;
                break;

            default:break;

        }
        //imageView.setImageBitmap(randomTargetImg);
        button1.setText(randomTargetName1);
        //Log.i("Mynamerandom", randomTargetName);







    }

    public void guess(View view) {

        Button button = (Button) view;
        String answer = button.getText().toString();
        Log.i("Answer",answer+" : "+guess);
        if (answer==guess){
            Toast.makeText(getApplicationContext(),"Wonderful!!!!",Toast.LENGTH_LONG).show();
            randomStar();
        }





    }



    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                return bmp;


            } catch (MalformedURLException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}
