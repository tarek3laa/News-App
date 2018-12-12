package com.example.tarek.news.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

   FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);



        try {


            Intent intent = getIntent();
            final String title0 = intent.getStringExtra("title");
            final String desc0 = intent.getStringExtra("desc");
            String imageUrl = intent.getStringExtra("image Url");
            String date0 = intent.getStringExtra("date");
            String sec0 = intent.getStringExtra("sec");

            title = (TextView) findViewById(R.id.title2);
            desc = (TextView) findViewById(R.id.description2);
            image = (ImageView) findViewById(R.id.image2);
            date = (TextView) findViewById(R.id.Date0);
            sec = (TextView) findViewById(R.id.sec);
            floatingActionButton=(FloatingActionButton)findViewById(R.id.floatingActionButton2);

            title.setText(title0);
            desc.setText(desc0);
            date.setText(date0);
            sec.setText(sec0);
            Picasso.get().load(imageUrl).into(image);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, String.format("%s: %s",title0,desc0));
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            });


        }catch (Exception e){
        }



    }
}
