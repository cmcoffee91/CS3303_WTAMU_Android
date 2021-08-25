package com.djia.cc916647.djia;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/*
Christopher Coffee
cc916647
CS3303
Lab 6

I've used AsyncTask before plenty of times, but I never understood
the three parameters that are passed and the flow of them until you taught
it in class. I also learned how to use publishProgress. I tried using double but
progress bar only accepts integer so it round the 3.33 each time I used publish progress. Wasn't
sure on how to do this correctly, so I just used integers then published 100 at the end.
 */

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView display, textResult;
    private Button computeButton;
    final String endpoint = "https://query1.finance.yahoo.com/v8/finance/chart/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        display     = findViewById(R.id.textView);
        textResult  = findViewById(R.id.textResult);
        computeButton = findViewById(R.id.button);


        progressBar.setVisibility(View.GONE);
        textResult.setVisibility(View.GONE);

        computeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                textResult.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                display.setText("DJIA computation in progress ...");
                new ComputeDjia().execute();
            }
        });





    }




    private class ComputeDjia extends AsyncTask<String, Integer, String> {
        // double result;
        long begin, end, timeTaken;

        // int times;
        @Override
        protected String doInBackground(String... params) {

            String[] tickerList = {"AXP", "AAPL", "BA", "CAT", "CVX", "CSCO", "KO", "DIS", "DWDP", "XOM", "GE",
                    "GS", "HD", "IBM", "INTC", "JNJ", "JPM", "MCD", "MRK",  "MMM", "MSFT", "NKE", "PFE", "PG",  "TRV",  "UTX", "UNH", "VZ",
                    "V", "WMT"};


            int totalProgress = 0;
            int prog = 100/30;
            double sum = 0;
            double divisor = 0.14523396877348;
            for(int i = 0; i < tickerList.length; i++)
            {
                try {
                    String priceString = "";
                    Log.i("doInBackground", "Stock symbol is " + tickerList[i]);
                    URL url = new URL(endpoint + tickerList[i] + "?interval=2m");
                    BufferedReader bufferedReader =  new BufferedReader(new InputStreamReader(url.openStream()));
                    String line = bufferedReader.readLine();
                    String[] ar = line.split("\"previousClose\":");
                    String[] sr = ar[1].split(",");
                    priceString = sr[0];
                    Log.i("doInBackground", "Stock price " + priceString);
                    bufferedReader.close();

                    sum = sum + Double.parseDouble(priceString);

                    totalProgress += prog;
                    if(i == tickerList.length-1)
                    {
                        //   publishProgress(100);
                    }
                    else {
                        publishProgress(totalProgress);
                    }
                    Log.i("totalProgress","totalProgress is: " + totalProgress);
                    Log.i("sum","sum is: " + sum);
                }
                catch (MalformedURLException e){
                    Toast.makeText(getApplicationContext(), "URL Error",Toast.LENGTH_SHORT).show();
                }
                catch (IOException e) {
                    Toast.makeText(getApplicationContext(), "URL Error",Toast.LENGTH_SHORT).show();
                }
            }


            double result = sum / divisor;

            String st = String.format("$%,.2f", result);

            publishProgress(100);

            return st;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            begin = System.nanoTime();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            end = System.nanoTime();
            long divisor = 1000000000;
            timeTaken = (end - begin) / divisor;
            double totalTimeTaken = (double)(end-begin)/ 1000000000.00;
            textResult.setVisibility(View.VISIBLE);
            textResult.setText("Time taken for the computation = " + String.format("%.2f", (double) totalTimeTaken) + " sec(s)");
            display.setText("DJIA for the previous day close is: " + s);
            computeButton.setEnabled(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

    }


}