package com.example.tarek.news.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarek.news.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {
   TextView title,desc,date,sec;
   ImageView image;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        MobileAds.initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView.loadAd(adRequest);
        try {


            Intent intent = getIntent();
            String title0 = intent.getStringExtra("title");
            String desc0 = intent.getStringExtra("desc");
            String imageUrl = intent.getStringExtra("image Url");
            String date0 = intent.getStringExtra("date");
            String sec0 = intent.getStringExtra("sec");

            title = (TextView) findViewById(R.id.title2);
            desc = (TextView) findViewById(R.id.description2);
            image = (ImageView) findViewById(R.id.image2);
            date = (TextView) findViewById(R.id.Date0);
            sec = (TextView) findViewById(R.id.sec);


            title.setText(title0);
            desc.setText(desc0);
            date.setText(date0);
            sec.setText(sec0);
            Picasso.get().load(imageUrl).into(image);
        }catch (Exception e){
        }



    }
}
