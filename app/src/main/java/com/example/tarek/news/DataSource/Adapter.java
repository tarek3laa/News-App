package com.example.tarek.news.DataSource;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tarek.news.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class Adapter extends ArrayAdapter<NewsSrc> {

    Context context;
 //   public String time1;
    public Adapter(@NonNull Context context, ArrayList<NewsSrc> resource) {
        super(context, 0,resource);

        this.context=context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view1 = convertView;
        if (view1 == null) {
            view1 = LayoutInflater.from(getContext()).inflate(R.layout.news_item, parent, false);
        }

        NewsSrc newsSrc = (NewsSrc)getItem(position);

        ImageView image =view1.findViewById(R.id.image1);

        TextView title=view1.findViewById(R.id.title1);
        TextView time=view1.findViewById(R.id.science);
        TextView description=view1.findViewById(R.id.description);

        String title1=null;
        String description1=null;
        if(newsSrc.getWebtitle().length()>=25) {
             title1=newsSrc.getWebtitle().substring(0,newsSrc.getWebtitle().length()/2)+" ...";
        } else {
             title1=newsSrc.getWebtitle();
        }
        if(newsSrc.getBodyText().length()>100)
              description1=newsSrc.getBodyText().substring(0,100)+" ....";
        else  description1=newsSrc.getBodyText();


        Picasso.get().load(newsSrc.getImageUrl()).into(image);
        title.setText(title1);


        time.setText(newsSrc.getDate());
        description.setText(description1);

        return view1;
    }



}
