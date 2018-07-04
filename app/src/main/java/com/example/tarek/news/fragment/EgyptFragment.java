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

import com.example.tarek.news.Activity.ArabicNewsActivity;
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

public class EgyptFragment  extends Fragment {
    Adapter adapter;
    TextView mEmptyStateTextView ;
    View loadingIndicator;
    private static final String EGYPT_URL="https://newsapi.org/v2/top-headlines?country=eg&apiKey=1ed38682eb2c45a2bf657366a44b581d";
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment, container, false);

        try {
            MobileAds.initialize(getActivity(),
                    "ca-app-pub-3940256099942544~3347511713");

            mAdView = view.findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
            mAdView.loadAd(adRequest);

        ListView listView= view.findViewById(R.id.list_view);

        loadingIndicator= view.findViewById(R.id.loading_indicator);
        mEmptyStateTextView= (TextView)view.findViewById(R.id.empty_view);

        loadingIndicator.setVisibility(View.VISIBLE);
        listView.setEmptyView(mEmptyStateTextView);
        final ArrayList<NewsSrc> newsSrcArrayList=new ArrayList<>();
        adapter = new Adapter(getActivity(), newsSrcArrayList);
        NewsAsyncTask task = new  NewsAsyncTask();
        listView.setAdapter(adapter);
        task.execute(EGYPT_URL);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ArabicNewsActivity.class);
                intent.putExtra("url",newsSrcArrayList.get(i).getWebUrl());

                startActivity(intent);
            }
        });

        System.out.println("Egypt Fragment");}catch (Exception e){

        }

        return view;
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<NewsSrc>> {


        @Override
        protected List<NewsSrc> doInBackground(String... urls) {
            List<NewsSrc> result = null;
            // Don't perform the request if there are no URLs, or the first URL is null.
            try {


            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

             result= QueryUtilities.fetchData(urls[0]);}catch (Exception e){


            }
            return result;
        }

        protected void onPostExecute(List<NewsSrc> data) {
            try {


                loadingIndicator.setVisibility(View.GONE);
                ConnectivityManager cm =
                        (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnected();
                if (!isConnected)
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
