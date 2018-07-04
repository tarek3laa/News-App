package com.example.tarek.news.DataSource;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class QueryUtilities {

    public static final String LOG_TAG = QueryUtilities.class.getSimpleName();



    private QueryUtilities(){

    }

    public static List<NewsSrc> extractArabic(String JSON_RESPONSE){

        List<NewsSrc> News = new ArrayList<>();

        try {
            JSONObject jasonRoot =new JSONObject(JSON_RESPONSE);

            JSONArray articles = jasonRoot.getJSONArray("articles");
            for(int i=0;i<articles.length();i++)
            {

                JSONObject currentObject = articles.getJSONObject(i);

                 String sectionName="";
                 String webtitle=null;
                 String webUrl=null;
                 String date=null;
                 String bodyText=null;
                 String imageUrl=null;


                 webtitle= currentObject.getString("title");
                 webUrl=currentObject.getString("url");
                 date=currentObject.getString("publishedAt");
                 bodyText=currentObject.getString("description");
                 imageUrl=currentObject.getString("urlToImage");


                String []time0=date.split("T");
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm");
                long date0;
                Date date1 = null;

                try {
                    date0= timeFormat.parse(time0[1]).getTime();
                    date1= new Date((7200000 + date0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String time1=formatTime(date1);


                NewsSrc newsSrc =new NewsSrc(sectionName,webtitle,webUrl,time1,bodyText,imageUrl);

                 News.add(newsSrc);


            }





        } catch (JSONException e) {
            e.printStackTrace();
        }

        return News;
    }
    private static String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


    public static List<NewsSrc> extract(String JSON_RESPONSE){

        List<NewsSrc> News = new ArrayList<>();

        try {
            JSONObject jasonRoot =new JSONObject(JSON_RESPONSE);
            JSONObject response = jasonRoot.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for(int i=0;i<results.length();i++)
            {

                JSONObject currentObject = results.getJSONObject(i);

                String sectionName=null;
                String webtitle=null;
                String webUrl=null;
                String date=null;
                String bodyText=null;
                String imageUrl=null;


                sectionName=currentObject.getString("sectionName");
                webtitle= currentObject.getString("webTitle");
                webUrl=currentObject.getString("webUrl");
                date=currentObject.getString("webPublicationDate");

                JSONObject fields =currentObject.getJSONObject("fields");

                bodyText=fields.getString("bodyText");
                imageUrl=fields.getString("thumbnail");

                String []time0=date.split("T");
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm");
                long date0;
                Date date1 = null;

                try {
                    date0= timeFormat.parse(time0[1]).getTime();
                    date1= new Date((7200000 + date0));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String time1=formatTime(date1);

                NewsSrc newsSrc =new NewsSrc(sectionName,webtitle,webUrl,time1,bodyText,imageUrl);

                News.add(newsSrc);


            }





        } catch (JSONException e) {
            e.printStackTrace();
        }

        return News;
    }

    public static List<NewsSrc> fetchData(String requestUrl) {
        List<NewsSrc> Newslist;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }


        if(requestUrl.contains("https://newsapi.org/")){
            Newslist=extractArabic(jsonResponse);
        }
        else {
            Newslist = extract(jsonResponse);
        }
        // Return the {@link Event}
         return Newslist;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */ );
            urlConnection.setConnectTimeout(15000 /* milliseconds */ );
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
             if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    @NonNull
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}
