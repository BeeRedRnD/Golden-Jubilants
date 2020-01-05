package com.learn2crack.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.learn2crack.R;
import android.content.Intent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsRoomFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsRoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsRoomFragment extends Fragment {
    public static final String TAG = NewsRoomFragment.class.getSimpleName();



    private ImageView communityNews,irishNews,globalNews;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_room,container,false);
        initViews(view);
        return view;


    }

    private void initViews(View v)
    {
        communityNews = (ImageView)v.findViewById(R.id.community_news);
        communityNews.setOnClickListener(view->openCommunityNews());

        irishNews= (ImageView) v.findViewById(R.id.irish_news);
        irishNews.setOnClickListener(view -> openIrishNews());

        globalNews= (ImageView) v.findViewById(R.id.world_news);
        globalNews.setOnClickListener(view -> openGlobalNews());

    }

    private void openCommunityNews()
    {
        Log.d("myTag", "Community News started");

        Uri uri = Uri.parse("https://www.rte.ie/news/ireland/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

        Log.d("myTag", "Community News ended");
    }

    private void openIrishNews()
    {
        Log.d("myTag", "Irish News started");


        Uri uri = Uri.parse("https://www.independent.ie/irish-news/"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
        Log.d("myTag", "Irish News ended");
    }

    private void openGlobalNews()
    {
        Log.d("myTag", "Global News started");

        Uri uri = Uri.parse("https://www.irishtimes.com/news/world"); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

        Log.d("myTag", "Global News ended");
    }





}
