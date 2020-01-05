package com.learn2crack.fragments;

import android.app.Fragment;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;
import android.app.ProgressDialog;
import android.util.Log;

import com.learn2crack.R;
import android.widget.MediaController;
import android.media.MediaPlayer.OnPreparedListener;
import com.learn2crack.utils.Constants;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Bilal on 05/11/2017.
 */

public class TestimonialsFragment extends Fragment{



    public static final String TAG = TestimonialsFragment.class.getSimpleName();


    private VideoView vidTest;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_testimonials,container,false);
        initViews(view);
        return view;


    }

    private void initViews(View view)
    {
        vidTest= (VideoView) view.findViewById(R.id.videoView2);
        Log.d("myTag", "Checking video element*********" +vidTest);


        // Create a progressbar
        pDialog = new ProgressDialog(getActivity());
        // Set progressbar title
        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();

        ///////Chawlatian starts

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    getActivity());
            mediacontroller.setAnchorView(vidTest);
            // Get the URL from String VideoURL
            // Helping tutorial link: http://www.androidbegin.com/tutorial/android-video-streaming-videoview-tutorial/
           Uri video = Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.video1);
            //Uri video= Uri.parse("https://www.youtube.com/watch?v=4V4E16OlXk0");
            vidTest.setMediaController(mediacontroller);
            vidTest.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        vidTest.requestFocus();
        vidTest.setOnPreparedListener(new OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                vidTest.start();
            }
        });

//        vidTest.setVideoURI(Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.video1));
//        vidTest.start();
//
//        vidTest.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//            }
//        });

//        vidTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("myTag", "Video clicked");
//            }
//        });
    }



}
