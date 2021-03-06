package com.example.tarek.news.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarek.news.Activity.NewsActivity;
import com.example.tarek.news.DataSource.Adapter;
import com.example.tarek.news.DataSource.NewsSrc;
import com.example.tarek.news.DataSource.QueryUtilities;
import com.example.tarek.news.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class WorldFragment extends Fragment {
    Adapter adapter;
    TextView mEmptyStateTextView ;
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    NewsAsyncTask task;

    private static final String WORLD_URL="https://content.guardianapis.com/search?section=world&show-fields=all&use-date=published&order-by=newest&api-key=622ce180-4b1b-4903-afbb-71a287d6bef1";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment, container, false);
        try {
            mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) view.findViewById(R.id.main_swipe);
            mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
                @Override public void onRefresh() {
                    // Do work to refresh the list here.
                    new NewsAsyncTask().execute(WORLD_URL);



                }
            });

        ListView listView= view.findViewById(R.id.list_view);


        mEmptyStateTextView= (TextView)view.findViewById(R.id.empty_view);

        listView.setEmptyView(mEmptyStateTextView);
        final ArrayList <NewsSrc>newsSrcArrayList=new ArrayList<>();

        adapter = new Adapter(getActivity(), newsSrcArrayList);
        NewsAsyncTask task = new NewsAsyncTask();
        listView.setAdapter(adapter);
        task.execute(WORLD_URL);
        mWaveSwipeRefreshLayout.setRefreshing(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("title",newsSrcArrayList.get(i).getWebtitle());
                intent.putExtra("desc",newsSrcArrayList.get(i).getBodyText());
                intent.putExtra("image Url",newsSrcArrayList.get(i).getImageUrl());
                intent.putExtra("date",newsSrcArrayList.get(i).getDate());
                intent.putExtra("sec",newsSrcArrayList.get(i).getSectioName());

                startActivity(intent);
            }
        });
        System.out.println("World Fragment");}catch (Exception e){


        }


        return view;
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<NewsSrc>> {


        @Override
        protected List<NewsSrc> doInBackground(String... urls) {
            List<NewsSrc> result=null;
            // Don't perform the request if there are no URLs, or the first URL is null.
            try {


            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            result = QueryUtilities.fetchData(urls[0]);}catch (Exception e){


            }
            return result;
        }

        protected void onPostExecute(List<NewsSrc> data) {
            try {

            mWaveSwipeRefreshLayout.setRefreshing(false);
            ConnectivityManager cm =
                    (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null &&
                    activeNetwork.isConnected();
            if(!isConnected)
                mEmptyStateTextView.setText("No Internet Connection");

            adapter.clear();
            if (data != null && !data.isEmpty()) {
                adapter.addAll(data);
            }
            }catch (Exception e){

            }
        }


    }
}

